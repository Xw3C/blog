package com.cxw.web;

import com.cxw.po.Blog;
import com.cxw.po.Comment;
import com.cxw.service.BlogService;
import com.cxw.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;


    @Value("${comment.avatar}")
    private String avatar;


    //评论
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {

        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        //返回评论片段
        return "blog :: commentList";
    }


    @PostMapping("/comments")
    public String post(Comment comment) {
        //先获取comment中的blog中的id
        Long blogId = comment.getBlog().getId();
        //根据blogId查询Blog对象，
        comment.setBlog(blogService.getBlog(blogId));
        comment.setAvatar(avatar);
        commentService.saveComment(comment);
        return "redirect:/comments/" + comment.getBlog().getId();
    }
}
