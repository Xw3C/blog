package com.cxw.service;

import com.cxw.NotFoundException;
import com.cxw.dao.TypeRepository;
import com.cxw.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

//接口方法的实现
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;


    @Transactional //放到事务
    @Override
    //保存功能
    public Type saveType(Type type) {

        return typeRepository.save(type);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    //根据ID查询Type
    public Type getType(Long id) {

        return typeRepository.findOne(id);
    }

    @Transactional
    @Override
    //分页，jpa已经封装了一个方法
    public Page<Type> listType(Pageable pageable) {

        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Transactional
    @Override
    //更新功能
    public Type updateType(Long id, Type type) {
        //根据ID先查询到一个Type对象
        Type t = typeRepository.findOne(id);
        //判断ID是否查询到
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        //调用BeanUtils中的copyProperties方法，将Type里面的值复制到t中
        BeanUtils.copyProperties(type, t);

        return typeRepository.save(t);
    }

    @Transactional
    @Override
    //删除功能
    public void deleteType(Long id) {

        typeRepository.delete(id);
    }
}
