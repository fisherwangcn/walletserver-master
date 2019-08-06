package com.huangjinyuanye.walletserver.dao;

import com.huangjinyuanye.walletserver.pojo.Picture;
import com.huangjinyuanye.walletserver.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PictureRepository extends JpaRepository<Picture, Integer> {
    Picture saveAndFlush(Picture picture);

}
