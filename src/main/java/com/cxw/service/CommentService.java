package com.cxw.service;

import com.cxw.po.Comment;

import java.util.List;

public interface CommentService {
    //方法:获取该博客下的评论列表
    List<Comment> listCommentByBlogId(Long blogId);

    //save方法
    Comment saveComment(Comment comment);

}
