package info.batcloud.wxc.core.service;

import info.batcloud.wxc.core.dto.FundTransferOrderDTO;

public interface FundTransferOrderService {

    FundTransferOrderDTO findById(long id);

}
