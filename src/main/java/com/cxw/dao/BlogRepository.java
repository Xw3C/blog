package com.cxw.dao;

import com.cxw.po.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

//JpaSpecificationExecutor集成了动态的组合查询方法 
public interface BlogRepository extends JpaRepository<Blog,Long> , JpaSpecificationExecutor<Blog>{

}
