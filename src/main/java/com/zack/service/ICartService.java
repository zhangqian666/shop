package com.zack.service;

import com.zack.common.ServerResponse;
import com.zack.vo.CartVo;

public interface ICartService {

    ServerResponse<CartVo> add(Integer id, Integer productId, Integer count);

    ServerResponse<CartVo> list(Integer id);

    ServerResponse<CartVo> update(Integer id, Integer productId, Integer count);

    ServerResponse<CartVo> deleteProduct(Integer id, String productIds);

    ServerResponse<CartVo> selectOrUnSelect(Integer uid,Integer productId, int checked);

    ServerResponse<Integer> getCartProductCount(Integer uid);
}
