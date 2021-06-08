package com.imooc.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mall.dao.ProductMapper;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.pojo.Product;
import com.imooc.mall.service.ICategoryService;
import com.imooc.mall.service.IProductService;
import com.imooc.mall.vo.ProductDetailVo;
import com.imooc.mall.vo.ProductVo;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.imooc.mall.enums.ProductStatusEnum.DELETE;
import static com.imooc.mall.enums.ProductStatusEnum.OFF_SALE;
import static com.imooc.mall.enums.ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE;

/**
 *
 */
@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private ProductMapper productMapper;

	@Override
	public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
		Set<Integer> categoryIdSet = new HashSet<>();
		if (categoryId != null) {
			categoryService.findSubCategoryId(categoryId, categoryIdSet);
			categoryIdSet.add(categoryId);
		}

		PageHelper.startPage(pageNum, pageSize);
		List<Product> productList = productMapper.selectByCategoryIdSet(categoryIdSet);
		List<ProductVo> productVoList = productList.stream()
				.map(e -> {
					ProductVo productVo = new ProductVo();
					BeanUtils.copyProperties(e, productVo);
					return productVo;
				})
				.collect(Collectors.toList());

		PageInfo pageInfo = new PageInfo<>(productList);
		pageInfo.setList(productVoList);
		return ResponseVo.success(pageInfo);
	}

	@Override
	public ResponseVo<ProductDetailVo> detail(Integer productId) {
		Product product = productMapper.selectByPrimaryKey(productId);

		//只对确定性条件判断
		if (product.getStatus().equals(OFF_SALE.getCode())
				|| product.getStatus().equals(DELETE.getCode())) {
			return ResponseVo.error(PRODUCT_OFF_SALE_OR_DELETE);
		}

		ProductDetailVo productDetailVo = new ProductDetailVo();
		BeanUtils.copyProperties(product, productDetailVo);
		//敏感数据处理
		productDetailVo.setStock(product.getStock() > 100 ? 100 : product.getStock());
		return ResponseVo.success(productDetailVo);
	}

	@Override
	public Integer productCount() {
		return productMapper.selectProductCount();
	}

	@Override
	public ResponseVo<List<ProductVo>> getProducts() {
		List<Product> productList = productMapper.selectAllProduct();
		ArrayList<ProductVo> productVos = new ArrayList<>();
		for(Product product:productList){
			ProductVo vo = new ProductVo();
			BeanUtils.copyProperties(product,vo);
			productVos.add(vo);
		}
		return ResponseVo.success(productVos);
	}

	@Override
	public ResponseVo productStatus(Integer productId, Integer status) {
		Product product = productMapper.selectByPrimaryKey(productId);
		if(product == null){
			return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
		}
		product.setStatus(status);
		int code = productMapper.updateByPrimaryKey(product);
		if(code == 1){
			return ResponseVo.successByMsg("修改状态成功");
		}else {
			return ResponseVo.error(ResponseEnum.PRODUCT_STATUS_ERROR);
		}
	}

	@Override
	public ResponseVo productSave(Product product) {
		if(product.getId() == null){
			int code = productMapper.insertSelective(product);
			if(code == 1){
				return ResponseVo.successByMsg("新增产品成功");
			}else {
				return ResponseVo.error(ResponseEnum.PRODUCT_ADD_ERROR);
			}
		}else {
			int code = productMapper.updateByPrimaryKeySelective(product);
			if(code == 1){
				return ResponseVo.successByMsg("产品更新成功");
			}else {
				return ResponseVo.error(ResponseEnum.PRODUCT_UPDATE_ERROR);
			}
		}
	}

	@Override
	public ResponseVo productDelete(Integer productId) {
		int code = productMapper.deleteByPrimaryKey(productId);
		if(code == 1){
			return ResponseVo.successByMsg("删除商品成功");
		}else {
			return ResponseVo.error(ResponseEnum.PRODUCT_DELETE_ERROR);
		}
	}
}
