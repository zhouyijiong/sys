package com.zyj.disk.controller.user;

import com.zyj.disk.service.user.UserService;
import com.zyj.disk.sys.annotation.verify.ParamsCheck;
import com.zyj.disk.sys.annotation.verify.ParamsCheck.Param;
import com.zyj.disk.sys.entity.Response;
import com.zyj.disk.sys.entity.Rules;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 用户控制器 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UseController{
	private final UserService userService;

	/**
	 * 注册 API
	 * @param username 要注册的用户名
	 * @param password 要注册的密码
	 * */
	@PostMapping("/registered")
	@ParamsCheck({
			@Param(name = "username",regex = Rules.NUM_CHAR_LOW),
			@Param(name = "password",regex = Rules.NUM_CHAR_LOW_32)
	})
	public Response<String> registered(String username,String password){
		userService.registered(username,password);
		return userService.result(username);
	}

	/**
	 * 登录 API
	 * @param username 登陆用户名
	 * @param password 登录密码
	 * */
	@PostMapping("/login")
	@ParamsCheck({
			@Param(name = "username",regex = Rules.NUM_CHAR_LOW),
			@Param(name = "password",regex = Rules.NUM_CHAR_LOW_32)
	})
	public Response<String> login(String username,String password){
		userService.login(username,password);
		return userService.result(username);
	}

	@GetMapping("/testInsert")
	public Response<String> testInsert(){
		userService.testInsert();
		return Response.success(null);
	}

	@GetMapping("/testDelete")
	public Response<String> testDelete(){
		userService.testDelete();
		return Response.success(null);
	}
}