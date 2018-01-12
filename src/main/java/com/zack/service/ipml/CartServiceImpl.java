package com.zack.service.ipml;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.zack.common.Const;
import com.zack.common.ResponseCode;
import com.zack.common.ServerResponse;
import com.zack.mapper.ProductCartMapper;
import com.zack.mapper.ProductMapper;
import com.zack.model.Product;
import com.zack.model.ProductCart;
import com.zack.service.ICartService;
import com.zack.utils.BigDecimalUtil;
import com.zack.utils.PropertiesUtil;
import com.zack.vo.CartProductVo;
import com.zack.vo.CartVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("iCartService")
@Slf4j
public class CartServiceImpl implements ICartService {


    @Autowired
    private ProductCartMapper productCartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse<CartVo> add(Integer uid, Integer productId, Integer count) {
        if (productId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        ProductCart productCart = productCartMapper.selectCartByUserIdAndProductId(uid, productId);
        if (productCart == null) {
            ProductCart cartItem = new ProductCart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setProductId(productId);
            cartItem.setUserId(uid);
            productCartMapper.insert(cartItem);
        } else {
            count = productCart.getQuantity() + count;
            productCart.setQuantity(count);
            productCartMapper.updateByPrimaryKeySelective(productCart);
        }
        return this.list(uid);
    }

    @Override
    public ServerResponse<CartVo> list(Integer uid) {
        CartVo cartVo = this.getCartVoLimit(uid);
        return ServerResponse.createBySuccess(cartVo);
    }

    @Override
    public ServerResponse<CartVo> update(Integer uid, Integer productId, Integer count) {
        if (productId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        ProductCart productCart = productCartMapper.selectCartByUserIdAndProductId(uid, productId);
        if (productCart != null) {
            productCart.setQuantity(count);
        }
        productCartMapper.updateByPrimaryKey(productCart);
        return this.list(uid);
    }

    @Override
    public ServerResponse<CartVo> deleteProduct(Integer uid, String productIds) {
        List<String> productList = Splitter.on(",").splitToList(productIds);
        if (CollectionUtils.isEmpty(productList)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        productCartMapper.deleteByUserIdProductIds(uid, productList);
        return this.list(uid);
    }

    @Override
    public ServerResponse<CartVo> selectOrUnSelect(Integer uid, Integer productId, int checked) {
        int resultCount = productCartMapper.checkedOrUncheckedProduct(uid, productId, checked);
        return this.list(uid);
    }

    @Override
    public ServerResponse<Integer> getCartProductCount(Integer uid) {
        if(uid == null){
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(productCartMapper.selectCartProductCount(uid));
    }

    private CartVo getCartVoLimit(Integer uid) {
        CartVo cartVo = new CartVo();
        List<ProductCart> cartList = productCartMapper.selectCartByUserId(uid);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");

        if (CollectionUtils.isNotEmpty(cartList)) {
            for (ProductCart cartItem : cartList) {
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(uid);
                cartProductVo.setProductId(cartItem.getProductId());

                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if (product != null) {
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStock(product.getStock());
                    //判断库存
                    int buyLimitCount = 0;
                    if (product.getStock() >= cartItem.getQuantity()) {
                        //库存充足的时候
                        buyLimitCount = cartItem.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //购物车中更新有效库存
                        ProductCart cartForQuantity = new ProductCart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        productCartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    //计算总价
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVo.getQuantity()));
                    cartProductVo.setProductChecked(cartItem.getChecked());
                }

                if (cartItem.getChecked() == Const.Cart.CHECKED) {
                    //如果已经勾选,增加到整个的购物车总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(this.getAllCheckedStatus(uid));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return cartVo;
    }

    private boolean getAllCheckedStatus(Integer userId) {
        if (userId == null) {
            return false;
        }
        return productCartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;

    }
}
