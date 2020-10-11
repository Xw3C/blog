package com.cxw.dao;

import com.cxw.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type,Long> {

    //定义方法规则 根据Name查询
    Type findByName(String name);

}
