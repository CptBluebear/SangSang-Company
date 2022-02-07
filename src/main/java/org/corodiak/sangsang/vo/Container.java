package org.corodiak.sangsang.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Container {
	private String id;
	private String ip;
	private int port;
	private String description;
	private LocalDateTime createdDate;
	private int userIdx;
	private String imageId;
}
