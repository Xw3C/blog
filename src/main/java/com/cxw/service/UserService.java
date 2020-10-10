package com.cxw.service;

import com.cxw.po.User;

public interface UserService {

    User checkUser(String username,String password);
}
