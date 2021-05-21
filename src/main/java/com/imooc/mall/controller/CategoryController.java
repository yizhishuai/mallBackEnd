package com.imooc.mall.controller;

import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.CategoryAddFrom;
import com.imooc.mall.form.CategoryDeleteForm;
import com.imooc.mall.service.ICategoryService;
import com.imooc.mall.vo.CategoryVo;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

/**
 * Created by 廖师兄
 */
@RestController
public class CategoryController {

	@Autowired
	private ICategoryService categoryService;

	@GetMapping("/categories")
	public ResponseVo<List<CategoryVo>> selectAll() {
		return categoryService.selectAll();
	}

	@GetMapping("/category/get_category")
	public ResponseVo<List<CategoryVo>> getCategory( @RequestParam(required = false, defaultValue = "0") Integer categoryId){
		return categoryService.selectCategory(categoryId);
	}

	@PostMapping("/category/add_category")
	public ResponseVo addCategory(@Valid @RequestBody CategoryAddFrom from){
		int result = categoryService.addCategory(from.getParentId(),from.getCategoryName());
		if(result == 1){
			return ResponseVo.successByMsg("添加品类成功");
		}else {
			return ResponseVo.error(ResponseEnum.CATEGORY_ADD_ERROR);
		}
	}

	@PostMapping("/category/delete")
	public ResponseVo delete(@Valid @RequestBody CategoryDeleteForm form){
		return categoryService.delete(form.getCategoryId());
	}
}
