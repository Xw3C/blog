package com.cxw.service;

import com.cxw.dao.CommentRepository;
import com.cxw.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommentSerivceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    //根据Comment中的Blog的id去查询
    public List<Comment> listCommentByBlogId(Long blogId) {
        //根据createTime进行排序
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return commentRepository.findByBlogId(blogId, sort);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();

        //parentComment=-1说明是这条评论是父评论，初始评论，不是-1说明是子评论，回复某人
        if (parentCommentId != -1) {
            //拿到父类评论的对象
            comment.setParentComment(commentRepository.findOne(parentCommentId));
        } else {
            //是-1
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
}
