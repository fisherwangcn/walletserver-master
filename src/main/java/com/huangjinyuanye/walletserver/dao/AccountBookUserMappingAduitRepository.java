package com.huangjinyuanye.walletserver.dao;

import com.huangjinyuanye.walletserver.pojo.AccountBook;
import com.huangjinyuanye.walletserver.pojo.AccountBookUserMappingAduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AccountBookUserMappingAduitRepository extends JpaRepository<AccountBookUserMappingAduit, Integer> {
    AccountBookUserMappingAduit saveAndFlush(AccountBookUserMappingAduit accountBookUserMappingAduit);

    AccountBookUserMappingAduit findById(int id);

    List<AccountBookUserMappingAduit> findAllByUserIdAndType(int userId,int type);
    List<AccountBookUserMappingAduit> findAllByUserId(int userId);

    List<AccountBookUserMappingAduit> findAllByUserIdAndAccountBookId(int userId,int accountBookId);

    @Query(value = "select a.* from \n" +
            "(select * from account_book_user_mapping_aduit where type = ?2) a\n" +
            "join\n" +
            "(select * from account_book where owner_id = ?1 ) b\n" +
            "on \n" +
            "a.account_book_id = b.id", nativeQuery = true)
    List<AccountBookUserMappingAduit> findAllByUserIdAndTypeNeedAduit(int userId, int type);

    @Query(value = "select a.* from \n" +
            "(select * from account_book_user_mapping_aduit) a\n" +
            "join\n" +
            "(select * from account_book where owner_id = ?1 ) b\n" +
            "on \n" +
            "a.account_book_id = b.id", nativeQuery = true)
    List<AccountBookUserMappingAduit> findAllByUserIdNeedAduit(int userId);

}
