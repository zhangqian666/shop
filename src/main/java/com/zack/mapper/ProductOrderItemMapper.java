package com.zack.mapper;

import com.zack.model.ProductOrderItem;
import java.util.List;

public interface ProductOrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductOrderItem record);

    ProductOrderItem selectByPrimaryKey(Integer id);

    List<ProductOrderItem> selectAll();

    int updateByPrimaryKey(ProductOrderItem record);
}