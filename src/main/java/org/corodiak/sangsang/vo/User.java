package org.corodiak.sangsang.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
	private String idx;
	private String id;
	@JsonIgnore
	private String pw;
	private String name;
	@JsonIgnore
	private String role;
}