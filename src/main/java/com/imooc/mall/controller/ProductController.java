package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.form.ProductIUpdateForm;
import com.imooc.mall.form.SetSaleStatusForm;
import com.imooc.mall.pojo.Product;
import com.imooc.mall.service.IProductService;
import com.imooc.mall.vo.ProductDetailVo;
import com.imooc.mall.vo.ProductVo;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.List;


@RestController
public class ProductController {

	@Autowired
	private IProductService productService;

	@GetMapping("/products")
	public ResponseVo<PageInfo> list(@RequestParam(required = false) Integer categoryId,
									 @RequestParam(required = false, defaultValue = "1") Integer pageNum,
									 @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
		return productService.list(categoryId, pageNum, pageSize);
	}

	@GetMapping("/products/{productId}")
	public ResponseVo<ProductDetailVo> detail(@PathVariable Integer productId) {
		return productService.detail(productId);
	}

	@GetMapping("/products/detail")
	public ResponseVo<ProductDetailVo> productDetail(@RequestParam Integer productId) {
		return productService.detail(productId);
	}

	@GetMapping("/products/list")
	public ResponseVo<List<ProductVo>> productList() {
		return productService.getProducts();
	}

	@PostMapping("/products/set_sale_status")
	public ResponseVo setSaleStatus(@Valid @RequestBody SetSaleStatusForm form){
		return productService.productStatus(form.getProductId(),form.getStatus());
	}

	@PostMapping("products/save")
	public ResponseVo save(@Valid  @RequestBody ProductIUpdateForm form){
		Product product = new Product();
		BeanUtils.copyProperties(form,product);
		return productService.productSave(product);
	}

	private String imageUpload(MultipartFile file,HttpServletRequest request) throws RuntimeException, IOException {
		if (file != null) {// ?????????????????????????????????
			String path = null;// ????????????
			String type = null;// ????????????
			String fileName = file.getOriginalFilename();// ???????????????
			System.out.println("????????????????????????:" + fileName); // ??????????????????
			type = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".") + 1) : null;
			if (type != null) {// ??????????????????????????????
				if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase()) || "JPEG".equals(type.toUpperCase())) { // ????????????????????????????????????????????????
					String realPath = getPhotoBasePath(request);
					String trueFileName =System.currentTimeMillis() + fileName; // ?????????????????????????????????
					path = realPath +trueFileName;
					file.transferTo(new File(path));
					return "http://localhost:8080/image/"+trueFileName;
				} else {
					throw new RuntimeException("?????????????????????????????????,????????????????????????");
				}
			} else {
				throw new RuntimeException("??????????????????");
			}
		} else {
			throw new RuntimeException("????????????");
		}
	}

	private String getPhotoBasePath(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/");
	}

	@GetMapping( "/image/{phone_path}")
	public void loadPhotoPath(@PathVariable String phone_path,HttpServletRequest request,HttpServletResponse response){
		String basePath = getPhotoBasePath(request);
		String url = request.getRequestURI();
		String fileName  = basePath+phone_path;
		File file = new File(fileName);
		if(file.exists()){
			responseFile(response,file);
		}else {
			try {
				response.sendError(1000,"???????????????");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void responseFile(HttpServletResponse response, File imgFile) {
		try{
			InputStream is = new FileInputStream(imgFile);
			OutputStream os = response.getOutputStream();
			byte[] buffer = new byte[1024]; // ????????????????????????
			while (is.read(buffer) != -1) {
				os.write(buffer);
			}
			os.flush();
		} catch (IOException ioe){
			ioe.printStackTrace();
			try {
				response.sendError(400, ioe.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	@PostMapping("/products/delete/{productId}")
	public ResponseVo productDelete(@PathVariable Integer productId){
		return productService.productDelete(productId);
	}

}
