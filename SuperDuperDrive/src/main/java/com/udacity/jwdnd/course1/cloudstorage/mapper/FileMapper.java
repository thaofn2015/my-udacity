package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM " +
            "FILES WHERE userid = #{userId}")
    List<File> getAll(Integer userId);

    @Select("SELECT * " +
            "FROM FILES " +
            "WHERE fileId = #{fileId} AND userid = #{userId}")
    File getByUser(Integer fileId, Integer userId);

    @Select("SELECT COUNT(1) " +
            "FROM FILES " +
            "WHERE filename = #{fileName} AND userid = #{userId}")
    int isExist(String fileName, Integer userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    boolean add(File upFile);

    @Delete("DELETE FROM FILES " +
            "WHERE fileId = #{fileId} AND userid = #{userId}")
    boolean delete(Integer fileId, Integer userId);
}
