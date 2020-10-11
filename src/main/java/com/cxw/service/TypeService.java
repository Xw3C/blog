package com.cxw.service;

import com.cxw.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//定义接口的方法
public interface TypeService {
    //新增方法接口
    Type saveType(Type type);

    //根据名称查询
    Type getTypeByName(String name);
    //查询方法
    Type getType(Long id);

    //类型分页
    Page<Type> listType(Pageable pageable);

    //修改
    Type updateType(Long id,Type type);

    //根据主键删除
    void deleteType(Long id);
}
