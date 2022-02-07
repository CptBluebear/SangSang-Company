package org.corodiak.sangsang.controller;

import java.util.Map;

import org.corodiak.sangsang.service.ProbService;
import org.corodiak.sangsang.service.UserService;
import org.corodiak.sangsang.type.Message;
import org.corodiak.sangsang.type.StatusEnum;
import org.corodiak.sangsang.util.JWTUtil;
import org.corodiak.sangsang.vo.Prob;
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
@RequestMapping("/prob")
public class ProbController {

	@Autowired
	UserService userService;
	
	@Autowired
	ProbService probService;
	
	@RequestMapping(value = "/{idx}", method = RequestMethod.GET)
	@ApiOperation(value = "문제 정보", notes = "문제 정보")
	public ResponseEntity<Message> getProb(
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
		
		Prob prob = probService.getProb(idx);
		
		if(prob == null) {
			message.setStatus(StatusEnum.NOT_FOUND);
			message.setMessage("PROB NOT FOUND");
			return new ResponseEntity<Message>(message, HttpStatus.NOT_FOUND);
		}
		
		message.setMessage("OK");
		message.setStatus(StatusEnum.OK);
		message.setData(prob);
		
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}
	
}
