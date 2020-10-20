package com.cxw.service;

import com.cxw.po.Tag;
import com.cxw.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    //新增方法接口
    Tag saveTag(Tag tag);

 	//查询方法
    Tag getTag(Long id);
    //根据名称查询
    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTagTop(Integer size);

    List<Tag> listTag(String ids);

    Tag updateTag(Long id, Tag type);

    //根据主键删除
    void deleteTag(Long id);
}
