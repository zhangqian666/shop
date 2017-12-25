package com.zack.service.ipml;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zack.common.ServerResponse;
import com.zack.mapper.ProductCategoryMapper;
import com.zack.model.ProductCategory;
import com.zack.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private ProductCategoryMapper productCategoryMapper;


    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("添加品类参数错误");
        }
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(categoryName);
        productCategory.setParentId(parentId);
        productCategory.setStatus(true);

        int rowCount = productCategoryMapper.insert(productCategory);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("添加品类失败");

    }


    public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {
        if (categoryId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("更新品类参数错误");
        }
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(categoryId);
        productCategory.setName(categoryName);
        int rowCount = productCategoryMapper.updateByPrimaryKeySelective(productCategory);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("更新品类名字成功");
        }
        return ServerResponse.createByErrorMessage("更新品类名字失败");
    }

    public ServerResponse<List<ProductCategory>> getChildrenParallelCategory(Integer categoryId) {
        List<ProductCategory> categoryList = productCategoryMapper.selectCategoryChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }


    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) {
        Set<ProductCategory> categorySet = Sets.newHashSet();
        findChildCategory(categorySet, categoryId);

        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null) {
            for (ProductCategory productCategory : categorySet) {
                categoryIdList.add(productCategory.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);

    }

    //递归算法,算出子节点
    private Set<ProductCategory> findChildCategory(Set<ProductCategory> categorySet, Integer categoryId) {
        ProductCategory category = productCategoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        //查找子节点,递归算法一定要有一个退出的条件
        List<ProductCategory> categoryList = productCategoryMapper.selectCategoryChildrenByParentId(categoryId);
        for (ProductCategory categoryItem : categoryList) {
            findChildCategory(categorySet, categoryItem.getId());
        }
        return categorySet;
    }
}
