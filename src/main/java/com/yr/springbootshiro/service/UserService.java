package com.yr.springbootshiro.service;

import com.yr.springbootshiro.dao.UserDao;
import com.yr.springbootshiro.entity.UUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public UUser getId(String email){
        return userDao.getId(email);
    }

    public List<String> getEmail(String email){
        List<String> list = userDao.getEmail(email);
        return list;
    }

    public String getMark(String url){
        String mark = userDao.getMark(url);
        return mark;
    }


}
