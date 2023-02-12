package info.batcloud.wxc.core.service.impl;

import info.batcloud.wxc.core.dto.FundTransferOrderDTO;
import info.batcloud.wxc.core.entity.FundTransferOrder;
import info.batcloud.wxc.core.repository.FundTransferOrderRepository;
import info.batcloud.wxc.core.service.FundTransferOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class FundTransferOrderServiceImpl implements FundTransferOrderService {

    @Inject
    private FundTransferOrderRepository fundTransferOrderRepository;

    @Override
    public FundTransferOrderDTO findById(long id) {
        FundTransferOrder to = fundTransferOrderRepository.findOne(id);
        return toBO(to);
    }

    private FundTransferOrderDTO toBO(FundTransferOrder order) {
        FundTransferOrderDTO bo = new FundTransferOrderDTO();
        BeanUtils.copyProperties(order, bo);
        return bo;
    }
}
