package org.corodiak.sangsang.service;

import java.util.List;

import org.corodiak.sangsang.mapper.ProbMapper;
import org.corodiak.sangsang.vo.Prob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProbService {

	@Autowired
	ProbMapper probMapper;
	
	public List<Prob> getClassProb(int idx) {
		return probMapper.findProbByClassIdx(idx);
	}
	
	public Prob getProb(int idx) {
		return probMapper.findProbByIdx(idx);
	}
}
