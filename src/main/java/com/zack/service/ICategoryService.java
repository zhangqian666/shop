package com.zack.service;

import com.zack.common.ServerResponse;
import com.zack.model.ProductCategory;

import java.util.List;

public interface ICategoryService {
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId, String categoryName);

    ServerResponse<List<ProductCategory>> getChildrenParallelCategory(Integer categoryId);
}
