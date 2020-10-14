package com.cxw.service;

import com.cxw.po.Blog;
import com.cxw.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    //根据ID查询
    Blog getBlog(Long id);

    //分页查询 一组数据
    //查询的结果封装成一组对象
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    //新增Blog
    Blog saveBlog(Blog blog);

    //修改Blog
    Blog updateBlog(Long id , Blog blog);

    //删除Blog
    void deleteBlog(Long id);


}
