package org.corodiak.sangsang.controller;

import java.util.List;
import java.util.Map;

import org.corodiak.sangsang.exception.UnauthorizedException;
import org.corodiak.sangsang.service.ClassService;
import org.corodiak.sangsang.service.ProbService;
import org.corodiak.sangsang.service.UserService;
import org.corodiak.sangsang.type.Message;
import org.corodiak.sangsang.type.StatusEnum;
import org.corodiak.sangsang.util.JWTUtil;
import org.corodiak.sangsang.vo.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/class")
public class ClassController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ClassService classService;
	
	@Autowired
	ProbService probService;
	
	@RequestMapping(value = {"/myclass", ""}, method = RequestMethod.GET)
	@ApiOperation(value = "내 강좌", notes = "내 강좌")
	public ResponseEntity<Message> getUserClasses(
			@CookieValue(name = "token", required = false, defaultValue = "")String token
			) {
		
		Message message = new Message();
		
		Map<String, Object> claims = JWTUtil.validateToken(token);
		if(userService.isDestroyedToken(token) || claims == null) {
			message.setStatus(StatusEnum.UNAUTHORIZED);
			message.setMessage("USER TOKEN CANNOT BE VERIFIED");
			
			return new ResponseEntity<Message>(message, HttpStatus.UNAUTHORIZED);
		}
		
		int userIdx = Integer.parseInt((String)claims.get("idx"));
		List<Class> list = classService.getUserClass(userIdx);
		message.setStatus(StatusEnum.OK);
		message.setMessage("OK");
		message.setData(list);
		
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{idx}", method = RequestMethod.GET)
	@ApiOperation(value = "강좌 상세정보", notes = "강좌 상세정보")
	public ResponseEntity<Message> getClassInfo(
			@CookieValue(name = "token", required = false, defaultValue = "")String token,
			@PathVariable(name = "idx", required = false)int idx
			) {
		
		Message message = new Message();
		
		Map<String, Object> claims = JWTUtil.validateToken(token);
		if(userService.isDestroyedToken(token) || claims == null) {
			message.setStatus(StatusEnum.UNAUTHORIZED);
			message.setMessage("USER TOKEN CANNOT BE VERIFIED");
			
			return new ResponseEntity<Message>(message, HttpStatus.UNAUTHORIZED);
		}
		
		Class c = classService.getClassInfo(idx);
		if(c == null) {
			message.setStatus(StatusEnum.NOT_FOUND);
			message.setMessage("CLASS NOT FOUND");
			
			return new ResponseEntity<Message>(message, HttpStatus.NOT_FOUND);
		}
		
		message.setStatus(StatusEnum.OK);
		message.setMessage("OK");
		message.setData(c);
		
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiOperation(value = "강좌 전체 리스트", notes = "강좌 전체 리스트")
	public ResponseEntity<Message> getClassList(
			@CookieValue(name = "token", required = false, defaultValue = "")String token
			) {
		
		Message message = new Message();
		
		Map<String, Object> claims = JWTUtil.validateToken(token);
		if(userService.isDestroyedToken(token) || claims == null) {
			message.setStatus(StatusEnum.UNAUTHORIZED);
			message.setMessage("USER TOKEN CANNOT BE VERIFIED");
			
			return new ResponseEntity<Message>(message, HttpStatus.UNAUTHORIZED);
		}
		
		message.setMessage("OK");
		message.setStatus(StatusEnum.OK);
		message.setData(classService.getAllClass());
		
		return new ResponseEntity<Message>(message, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{idx}/prob", method = RequestMethod.GET)
	@ApiOperation(value = "강좌에 해당하는 문제 목록", notes = "강좌에 해당하는 문제 목록")
	public ResponseEntity<Message> getClassProb(
			@CookieValue(name = "token", required = false, defaultValue = "")String token,
			@PathVariable(name = "idx")int idx
			) {
		
		Message message = new Message();
		
		Map<String, Object> claims = JWTUtil.validateToken(token);
		if(userService.isDestroyedToken(token) || claims == null) {
			message.setStatus(StatusEnum.UNAUTHORIZED);
			message.setMessage("USER TOKEN CANNOT BE VERIFIED");
			
			return new ResponseEntity<Message>(message, HttpStatus.UNAUTHORIZED);
		}
		
		message.setMessage("OK");
		message.setStatus(StatusEnum.OK);
		message.setData(probService.getClassProb(idx));
		
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}
}
