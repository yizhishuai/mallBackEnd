package com.imooc.mall.service;

import com.imooc.mall.vo.CategoryVo;
import com.imooc.mall.vo.ResponseVo;

import java.util.List;
import java.util.Set;

/**
 *
 */
public interface ICategoryService {

	ResponseVo<List<CategoryVo>> selectAll();

	void findSubCategoryId(Integer id, Set<Integer> resultSet);

	ResponseVo<List<CategoryVo>> selectCategory(int categoryId);

	int addCategory(int parentId,String categoryName);

	ResponseVo delete(int categoryId);
}
