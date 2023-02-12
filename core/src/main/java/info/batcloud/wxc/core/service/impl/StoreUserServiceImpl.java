package info.batcloud.wxc.core.service.impl;

import com.ctospace.archit.common.pagination.Paging;
import com.sankuai.meituan.banma.PeisongClient;
import com.sankuai.meituan.banma.request.ShopCreateRequest;
import com.sankuai.meituan.banma.response.ShopCreateResponse;
import com.sankuai.meituan.waimai.opensdk.vo.SystemParam;
import dada.com.DaDaClient;
import info.batcloud.wxc.core.bmap.BMapClient;
import info.batcloud.wxc.core.bmap.response.MapResponse;
import info.batcloud.wxc.core.config.MeituanPeisongApp;
import info.batcloud.wxc.core.domain.LocateInfo;
import info.batcloud.wxc.core.domain.MerchantUserDetails;
import info.batcloud.wxc.core.dto.StoreUserDTO;
import info.batcloud.wxc.core.entity.Employee;
import info.batcloud.wxc.core.entity.Store;
import info.batcloud.wxc.core.entity.StoreUser;
import info.batcloud.wxc.core.enums.DeliveryType;
import info.batcloud.wxc.core.enums.StoreUserStatus;
import info.batcloud.wxc.core.enums.UuAccountType;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.helper.CoordinateHelper;
import info.batcloud.wxc.core.helper.PagingHelper;
import info.batcloud.wxc.core.repository.EmployeeRepository;
import info.batcloud.wxc.core.repository.StoreRepository;
import info.batcloud.wxc.core.repository.StoreUserRepository;
import info.batcloud.wxc.core.service.EmployeeService;
import info.batcloud.wxc.core.service.RegionService;
import info.batcloud.wxc.core.service.StoreUserService;
import info.batcloud.wxc.core.service.YilainyunPrintService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shansong.ShanSongClient;
import shansong.request.CreateShopRequest;
import shansong.response.CreateShopResponse;
import uupt.src.com.uupt.openapi.request.AddShopRequest;
import uupt.src.com.uupt.openapi.response.AddShopResponse;

