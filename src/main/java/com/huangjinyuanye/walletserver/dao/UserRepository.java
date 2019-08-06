package com.huangjinyuanye.walletserver.dao;

import com.huangjinyuanye.walletserver.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
    User saveAndFlush(User user);

    User findById(int id);

    @Query(value = "select * from user where phone = ?1 limit 1", nativeQuery = true)
    User findPhoneExists(String phone);

    User findByPhoneAndPasswd(String phone,String passwd);


//    @Query(value = "select * from update_task_instance where update_task_id = ?1 order by createtime desc limit 1", nativeQuery = true)
//    default UpdateTaskInstance findLatestByUpdateTaskId(int updateTaskId) {
//        return null;
//    }
}
