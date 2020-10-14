package com.cxw.service;

import com.cxw.NotFoundException;
import com.cxw.dao.BlogRepository;
import com.cxw.po.Blog;
import com.cxw.po.Type;
import com.cxw.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        //根据主键ID来查询
        return blogRepository.findOne(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        //分页查询的方法
        //blog传递的是条件组合
        //由3个if组成的动态条件查询
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //查询条件
                List<Predicate> predicates = new ArrayList<>();

                //构造like查询的调剂
                //构造标题查询
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    //获取传递过来的值属性 "title"是属性的名字、getTitle是属性的值
                    //like查询的条件
                    predicates.add(cb.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                //分类查询调剂
                if (blog.getTypeId()!= null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id") ,blog.getTypeId()));
                }

                //查询 是否推荐
                if (blog.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                //使用cq进行查询
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        //保存方法
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        //更新
        Blog b = blogRepository.findOne(id);//先根据ID查询对象
        //如果查询的对象不存在，则说明没有这个对象，无法更新
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        //将blog中的值赋给b
        BeanUtils.copyProperties(b, blog);
        //调用blogRepository中的save方法将b对象保存
        return blogRepository.save(b);
    }

    @Override
    public void deleteBlog(Long id) {
        //根据id来删除
        blogRepository.delete(id);
    }
}
