package com.zack.service;

import com.github.pagehelper.PageInfo;
import com.zack.common.ServerResponse;
import com.zack.model.Shipping;

public interface IShippingService {
    ServerResponse add(Integer uid, Shipping shipping);

    ServerResponse del(Integer uid, Integer shippingId);

    ServerResponse update(Integer uid, Shipping shipping);

    ServerResponse<Shipping> select(Integer uid, Integer shippingId);

    ServerResponse<PageInfo> list(Integer uid, int pageNum, int pageSize);
}
