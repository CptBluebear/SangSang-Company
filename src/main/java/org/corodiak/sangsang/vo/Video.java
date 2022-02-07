package org.corodiak.sangsang.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Video {
	private int idx;
	private String title;
	private String link;
	private String type;
	private int length;
	private int classIdx;
}
