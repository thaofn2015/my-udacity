package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesServices {

	@Autowired
	private NotesMapper notesMapper;

	public List<Notes> getAll(Integer userId) {
		return notesMapper.getAll(userId);
	}

	public Notes get(Integer noteId, Integer userId) {
		return notesMapper.get(noteId, userId);
	}

	public List<Notes> getByTitle(String title) {
		return notesMapper.getByTitle(title);
	}

	public boolean isExist(Integer noteId, Integer userId) {
		return notesMapper.get(noteId, userId) != null;
	}

	public boolean add(Notes note) {
		return notesMapper.add(note);
	}

	public boolean update(Notes note) {
		return notesMapper.update(note);
	}

	public boolean delete(Integer noteId, Integer userId) {
		return notesMapper.delete(noteId, userId);
	}
}
