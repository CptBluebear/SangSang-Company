package org.corodiak.sangsang.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.corodiak.sangsang.vo.User;

@Mapper
public interface UserMapper {

	public User findUserById(@Param("id")String id);
	public void insertUser(@Param("User")User user);
	public User findUserByIdx(@Param("idx")int idx);
	
	@Select("SELECT COUNT(*) FROM DESTROYEDTOKEN WHERE token=#{token}")
	public int findToken(@Param("token")String token);
	@Insert("INSERT INTO DESTROYEDTOKEN VALUES(#{token})")
	public void insertToken(@Param("token")String token);
}
