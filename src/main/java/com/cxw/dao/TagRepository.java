package com.cxw.dao;

import com.cxw.po.Tag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {
    //定义方法规则 根据Name查询
    Tag findByName(String name);
}
