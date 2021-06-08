package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.pojo.Product;
import com.imooc.mall.vo.ProductDetailVo;
import com.imooc.mall.vo.ProductVo;
import com.imooc.mall.vo.ResponseVo;

import java.util.List;

/**
 *
 */
public interface IProductService {

	ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

	ResponseVo<ProductDetailVo> detail(Integer productId);

	Integer productCount();

	ResponseVo<List<ProductVo>> getProducts();

	ResponseVo productStatus(Integer productId,Integer status);

	ResponseVo productSave(Product product);

	ResponseVo productDelete(Integer productId);
}
