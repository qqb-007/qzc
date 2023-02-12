package info.batcloud.wxc.core.service.impl;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.constants.MessageKeyConstants;
import info.batcloud.wxc.core.dto.WalletDTO;
import info.batcloud.wxc.core.entity.Wallet;
import info.batcloud.wxc.core.entity.WalletFlowDetail;
import info.batcloud.wxc.core.enums.WalletFlowDetailType;
import info.batcloud.wxc.core.enums.WalletValueType;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.repository.StoreUserRepository;
import info.batcloud.wxc.core.repository.WalletFlowDetailRepository;
import info.batcloud.wxc.core.repository.WalletRepository;
import info.batcloud.wxc.core.service.StoreUserService;
import info.batcloud.wxc.core.service.SystemSettingService;
import info.batcloud.wxc.core.service.WalletService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

    private static final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    @Inject
    private WalletRepository walletRepository;

    @Inject
    private WalletFlowDetailRepository walletFlowDetailRepository;

    @Inject
    private StoreUserRepository storeUserRepository;

    @Inject
    private StoreUserService storeUserService;

    @Inject
    private SystemSettingService systemSettingService;

    @Override
    @Transactional
    //钱包金额改变
    public synchronized WalletChangeResult addMoney(long userId, float money, WalletFlowDetailType flowDetailType, String[] context) {
        return addValue(userId, money, WalletValueType.MONEY, flowDetailType, context);
    }

    @Override
    public synchronized WalletDTO findByStoreUserId(long storeUserId) {
        Wallet wallet = walletRepository.findByStoreUserId(storeUserId);
        if (wallet == null) {
            wallet = new Wallet();
            wallet.setStoreUser(storeUserRepository.findOne(storeUserId));
        }
        return toWalletDto(wallet);
    }

    @Override
    @Transactional
    //商家取钱
    public synchronized WalletConsumeResult consumeMoney(long userId, float money, WalletFlowDetailType flowDetailType, String... context) {
        WalletConsumeResult result = new WalletConsumeResult();
        WalletDTO wallet = findByStoreUserId(userId);
        if (money <= 0 || wallet.getMoney() < money) {
            result.setSuccess(false);
            result.setCode(MessageKeyConstants.NO_ENOUGH_MONEY);
            return result;
        }
        result.setSuccess(true);
        WalletChangeResult changeResult = this.addMoney(userId, -money, flowDetailType, context);
        result.setWalletChangeResult(changeResult);
        return result;
    }

    @Override
    public Paging<WalletDTO> search(SearchParam param) {
        Specification<Wallet> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (param.getStoreUserId() != null) {
                expressions.add(cb.equal(root.get("storeUser").get("id"), param.getStoreUserId()));
            }
            if (StringUtils.isNotEmpty(param.getStoreUserName())) {
                expressions.add(cb.like(root.get("storeUser").get("name"), "%" + param.getStoreUserName() + "%"));
            }
            if (StringUtils.isNotEmpty(param.getStoreUserPhone())) {
                expressions.add(cb.equal(root.get("storeUser").get("phone"), param.getStoreUserPhone()));
            }
            return predicate;
        };
        Sort sort = null;
        if (param.getSort() != null) {
            switch (param.getSort()) {
                case ID_DESC:
                    sort = new Sort(Sort.Direction.DESC, "id");
                    break;
                case MONEY_DESC:
                    sort = new Sort(Sort.Direction.DESC, "money");
                    break;
                case MONEY_ASC:
                    sort = new Sort(Sort.Direction.ASC, "money");
                    break;

            }
        } else {
            sort = new Sort(Sort.Direction.DESC, "id");
        }
        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<Wallet> page = walletRepository.findAll(specification, pageable);
        Paging<WalletDTO> paging = PagingHelper.of(page, item -> toWalletDto(item), param.getPage(), param.getPageSize());
        return paging;
    }

    private synchronized WalletChangeResult addValue(long storeUserId, Number value, WalletValueType type, WalletFlowDetailType flowDetailType, String[] context) {
        logger.info(String.format("用户钱包变更：storeUserId:%s,value:%s,type:%s,flowDetailType:%s,context:%s",
                new Object[]{storeUserId + "", value + "", type.name(), flowDetailType.name(), context}));
        Wallet wallet = walletRepository.findByStoreUserId(storeUserId);
        if (wallet == null) {
            wallet = new Wallet();
            wallet.setStoreUser(storeUserRepository.findOne(storeUserId));
            wallet.setCreateTime(new Date());
        }
        wallet.setUpdateTime(new Date());
        WalletFlowDetail detail = new WalletFlowDetail();
        logger.info(String.format("用户钱包变更前状态：总获取金额%s,金额%s,消费金额%s",
                new Object[]{wallet.getObtainedMoney(),
                        wallet.getMoney(), wallet.getConsumedMoney()}));
        switch (type) {
            case MONEY:
                detail.setBeforeValue(wallet.getMoney());
                wallet.setMoney(value.floatValue() + wallet.getMoney());
                detail.setAfterValue(wallet.getMoney());
                if (value.floatValue() > 0) {
                    //如果金额大于0，则保存总获取的现金数量
                    wallet.setObtainedMoney(wallet.getObtainedMoney() + value.floatValue());
                }
                logger.info(String.format("增加金额：增加前%s,增加%s,增加后%s", new Object[]{detail.getBeforeValue() + "", value + "", wallet.getMoney() + ""}));
                break;
        }
        walletRepository.save(wallet);
        detail.setStoreUser(storeUserRepository.findOne(storeUserId));
        detail.setCreateTime(new Date());
        detail.setType(flowDetailType);
        detail.setValue(value.floatValue());
        detail.setValueType(type);
        detail.setContext(context == null ? null : String.join(",", context));
        walletFlowDetailRepository.save(detail);
        WalletChangeResult result = new WalletChangeResult();
        result.setWalletDTO(toWalletDto(wallet));
        result.setWalletDetailId(detail.getId());
        logger.info(String.format("用户钱包变更后状态：总获取金额%s,金额%s,消费金额%s", new Object[]{wallet.getObtainedMoney() + "",
                wallet.getMoney() + "", wallet.getConsumedMoney() + ""}));
        return result;
    }

    private WalletDTO toWalletDto(Wallet wallet) {
        WalletDTO dto = new WalletDTO();
        BeanUtils.copyProperties(wallet, dto);
        dto.setStoreUser(storeUserService.toDTO(wallet.getStoreUser()));
        return dto;
    }
}
