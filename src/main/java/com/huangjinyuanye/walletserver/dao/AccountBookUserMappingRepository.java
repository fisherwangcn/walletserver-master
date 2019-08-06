package com.huangjinyuanye.walletserver.dao;

import com.huangjinyuanye.walletserver.pojo.ABMappingPK;
import com.huangjinyuanye.walletserver.pojo.AccountBook;
import com.huangjinyuanye.walletserver.pojo.AccountBookUserMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AccountBookUserMappingRepository extends JpaRepository<AccountBookUserMapping, ABMappingPK> {
    AccountBookUserMapping saveAndFlush(AccountBookUserMapping accountBookUserMapping);

    AccountBookUserMapping findByAccountBookIdAndUserId(int accountBookId,int userId);

    List<AccountBookUserMapping> findAllByAccountBookId(int bookId);

    List<AccountBookUserMapping> findAllByUserId(int userId);

    @Query(value = "select * from account_book_user_mapping where user_id = ?1 order by createtime asc limit 1", nativeQuery = true)
    AccountBookUserMapping findInitAccountBook(int userId);

}
