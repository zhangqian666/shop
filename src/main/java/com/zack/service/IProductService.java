package com.zack.service;

import com.github.pagehelper.PageInfo;
import com.zack.common.ServerResponse;
import com.zack.model.Product;
import com.zack.vo.ProductDetailsVo;

public interface IProductService {
    ServerResponse<ProductDetailsVo> getProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy);

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse setSaleStatus(Integer productId, Integer status);

    ServerResponse manageProductDetail(Integer productId);

    ServerResponse getProductList(int pageNum, int pageSize);

    ServerResponse searchProduct(String productName, Integer productId, int pageNum, int pageSize);
}
