package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

	@Select("SELECT credentialId, url, username, key, password, userid " +
			"FROM CREDENTIALS " +
			"WHERE userid = #{userId}")
	List<Credentials> getAll(Integer userId);

	@Select("SELECT credentialId, url, username, key, password, userid " +
			"FROM CREDENTIALS " +
			"WHERE userid = #{userId} and credentialid = #{credentialId}")
	Credentials get(Integer credentialId, Integer userId);

	@Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
			"VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
	boolean add(Credentials credential);

	@Update("UPDATE CREDENTIALS SET url = #{url}, key = #{key}, username = #{username}, password = #{password} " +
			"WHERE credentialid = #{credentialId} and userid = #{userId}")
	boolean update(Credentials credential);

	@Delete("DELETE FROM CREDENTIALS " +
			"WHERE userid = #{userId} and credentialid = #{credentialId}")
	boolean delete(Integer credentialId, Integer userId);

}
