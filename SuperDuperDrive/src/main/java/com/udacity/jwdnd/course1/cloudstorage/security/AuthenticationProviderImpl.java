package com.udacity.jwdnd.course1.cloudstorage.security;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationProviderImpl implements AuthenticationProvider {

	@Autowired
	private HashService hashService;

	@Autowired
	private UserMapper userMapper;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String password = authentication.getCredentials().toString();

		Users users = userMapper.get(authentication.getName());

		if (users != null) {
			String salt = users.getSalt();
			String pwdHashed = hashService.getHashedValue(password, salt);
 			if (users.getPassword().equals(pwdHashed)) {
				return new UsernamePasswordAuthenticationToken(users, password, new ArrayList<>());
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
