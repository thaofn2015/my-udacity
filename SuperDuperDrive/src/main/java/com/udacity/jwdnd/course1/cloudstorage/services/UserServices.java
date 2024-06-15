package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.common.CommonUtil;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServices {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private HashService hashService;

	public Users getByUsername(String username) {
		return userMapper.get(username);
	}

	public boolean add(Users users) {
		String salt = CommonUtil.keyGen();

		String passHashed = hashService.getHashedValue(users.getPassword(), salt);

		users.setSalt(salt);
		users.setPassword(passHashed);

		return userMapper.add(users);
	}

	public String validation(Users users) {
		String errorMessage = "";
		if (StringUtils.isEmpty(users.getFirstName())
				&& StringUtils.isEmpty(users.getLastName())
				&& StringUtils.isEmpty(users.getUsername())
				&& StringUtils.isEmpty(users.getPassword())) {
			errorMessage = "User info must be input!!!";
		} else if (getByUsername(users.getUsername()) != null) {
			errorMessage = "User is exists!!!";
		}
		return errorMessage;
	}
}
