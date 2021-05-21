package com.imooc.mall.service;

import com.imooc.mall.pojo.User;
import com.imooc.mall.vo.ResponseVo;
import com.imooc.mall.vo.Statistic;

import java.util.List;

/**
 * Created by 廖师兄
 */
public interface IUserService {

	/**
	 * 注册
	 */
	ResponseVo<User> register(User user);

	/**
	 * 登录
	 */
	ResponseVo<User> login(String username, String password);

	ResponseVo<List<User>> userList();

	Integer userCount();

	ResponseVo<Statistic> getStatistic();

	ResponseVo userDelete(int userId);

	ResponseVo userModify(User user);
}
