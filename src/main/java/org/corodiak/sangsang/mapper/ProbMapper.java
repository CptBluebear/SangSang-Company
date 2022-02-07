package org.corodiak.sangsang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.corodiak.sangsang.vo.Prob;

@Mapper
public interface ProbMapper {

	public List<Prob> findProbByClassIdx(@Param("idx")int idx);
	public Prob findProbByIdx(@Param("idx")int idx);
	
}
