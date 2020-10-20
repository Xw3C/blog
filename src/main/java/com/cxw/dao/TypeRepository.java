package com.cxw.dao;

import com.cxw.po.Type;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {

    //定义方法规则 根据Name查询
    Type findByName(String name);

    //分类从由大到小选6个
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);

}
