package com.cxw.web;


import com.cxw.NotFoundException;
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


@Controller
public class IndexController {

    @Autowired
    //博客内容
    private BlogService blogService;

    @Autowired
    //分类标题
    private TypeService typeService;

    @Autowired
    //标签标题
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {

        //拿到分页的数据，放到model里面
        model.addAttribute("page",blogService.listBlog(pageable));

  		//拿到分类的数据、显示分类的前6个
        model.addAttribute("types",typeService.listTypeTop(6));
		
        //拿到标签的数据
        model.addAttribute("tags",tagService.listTagTop(10));

      

        //拿到推荐博客的数据
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(6));



        return "index";
    }



    @GetMapping("/blog/{id}")
    public String blog() {
        return "blog";
    }
}
