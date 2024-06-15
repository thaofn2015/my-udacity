package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
	@Select("SELECT noteid, notetitle, notedescription, userid, userid " +
			"FROM NOTES " +
			"WHERE userid = #{userId}")
	List<Notes> getAll(Integer userId);

	@Select("SELECT noteid, notetitle, notedescription, userid, userid " +
			"FROM NOTES " +
			"WHERE noteid = #{noteId} and userid = #{userId}")
	Notes get(Integer noteId, Integer userId);
	
	@Select("SELECT noteid, notetitle, notedescription, userid, userid " +
			"FROM NOTES " +
			"WHERE notetitle = #{title}")
	List<Notes> getByTitle(String title);

	@Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
			"VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
	boolean add(Notes note);

	@Update("UPDATE NOTES SET notetitle = #{noteTitle}, noteDescription = #{noteDescription} " +
			"WHERE noteid = #{noteId} and userid = #{userId}")
	boolean update(Notes note);

	@Delete("DELETE FROM NOTES " +
			"WHERE noteid = #{noteId} and userid = #{userId}")
	boolean delete(Integer noteId, Integer userId);
}
