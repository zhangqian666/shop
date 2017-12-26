package com.zack.service;

import com.github.pagehelper.PageInfo;
import com.zack.common.ServerResponse;
import com.zack.vo.OrderVo;

public interface IOrderService {
    ServerResponse createOrder(Integer uid, Integer shippingId);

    ServerResponse cancel(Integer uid, Long orderNo);

    ServerResponse getOrderCartProduct(Integer uid);

    ServerResponse getOrderDetail(Integer uid, Long orderNo);

    ServerResponse getOrderList(Integer uid, int pageNum, int pageSize);


    //manage
    ServerResponse<PageInfo> manageList(int pageNum, int pageSize);

    ServerResponse<OrderVo> manageDetail(Long orderNo);

    ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize);

    ServerResponse<String> manageSendGoods(Long orderNo);
}
