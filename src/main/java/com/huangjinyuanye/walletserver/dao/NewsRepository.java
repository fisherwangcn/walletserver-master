package com.huangjinyuanye.walletserver.dao;

import com.huangjinyuanye.walletserver.pojo.News;
import com.huangjinyuanye.walletserver.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface NewsRepository extends JpaRepository<News, Integer> {
    News saveAndFlush(News news);

    News findById(int id);

    List<News> findAll();
}
