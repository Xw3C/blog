package com.cxw.service;

import com.cxw.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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

    //获取所有的参数
    List<Type> listType();

    //定义一个结构方法，获取一个list
    List<Type> listTypeTop(Integer size);

    //修改
    Type updateType(Long id,Type type);

    //根据主键删除
    void deleteType(Long id);
}
