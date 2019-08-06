package com.huangjinyuanye.walletserver.dao;

import com.huangjinyuanye.walletserver.pojo.SmsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SmsInfoRepository extends JpaRepository<SmsInfo, Integer> {
    SmsInfo saveAndFlush(SmsInfo smsInfo);

    SmsInfo findById(int id);

    List<SmsInfo> findAll();

    //获得未过期的数据
    @Query(value = "select * from sms_info p where p.phone = ?1 and p.app_id=?2 and isused = ?3 and expiredtime>unix_timestamp(now()) order by createtime desc limit 1",nativeQuery = true)
    SmsInfo findByPhoneAndAppIdAndIsused(String phone, int app_id, int used);
}
