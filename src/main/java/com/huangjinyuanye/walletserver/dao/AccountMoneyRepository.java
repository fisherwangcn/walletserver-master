package com.huangjinyuanye.walletserver.dao;

import com.huangjinyuanye.walletserver.pojo.AccountBook;
import com.huangjinyuanye.walletserver.pojo.AccountMoney;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AccountMoneyRepository extends JpaRepository<AccountMoney, Integer> {
    AccountMoney saveAndFlush(AccountMoney accountMoney);

    AccountMoney findById(int id);

    List<AccountMoney> findAllByUserIdAndYearAndMonthAndBookId(int user_id,int year,int month,int bookId);

}
