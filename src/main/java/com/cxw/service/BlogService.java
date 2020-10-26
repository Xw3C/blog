package com.cxw.service;

import com.cxw.po.Blog;
import com.cxw.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    //根据ID查询
    Blog getBlog(Long id);

    //专门用于前端展示,markdown
    Blog getAndConvert(Long id);


    //分页查询 一组数据
    //查询的结果封装成一组对象
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);


    Page<Blog> listBlog(Pageable pageable);

    //根据标签Id，查询所包含的Blog
    Page<Blog> listBlog(Long tagId,Pageable pageable);

    //根据Query查询 搜索功能  重新定义一个结构方法
    Page<Blog> listBlog(String query,Pageable pageable);

    //归档功能，使用Map集合，数据之间有映射关系
    Map<String,List<Blog>> archiveBlog();

    //归档 统计博客数量
    Long countBlog();



    //推荐博客 接口
    List<Blog> listRecommendBlogTop(Integer size);
    //新增Blog
    Blog saveBlog(Blog blog);

    //修改Blog
    Blog updateBlog(Long id , Blog blog);

    //删除Blog
    void deleteBlog(Long id);


}
