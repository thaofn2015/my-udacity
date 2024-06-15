package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.common.CommonUtil;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialsServices {

	@Autowired
	private CredentialsMapper credentialsMapper;

	public List<Credentials> getAll(Integer userId) {
		List<Credentials> lst = credentialsMapper.getAll(userId);

		lst.stream().forEach(crt -> {
			crt.setPasswordTxt(CommonUtil.decryptString(crt.getPassword(), crt.getKey()));
		});

		return lst;
	}

	public Credentials get(Integer creId, Integer userId) {
		return credentialsMapper.get(creId, userId);
	}

	public boolean isExist(Integer creId, Integer userId) {
		return credentialsMapper.get(creId, userId) != null;
	}

	public boolean add(Credentials credential) {
		String encodedKey = CommonUtil.keyGen();

		credential.setPassword(CommonUtil.encryptString(credential.getPassword(), encodedKey));
		credential.setKey(encodedKey);

		return credentialsMapper.add(credential);
	}

	public boolean update(Credentials credential) {
		String encodedKey = CommonUtil.keyGen();

		credential.setPassword(CommonUtil.encryptString(credential.getPassword(), encodedKey));
		credential.setKey(encodedKey);

		return credentialsMapper.update(credential);
	}

	public boolean delete(Integer creId, Integer userId) {
		return credentialsMapper.delete(creId, userId);
	}

}
