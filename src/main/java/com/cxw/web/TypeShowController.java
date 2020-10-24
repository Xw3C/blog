package com.cxw.web;


import com.cxw.po.Type;
import com.cxw.service.BlogService;
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

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    //@PathVariable是spring3.0的一个新功能：接收请求路径中占位符的值
    public String type(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long id, Model model){
        //查询全部
        List<Type> types = typeService.listTypeTop(10000);
        if (id == -1){
            //如果id==-1，说明是从首页点击分类进来的
            //赋予一个初始值，则获取第一个type的id
            id = types.get(0).getId();
        }
        //new一个blogQuery对象，里面包含typeId
        BlogQuery blogQuery = new BlogQuery();
        //将id set到blogQuery中
        blogQuery.setTypeId(id);
        //将查询到的分类传递
        model.addAttribute("types",types);
        //根据blogQuery中的id查询到的blog，以分页形式传递
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        //将传过来的id传递回去，使其被选中的分类标识改变
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
