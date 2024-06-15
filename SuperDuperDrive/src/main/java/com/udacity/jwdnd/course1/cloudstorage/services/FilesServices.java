package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FilesServices {

	@Autowired
	FileMapper filesSUploadMapper;

	public List<File> findAll(Integer userId) {
		return filesSUploadMapper.getAll(userId);
	}

	public boolean save(File file, Integer userId) throws IOException {

		return filesSUploadMapper.add(file);
	}

	public boolean deleteFileById(Integer fileId, Integer userId) {
		return filesSUploadMapper.delete(fileId, userId);
	}

	public boolean isDuplicateName(String fileName, Integer userId) {
		return filesSUploadMapper.isExist(fileName, userId) > 0;
	}

	public File get(Integer fileId, Integer userId) {
		return filesSUploadMapper.getByUser(fileId, userId);
	}

}
