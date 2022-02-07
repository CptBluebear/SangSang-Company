package org.corodiak.sangsang.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Prob {
	private int idx;
	private String title;
	private String content;
	private LocalDateTime registerDate;
	private int classIdx;
}
