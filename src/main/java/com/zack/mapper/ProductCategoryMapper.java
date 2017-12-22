package com.zack.mapper;

import com.zack.model.ProductCategory;
import java.util.List;

public interface ProductCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductCategory record);

    ProductCategory selectByPrimaryKey(Integer id);

    List<ProductCategory> selectAll();

    int updateByPrimaryKey(ProductCategory record);
}