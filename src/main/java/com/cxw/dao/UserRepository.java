package com.cxw.dao;

import com.cxw.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    //JpaRepository支持接口规范方法名查询，接口中定义的查询方法符合它的命名规则，就可以不用写实现
    User findByUsernameAndPassword(String username,String password);
}
