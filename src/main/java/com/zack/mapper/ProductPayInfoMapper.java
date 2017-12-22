package com.zack.mapper;

import com.zack.model.ProductPayInfo;
import java.util.List;

public interface ProductPayInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductPayInfo record);

    ProductPayInfo selectByPrimaryKey(Integer id);

    List<ProductPayInfo> selectAll();

    int updateByPrimaryKey(ProductPayInfo record);
}