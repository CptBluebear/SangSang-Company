package org.corodiak.sangsang.controller;

import java.util.HashMap;
import java.util.Map;

import org.corodiak.sangsang.service.UserService;
import org.corodiak.sangsang.type.Message;
import org.corodiak.sangsang.type.StatusEnum;
import org.corodiak.sangsang.util.JWTUtil;
import org.corodiak.sangsang.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	@ApiOperation(value = "로그인", notes = "로그인")
	public ResponseEntity<Message> signin(
			@RequestParam(value = "id", required = false)String id,
			@RequestParam(value = "pw", required = false)String pw
			) {
		
		Message message = new Message();
		
		if(!(StringUtils.hasLength(id) && StringUtils.hasLength(pw))) {
			message.setMessage("ID or PASSWORD EMPTY");
			return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
		}
		
		String token = userService.signin(id, pw);
		if(!StringUtils.hasLength(token)) {
			message.setMessage("ID or PASSWORD INCORRECT");
			message.setStatus(StatusEnum.UNAUTHORIZED);
			return new ResponseEntity<Message>(message, HttpStatus.UNAUTHORIZED);
		}
		
		message.setStatus(StatusEnum.OK);
		message.setMessage("OK");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		message.setData(map);
		
		HttpHeaders headers =  new HttpHeaders();
		headers.add("Set-Cookie", "token="+token+"; Max-Age=31536000; Path=/; SameSite=Lax; HttpOnly");
		
		return new ResponseEntity<Message>(message, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	@ApiOperation(value = "회원가입", notes = "회원가입")
	public ResponseEntity<Message> signup(
			@RequestParam(value = "id", required = false)String id,
			@RequestParam(value = "pw", required = false)String pw,
			@RequestParam(value = "name", required = false)String name
			) {
		
		Message message = new Message();
		
		if(!(StringUtils.hasLength(id) && StringUtils.hasLength(pw) && StringUtils.hasLength(name))) {
			message.setMessage("REQUIRED DATA IS MISSING");
			return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
		}
		
		if(!userService.signup(id, pw, name, "ROLE_GUEST")) {
			message.setMessage("SIGNUP FAILED");
			return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
		}
		
		message.setStatus(StatusEnum.OK);
		message.setMessage("OK");
		
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ApiOperation(value = "로그아웃", notes = "로그아웃")
	public ResponseEntity<Message> logout(
			@CookieValue(name = "token", required = false)String token
			) {
		Message message = new Message();
		message.setStatus(StatusEnum.OK);
		message.setMessage("LOGOUT SUCCESS");
		
		try {
			userService.logout(token);
		} catch (Exception e) {} //로그아웃이니깐 제어 안함
		
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/info", method = RequestMethod.GET)
	@ApiOperation(value = "내 정보", notes = "내 정보")
	public ResponseEntity<Message> userInfo(
			@CookieValue(name = "token", required = false, defaultValue = "")String token
			) {
		
		System.out.println(token);
		Message message = new Message();
		Map<String, Object> claims = JWTUtil.validateToken(token);
		if(userService.isDestroyedToken(token) || claims == null) {
			message.setStatus(StatusEnum.UNAUTHORIZED);
			message.setMessage("USER TOKEN CANNOT BE VERIFIED");
			return new ResponseEntity<Message>(message, HttpStatus.UNAUTHORIZED);
		}
		int userIdx = Integer.parseInt((String)claims.get("idx"));
		User user = userService.getUserInfo(userIdx);
		
		message.setStatus(StatusEnum.OK);
		message.setMessage("USER INFORMATION FOUND");
		message.setData(user);
		
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}
}
