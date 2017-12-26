package com.zack.mapper;

import com.zack.model.ProductOrderItem;
import com.zack.model.ProductOrderItemExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ProductOrderItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_order_item
     *
     * @mbggenerated
     */
    int countByExample(ProductOrderItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_order_item
     *
     * @mbggenerated
     */
    int deleteByExample(ProductOrderItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_order_item
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_order_item
     *
     * @mbggenerated
     */
    int insert(ProductOrderItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_order_item
     *
     * @mbggenerated
     */
    int insertSelective(ProductOrderItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_order_item
     *
     * @mbggenerated
     */
    List<ProductOrderItem> selectByExample(ProductOrderItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_order_item
     *
     * @mbggenerated
     */
    ProductOrderItem selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_order_item
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") ProductOrderItem record, @Param("example") ProductOrderItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_order_item
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") ProductOrderItem record, @Param("example") ProductOrderItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_order_item
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ProductOrderItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_order_item
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ProductOrderItem record);

    void batchInsert(@Param("orderItemList") List<ProductOrderItem> orderItemList);

    List<ProductOrderItem> getByOrderNoAndUid(@Param("uid") Integer uid, @Param("orderNo") Long orderNo);

    List<ProductOrderItem> getByOrderNo(Long orderNo);
}