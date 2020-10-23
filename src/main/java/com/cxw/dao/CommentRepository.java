package com.cxw.dao;

import com.cxw.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    //由Comment的创新时间，使用Sort进行排序
    //findByBlogId 根据id和ParentComment为空
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
