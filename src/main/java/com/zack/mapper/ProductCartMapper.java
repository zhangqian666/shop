package com.zack.mapper;

import com.zack.model.ProductCart;
import java.util.List;

public interface ProductCartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductCart record);

    ProductCart selectByPrimaryKey(Integer id);

    List<ProductCart> selectAll();

    int updateByPrimaryKey(ProductCart record);
}