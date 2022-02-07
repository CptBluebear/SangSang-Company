package org.corodiak.sangsang.service;

import java.util.List;

import org.corodiak.sangsang.mapper.ClassMapper;
import org.corodiak.sangsang.vo.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassService {

	@Autowired
	ClassMapper classMapper;
	
	public List<Class> getUserClass(int idx) {
		return classMapper.findClassByUserIdx(idx);
	}
	
	public Class getClassInfo(int idx) {
		return classMapper.findClassByIdx(idx);
	}
	
	public List<Class> getAllClass() {
		return classMapper.findAllClass();
	}
	
}
