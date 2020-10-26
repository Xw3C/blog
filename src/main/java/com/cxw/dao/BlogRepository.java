package com.cxw.dao;

import com.cxw.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    //?1代表第一个参数
    //sql语句 select * from t_blog where title like '%内容%'
    @Query("select b from Blog b where b.title like ?1 or b.description like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    //自定义更新，浏览次数
    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views + 1 where b.id = ?1")
    int updateViews(Long id);


    //SQL语言： select date_format(b.update_time,'%Y') as year from t_blog b Group By year ORDER BY year DESC;
    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc ")     //jpql语句
    List<String> findGroupYear();

    //SQL语言： select * from t_blog b where date_format(b.update_time,'%Y')='2020';
    //?1代表第一个占位，即findByYear方法中第一个参数，year
    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1 order by b.updateTime DESC")
    List<Blog> findByYear(String year);
}
