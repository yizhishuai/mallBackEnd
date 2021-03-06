package com.imooc.mall.controller;

import com.imooc.mall.consts.MallConst;
import com.imooc.mall.form.*;
import com.imooc.mall.pojo.User;
import com.imooc.mall.service.IUserService;
import com.imooc.mall.vo.ResponseVo;
import com.imooc.mall.vo.Statistic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@RestController
@Slf4j
public class UserController {

	@Autowired
	private IUserService userService;

	@PostMapping("/user/register")
	public ResponseVo<User> register(@Valid @RequestBody UserRegisterForm userForm) {
		User user = new User();
		BeanUtils.copyProperties(userForm, user);
		//dto
		return userService.register(user);
	}

	@PostMapping("/user/login")
	public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
								  HttpSession session) {
		ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());

		//设置Session
		session.setAttribute(MallConst.CURRENT_USER, userResponseVo.getData());
		log.info("/login sessionId={}", session.getId());

		return userResponseVo;
	}

	//session保存在内存里，改进版：token+redis
	@GetMapping("/user")
	public ResponseVo<User> userInfo(HttpSession session) {
		log.info("/user sessionId={}", session.getId());
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return ResponseVo.success(user);
	}

	//session保存在内存里，改进版：token+redis
	@GetMapping("/user/list")
	public ResponseVo<List<User>> userList() {
		return userService.userList();
	}


	/**
	 * {@link TomcatServletWebServerFactory} getSessionTimeoutInMinutes
	 */
	@PostMapping("/user/logout")
	public ResponseVo logout(HttpSession session) {
		log.info("/user/logout sessionId={}", session.getId());
		session.removeAttribute(MallConst.CURRENT_USER);
		return ResponseVo.success();
	}



	@GetMapping("/statistic/base_count")
	public ResponseVo<Statistic> baseCount(){
		return userService.getStatistic();
	}


	@PostMapping("/user/delete")
	public ResponseVo userDelete(@Valid @RequestBody UserIDFrom from){
		return userService.userDelete(from.getUserId());
	}


	@PostMapping("/user/modify")
	public ResponseVo userModify(@Valid @RequestBody UserModifyForm form){
		User user = new User();
		 BeanUtils.copyProperties(form,user);
		 return userService.userModify(user);
	}
}
