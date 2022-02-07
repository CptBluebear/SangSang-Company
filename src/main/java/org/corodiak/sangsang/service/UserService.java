package org.corodiak.sangsang.service;

import java.util.HashMap;
import java.util.Map;

import org.corodiak.sangsang.mapper.UserMapper;
import org.corodiak.sangsang.util.JWTUtil;
import org.corodiak.sangsang.vo.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	UserMapper userMapper;
	
	public String signin(String id, String pw) {
		
		User user = userMapper.findUserById(id);
		if(user == null)
			return null;
		if(!BCrypt.checkpw(pw, user.getPw()))
			return null;
		
		Map<String, Object> payload = new HashMap<String, Object>();
		payload.put("idx", user.getIdx());
		payload.put("role", user.getRole());
		
		return JWTUtil.createToken(payload);
	}
	
	public boolean signup(String id, String pw, String name, String role) {
		
		User user = new User();
		user.setId(id);
		user.setPw(BCrypt.hashpw(pw, BCrypt.gensalt()));
		user.setName(name);
		user.setRole(role);
		
		try {
			userMapper.insertUser(user);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public boolean isDestroyedToken(String token) {
		if(userMapper.findToken(token) > 0)
			return true;
		return false;
	}
	
	public void logout(String token) throws Exception {
		userMapper.insertToken(token);
	}
	
	public User getUserInfo(int idx) {
		return userMapper.findUserByIdx(idx);
	}
}
