package com.yr.springbootshiro.dao;

import com.yr.springbootshiro.entity.UUser;
import com.yr.springbootshiro.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据email查出当前email用户的所有信息
     * @param email
     * @return
     */
    public UUser getId(String email){
        UUser user = userMapper.getId(email);
        System.out.println("dao -> users : " + user);
        return user;
    }

    /**
     * 根据email查出当前email用户所拥有的所有权限信息
     * @param email
     * @return
     */
    public List<String> getEmail(String email){
        List<String> list = userMapper.getEmail(email);
        return list;
    }

    /**
     * 根据url查出对应的权限mark值
     * @param url
     * @return
     */
    public String getMark(String url){
        String mark = userMapper.getMark(url);
        return mark;
    }

}
