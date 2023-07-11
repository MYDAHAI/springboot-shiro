package com.yr.springbootshiro.mapper;

import com.yr.springbootshiro.entity.UUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper{

	UUser getId(String email);

	List<String> getEmail(String email);

	String getMark(String url);

}
