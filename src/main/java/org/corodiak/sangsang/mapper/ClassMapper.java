package org.corodiak.sangsang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.corodiak.sangsang.vo.Class;

@Mapper
public interface ClassMapper {
	
	public List<Class> findClassByUserIdx(@Param("idx")int idx);
	public Class findClassByIdx(@Param("idx")int idx);
	public List<Class> findAllClass();
	
}
