package com.cxw.web.admin;

import com.cxw.po.Blog;
import com.cxw.service.BlogService;
import com.cxw.service.TagService;
import com.cxw.service.TypeService;
import com.cxw.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model) {
        //初始化分类
        model.addAttribute("types",typeService.listType());
        //初始化标签
        model.addAttribute("tags",tagService.listTag());
        //初始化分类
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs";
    }


    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {
        //将数据传回前端页面
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        //返回admin下面blogs页面下面的blogList片段
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }
}
