package com.zack.controller.portal;

import com.github.pagehelper.PageInfo;
import com.zack.common.ServerResponse;
import com.zack.model.Product;
import com.zack.service.IProductService;
import com.zack.vo.ProductDetailsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    public IProductService iProductService;

    @RequestMapping(value = "details.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<ProductDetailsVo> getDetails(Integer productId) {
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping(value = "list.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy", defaultValue = "") String orderBy) {

        return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);

    }
}
