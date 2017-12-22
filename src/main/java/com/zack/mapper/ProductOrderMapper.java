package com.zack.mapper;

import com.zack.model.ProductOrder;
import java.util.List;

public interface ProductOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductOrder record);

    ProductOrder selectByPrimaryKey(Integer id);

    List<ProductOrder> selectAll();

    int updateByPrimaryKey(ProductOrder record);
}