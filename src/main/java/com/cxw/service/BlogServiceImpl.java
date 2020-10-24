package com.cxw.service;

import com.cxw.NotFoundException;
import com.cxw.dao.BlogRepository;
import com.cxw.po.Blog;
import com.cxw.po.Type;
import com.cxw.util.MarkdownUtils;
import com.cxw.util.MyBeanUtils;
import com.cxw.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
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

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        //首先根据获取一个blog
        Blog blog = blogRepository.findOne(id);
        //如果获取的blog为空，则抛出一个异常
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }

        //new一个新的Blog对象b
        Blog b = new Blog();
        //将blog的复制给b，此目的是为了用b对象代替blog对象，去处理content。若直接处理blog对象，
        //会改变数据库原本内容变成html语句，后面无法修改博客内容。
        BeanUtils.copyProperties(blog,b);
        //获取blog中的content
        String content = b.getContent();
        //利用markdownUtils工具类，将具有markdown语法格式的content传入
        //处理完后的content，重新赋值给b
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        //点击数累加
        blogRepository.updateViews(id);
        return b;
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
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }

                //查询 是否推荐
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                //使用cq进行查询
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }


    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //关联表查询
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = new PageRequest(0, size, sort);
        return blogRepository.findTop(pageable);
    }


    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        //当ID是null时新增，代表是新增
        if (blog.getId() == null) {
            //保存方法
            blog.setCreateTime(new Date());//设置一个新日期
            blog.setUpdateTime(new Date());//新的更新日期
            blog.setViews(0);//浏览次数设置为0
        } else {
            blog.setUpdateTime(new Date());//新的更新日期
        }


        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        //更新
        Blog b = blogRepository.findOne(id);//先根据ID查询对象
        //如果查询的对象不存在，则说明没有这个对象，无法更新
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        //根据id来删除
        blogRepository.delete(id);
    }
}