import javax.inject.Inject;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class StoreUserServiceImpl implements StoreUserService {

    @Inject
    private StoreUserRepository storeUserRepository;

    @Inject
    private StoreRepository storeRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private EmployeeRepository employeeRepository;

    @Inject
    private EmployeeService employeeService;

    @Inject
    private RegionService regionService;

    @Inject
    private PeisongClient peisongClient;

    @Inject
    private DaDaClient daDaClient;

    @Inject
    private ShanSongClient shanSongClient;

    @Inject
    private MeituanPeisongApp meituanPeisongApp;

    @Inject
    private YilainyunPrintService yilainyunPrintService;

    @Inject
    private RedisTemplate<String, String> redisTemplate;

    @Inject
    private BMapClient bMapClient;

    private static final Logger logger = LoggerFactory.getLogger(StoreUserService.class);

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        StoreUser storeUser = storeUserRepository.findByPhoneAndStatusNot(s, StoreUserStatus.DELETED);
        if (storeUser == null) {
            throw new UsernameNotFoundException("未找到" + s + "的账号");
        }
        MerchantUserDetails userDetails = new MerchantUserDetails();
        userDetails.setUsername(storeUser.getPhone());
        userDetails.setEnabled(true);
        userDetails.setAccountNonExpired(true);
        userDetails.setAccountNonLocked(true);
        userDetails.setCredentialsNonExpired(true);
        userDetails.setPassword(storeUser.getPassword());
        userDetails.setStoreUserId(storeUser.getId());
        //加载账号的权限
        ((Collection<GrantedAuthority>) userDetails.getAuthorities()).add(new SimpleGrantedAuthority("ROLE_STORE_USER"));
        return userDetails;
    }

    @Override
    public void createDDShop(Long storeUserId) {
        StoreUser storeUser = storeUserRepository.findOne(storeUserId);
        if (StringUtils.isBlank(storeUser.getLat()) || StringUtils.isBlank(storeUser.getLng())) {
            throw new BizException("请填写门店经纬度");
        } else {
            dada.com.request.CreateShopRequest request = new dada.com.request.CreateShopRequest();
            request.setBusiness(13);
            request.setContacName(storeUser.getName());
            request.setStationName(storeUser.getName());
            request.setLat(Double.valueOf(storeUser.getLat()));
            request.setLng(Double.valueOf(storeUser.getLng()));
            request.setOriginShopId(storeUserId.toString());
            request.setPhone(storeUser.getPhone());
            request.setStationAddress(storeUser.getAddress());
            dada.com.response.CreateShopResponse response = daDaClient.request(request);
            dada.com.response.CreateShopResponse.Result result = response.getResult();
            if (result.getSuccess() == 1) {
                throw new BizException("创建达达店铺成功");
            } else {
                throw new BizException(result.getFailedList().get(0));
            }
        }
    }

    @Override
    public void createSSShop(Long storeUserId) {
        StoreUser storeUser = storeUserRepository.findOne(storeUserId);
        if (StringUtils.isBlank(storeUser.getLat()) || StringUtils.isBlank(storeUser.getLng())) {
            throw new BizException("请填写门店经纬度");
        } else {
            LocateInfo storeLocal = this.getBaiDuMap(storeUser.getLng(), storeUser.getLat());
            CreateShopRequest request = new CreateShopRequest();
            request.setStoreName(storeUser.getName());
            request.setPhone(storeUser.getPhone());
            request.setOperationType("1");
            request.setLongitude(String.valueOf(storeLocal.getLongitude()));
            request.setLatitude(String.valueOf(storeLocal.getLatitude()));
            request.setGoodType("10");
            request.setCityName(storeUser.getCity().getName());
            request.setAddressDetail(storeUser.getAddress());
            request.setAddress(storeUser.getAddress());
            CreateShopResponse response = shanSongClient.request(request);
            logger.info("闪送创建店铺返回"+ response.toString());
        }
    }

    @Override
    public void createUUShop(Long storeUserId) {
        StoreUser storeUser = storeUserRepository.findOne(storeUserId);
        if (StringUtils.isBlank(storeUser.getLat()) || StringUtils.isBlank(storeUser.getLng())) {
            throw new BizException("请填写门店经纬度");
        } else {
            AddShopRequest addShopRequest = new AddShopRequest();
            addShopRequest.setAddress(storeUser.getAddress());
            addShopRequest.setCity_name(storeUser.getCity().getName());
            addShopRequest.setCounty_name(storeUser.getDistrict().getName());
            LocateInfo storeLocal = this.getBaiDuMap(storeUser.getLng(), storeUser.getLat());
            addShopRequest.setLat(String.valueOf(storeLocal.getLatitude()));
            addShopRequest.setLinkman(storeUser.getName());
            addShopRequest.setLinkman_mobile(storeUser.getPhone());
            addShopRequest.setLng(String.valueOf(storeLocal.getLongitude()));
            addShopRequest.setShopname(storeUser.getName());
            addShopRequest.setUsernote(storeUser.getAddress());
            List<Store> stores = storeRepository.findByStoreUserIdAndOpeningTrue(storeUserId);
            UuAccountType uuAccountType = UuAccountType.TOTAL;
            for (Store store : stores) {
                if (store.getDeliveryType() == DeliveryType.UU_OPEN) {
                    if (store.getUuAcountType() != null) {
                        uuAccountType = store.getUuAcountType();
                    }
                }
            }
            AddShopResponse execute = addShopRequest.execute(uuAccountType);
            logger.info("创建uu跑腿门店返回" + execute.getReturn_code() + execute.getReturn_msg());

        }
    }

    private LocateInfo getBaiDuMap(String lng, String lat) {
        LocateInfo storeLocal = new LocateInfo();
        try {
            MapResponse location1 = bMapClient.HuoToBai(lng, lat);
            if (location1.getStatus() == 0) {
                storeLocal.setLongitude(Double.valueOf(location1.getResult().get(0).getX()));
                storeLocal.setLatitude(Double.valueOf(location1.getResult().get(0).getY()));
                return storeLocal;
            }
        } catch (Exception e) {
            logger.error("调用百度获取坐标失败");
        }
        storeLocal = CoordinateHelper.gcj02_To_Bd09(Double.valueOf(lng), Double.valueOf(lat));
        return storeLocal;
    }

    @Override
    public void deleteSms(Long storeUserId) {
        StoreUser storeUser = storeUserRepository.findOne(storeUserId);
        String phone = storeUser.getPhone();
        String key = "SMS_PHONE_CODE_" + phone;
        redisTemplate.delete(key);
    }

    @Override
    public void getNewYlyToken() {
        List<StoreUser> storeUsers = storeUserRepository.findAllByMachineCodeIsNotNull();
        for (StoreUser storeUser : storeUsers) {
            try {
                yilainyunPrintService.getToken(storeUser.getId());
            } catch (Exception e) {
                System.out.println("更新商家易联云token出错" + storeUser.getName() + e.getMessage());
            }

        }
    }

    @Override
    public StoreUserDTO findByPhone(String phone) {
        return toDTO(storeUserRepository.findByPhoneAndStatusNot(phone, StoreUserStatus.DELETED));
    }

    @Override
    public StoreUserDTO findById(Long id) {
        return toDTO(storeUserRepository.findOne(id));
    }

    @Override
    public void modifyPassword(long id, String originPassword, String password) {
        StoreUser storeUser = storeUserRepository.findOne(id);
        if (!passwordEncoder.matches(originPassword, storeUser.getPassword())) {
            throw new BizException("原始密码输入不正确");
        }
        storeUser.setPassword(passwordEncoder.encode(password));
        storeUserRepository.save(storeUser);
    }

    @Override
    public boolean checkPassword(StoreUser storeUser, String password) {
        return false;
    }

    @Override
    public void saveStoreUser(StoreUser storeUser) {
        storeUser.setDeliveryType(DeliveryType.PLATFORM);
        StoreUser eStoreUser = this.storeUserRepository.findByPhoneAndStatusNot(storeUser.getPhone(), StoreUserStatus.DELETED);
        if (eStoreUser != null) {
            throw new BizException("管理账户已经存在");
        }
        if (StringUtils.isNotBlank(storeUser.getCode()) && storeUserRepository.countByCode(storeUser.getCode()) != 0) {
            throw new BizException("编码已经存在");
        }
        if (storeUserRepository.countByName(storeUser.getName()) != 0) {
            throw new BizException("名称已经存在");
        }
        storeUser.setCreateTime(new Date());
        storeUser.setPassword(passwordEncoder.encode(storeUser.getPassword()));
        storeUserRepository.save(storeUser);
    }

    @Override
    public void updateStoreUser(long id, StoreUserUpdateParam param) {
        if (storeUserRepository.countByNameAndIdNot(param.getName(), id) != 0) {
            throw new BizException("名称已经存在");
        }
        if (storeUserRepository.countByPhoneAndIdNot(param.getPhone(), id) != 0) {
            throw new BizException("手机号已经存在");
        }
        StoreUser eStoreUser = this.storeUserRepository.findOne(id);
        if (eStoreUser != null && !eStoreUser.getId().equals(id)) {
            throw new BizException("管理账户已经存在");
        }
        BeanUtils.copyProperties(param, eStoreUser);
        eStoreUser.setProvince(regionService.findById(param.getProvinceId()));
        eStoreUser.setCity(regionService.findById(param.getCityId()));
        eStoreUser.setDistrict(regionService.findById(param.getDistrictId()));
        if (param.getSfpsShopId() != null) {
            eStoreUser.setSfpsShopId(param.getSfpsShopId());
        }

        if (eStoreUser.getDeliveryType() == DeliveryType.MEITUAN_OPEN) {
            this.updateMeituanPeisongShop(eStoreUser);
        }
        storeUserRepository.save(eStoreUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setStoreUserDeliveryType(long id, DeliveryType type) {
        StoreUser storeUser = this.storeUserRepository.findOne(id);
        if (type == DeliveryType.MEITUAN_OPEN) {
            this.updateMeituanPeisongShop(storeUser);
        }
        storeUser.setDeliveryType(type);
        storeUserRepository.save(storeUser);
    }

    private void updateMeituanPeisongShop(StoreUser storeUser) {
        if (null == storeUser.getMtpsCategory()) {
            throw new BizException("美团配送一级品类不能为空");
        }
        if (null == storeUser.getMtpsSecondCategory()) {
            throw new BizException("美团配送二级品类不能为空");
        }
        if (storeUser.getPsContactName() == null) {
            throw new BizException("配送联系人不能为空");
        }
        if (storeUser.getPsContactPhone() == null) {
            throw new BizException("配送联系人电话不能为空");
        }
        if (storeUser.getLat() == null || storeUser.getLng() == null) {
            throw new BizException("坐标不能为空");
        }
        if (storeUser.getBizEndTime() == null || storeUser.getBizEndTime() == null) {
            throw new BizException("营业时间不能为空");
        }
        ShopCreateRequest req = new ShopCreateRequest();
        req.setCategory(storeUser.getMtpsCategory());
        req.setSecondCategory(storeUser.getMtpsSecondCategory());
        req.setBusinessTime("[{startTime:\"" + storeUser.getBizStartTime() + "\",endTime:\"" + storeUser.getBizEndTime() + "\"}]");
        req.setContactName(storeUser.getPsContactName());
        req.setContactPhone(storeUser.getPsContactPhone());
        req.setCoordinateType(0);
        req.setShopAddress(storeUser.getAddress());
        req.setShopId(storeUser.getId() + "");
        req.setShopLat(Double.valueOf((Double.parseDouble(storeUser.getLat()) * Math.pow(10d, 6))).intValue());
        req.setShopLng(Double.valueOf((Double.parseDouble(storeUser.getLng()) * Math.pow(10d, 6))).intValue());
        req.setShopName(storeUser.getName());
        req.setDeliveryServiceCodes(meituanPeisongApp.getDeliveryServiceCode() + "");
        ShopCreateResponse res = peisongClient.execute(req);
        if (res.getData() != null && res.getData().getShopId() != null) {
            storeUser.setMtpsShopId(res.getData().getShopId());
        } else {
            throw new BizException(res.getMessage());
        }
    }

    @Override
    public void lockStoreUser(long id) {
        StoreUser storeUser = this.storeUserRepository.findOne(id);
        storeUser.setStatus(StoreUserStatus.INVALID);
        storeUserRepository.save(storeUser);
    }

    @Override
    public void unLockStoreUser(long id) {
        StoreUser storeUser = this.storeUserRepository.findOne(id);
        storeUser.setStatus(StoreUserStatus.VALID);
        storeUserRepository.save(storeUser);
    }

    @Override
    public void modifyPassword(long id, String password) {
        StoreUser storeUser = this.storeUserRepository.findOne(id);
        storeUser.setPassword(passwordEncoder.encode(password));
        storeUserRepository.save(storeUser);
    }

    @Override
    public Paging<StoreUserDTO> search(SearchParam param) {
        Specification<StoreUser> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (query.getResultType() != Long.class) {
                root.fetch("bizManager", JoinType.LEFT);
            }
            if (param.getMeituanOpened() != null) {
                expressions.add(cb.equal(root.get("meituanOpened"), param.getMeituanOpened()));
            }
            if (param.getJddjOpened() != null) {
                expressions.add(cb.equal(root.get("jddjOpened"), param.getJddjOpened()));
            }
            if (param.getEleOpened() != null) {
                expressions.add(cb.equal(root.get("eleOpened"), param.getEleOpened()));
            }
            if (param.getClbmOpened() != null) {
                expressions.add(cb.equal(root.get("clbmOpened"), param.getClbmOpened()));
            }
            if (param.getWanteOpened() != null) {
                expressions.add(cb.equal(root.get("wanteOpened"), param.getWanteOpened()));
            }
            if (StringUtils.isNotBlank(param.getPhone())) {
                expressions.add(cb.equal(root.get("phone"), param.getPhone()));
            }
            if (StringUtils.isNotBlank(param.getName())) {
                expressions.add(cb.like(root.get("name"), "%" + param.getName() + "%"));
            }
            if (StringUtils.isNotBlank(param.getCode())) {
                expressions.add(cb.equal(root.get("code"), param.getName()));
            }
            if (param.getSeCycle() != null) {
                expressions.add(cb.equal(root.get("seCycle"), param.getSeCycle()));
            }
            if (param.getStatus() != null) {
                expressions.add(cb.equal(root.get("status"), param.getStatus()));
            } else {
                expressions.add(cb.notEqual(root.get("status"), StoreUserStatus.DELETED));
            }
            if (param.getOpened() != null) {
                expressions.add(cb.equal(root.get("opened"), param.getOpened()));
            }

            if (param.getCityId() != null) {
                expressions.add(cb.equal(root.get("city").get("id"), param.getCityId()));
            }

            if (param.getDistrictId() != null) {
                expressions.add(cb.equal(root.get("district").get("id"), param.getDistrictId()));
            }

            if (param.getProvinceId() != null) {
                expressions.add(cb.equal(root.get("province").get("id"), param.getProvinceId()));
            }

            if (param.getAutoReceiptOrder() != null) {
                expressions.add(cb.equal(root.get("autoReceiptOrder"), param.getAutoReceiptOrder()));
            }
            if (param.getSettleType() != null) {
                expressions.add(cb.equal(root.get("settleType"), param.getSettleType()));
            }
            return predicate;
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");

        Pageable pageable = new PageRequest(param.getPage() - 1,
                param.getPageSize(), sort);
        Page<StoreUser> page = storeUserRepository.findAll(specification, pageable);
        return PagingHelper.of(page, user -> toDTO(user), param.getPage(), param.getPageSize());
    }


    @Override
    public void deleteById(long id) {
        StoreUser storeUser = this.storeUserRepository.findOne(id);
        storeUser.setStatus(StoreUserStatus.DELETED);
        storeUserRepository.save(storeUser);
    }

    @Override
    public void setStoreUserBizManager(long storeUserId, long employeeId) {
        StoreUser storeUser = this.storeUserRepository.findOne(storeUserId);
        Employee employee = employeeRepository.findOne(employeeId);
        storeUser.setBizManager(employee);
        storeUserRepository.save(storeUser);
    }

    @Override
    public void setStoreUserBizManager2(long storeUserId, long employeeId) {
        StoreUser storeUser = this.storeUserRepository.findOne(storeUserId);
        Employee employee = employeeRepository.findOne(employeeId);
        storeUser.setBizManager2(employee);
        storeUserRepository.save(storeUser);
    }

    @Override
    public void updateStoreUserBank(long storeUserId, String bankName, String bankAccount, String bankAccountName) {
        StoreUser storeUser = this.storeUserRepository.findOne(storeUserId);
        storeUser.setBankName(bankName);
        storeUser.setBankAccount(bankAccount);
        storeUser.setBankAccountName(bankAccountName);
        storeUserRepository.save(storeUser);
    }

    @Override
    public void changeBizManager(long originBizManagerId, long currentBizManagerId) {
        storeUserRepository.updateBizManagerId(originBizManagerId, currentBizManagerId);
    }

    @Override
    public StoreUserDTO toDTO(StoreUser user) {
        if (user == null) {
            return null;
        }
        StoreUserDTO dto = new StoreUserDTO();
        BeanUtils.copyProperties(user, dto);
        if (user.getDistrict() != null) {
            dto.setDistrict(regionService.toDTO(regionService.findById(user.getDistrict().getId())));
        }
        if (user.getCity() != null) {
            dto.setCity(regionService.toDTO(regionService.findById(user.getCity().getId())));
        }
        if (user.getProvince() != null) {
            dto.setProvince(regionService.toDTO(regionService.findById(user.getProvince().getId())));
        }
        dto.setBizManager(employeeService.toDTO(user.getBizManager()));
        dto.setBizManager2(employeeService.toDTO(user.getBizManager2()));
        return dto;
    }

    @Override
    public boolean toggleAutoReceiptOrderById(long id) {
        StoreUser storeUser = storeUserRepository.findOne(id);
        storeUser.setAutoReceiptOrder(storeUser.getAutoReceiptOrder() == null ? true : !storeUser.getAutoReceiptOrder());
        storeUserRepository.save(storeUser);
        return storeUser.getAutoReceiptOrder();
    }

    @Override
    public boolean isAutoReceiptOrderById(long id) {
        StoreUser storeUser = storeUserRepository.findOne(id);
        return storeUser.getAutoReceiptOrder() == null ? false : storeUser.getAutoReceiptOrder();
    }

}
