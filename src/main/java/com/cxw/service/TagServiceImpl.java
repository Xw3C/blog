package com.cxw.service;

import com.cxw.NotFoundException;
import com.cxw.dao.TagRepository;
import com.cxw.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TagServiceImpl implements TagService{
    @Autowired
    private TagRepository tagRepository;


    @Transactional //放到事务
    @Override
    //保存功能
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional //放到事务
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Transactional //放到事务
    @Override
    //分页，jpa已经封装了一个方法
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Transactional //放到事务
    @Override
    //更新功能
    public Tag updateTag(Long id, Tag tag) {
        //根据ID查询对象
        Tag t = tagRepository.findOne(id);
        //判断t是否
        if (t == null){
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }

    @Transactional //放到事务
    @Override
    public void deleteTag(Long id) {
        //根据id删除
        tagRepository.delete(id);
    }
}
