package com.huangjinyuanye.walletserver.dao;

import com.huangjinyuanye.walletserver.pojo.AccountBook;
import com.huangjinyuanye.walletserver.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AccountBookRepository extends JpaRepository<AccountBook, Integer> {
    AccountBook saveAndFlush(AccountBook accountBook);

    AccountBook findById(int id);

//    @Query(value = "select * from update_task_instance where update_task_id = ?1 order by createtime desc limit 1", nativeQuery = true)
//    default UpdateTaskInstance findLatestByUpdateTaskId(int updateTaskId) {
//        return null;
//    }
}
