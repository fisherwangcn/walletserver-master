package com.huangjinyuanye.walletserver.service.impl;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.huangjinyuanye.walletserver.dao.AccountBookRepository;
import com.huangjinyuanye.walletserver.dao.AccountBookUserMappingAduitRepository;
import com.huangjinyuanye.walletserver.dao.AccountBookUserMappingRepository;
import com.huangjinyuanye.walletserver.dao.AccountMoneyRepository;
import com.huangjinyuanye.walletserver.pojo.AccountBook;
import com.huangjinyuanye.walletserver.pojo.AccountBookUserMapping;
import com.huangjinyuanye.walletserver.pojo.AccountBookUserMappingAduit;
import com.huangjinyuanye.walletserver.pojo.AccountMoney;
import com.huangjinyuanye.walletserver.utils.CallenderUtil;
import com.huangjinyuanye.walletserver.utils.IThreadPoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class AccountMoneyService {

    private static final Logger logger = LoggerFactory.getLogger(AccountMoneyService.class);

    @Autowired
    AccountBookRepository accountBookRepository;

    @Autowired
    AccountBookUserMappingAduitRepository accountBookUserMappingAduitRepository;

    @Autowired
    AccountBookUserMappingRepository accountBookUserMappingRepository;

    @Autowired
    AccountMoneyRepository accountMoneyRepository;


    @Autowired
    private IThreadPoolService threadPoolService;

    private AccountMoneyService accountMoneyService;

    @PostConstruct
    public void init () {
        accountMoneyService = this;
    }
    public JSONObject account_money_update(String body) {
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", "server fail");
        try {

            JSONObject params = JSONObject.parseObject(body);

            int id = params.getIntValue("id");
            int book_id = params.getIntValue("book_id");
            int user_id = params.getIntValue("user_id");
            String comment = params.getString("comment");
            double money = params.getDoubleValue("money");
            int catecory_id = params.getIntValue("catecory_id");
            int type = params.getIntValue("type");
            int delete = params.getIntValue("delete");

            AccountBook byId = this.accountBookRepository.findById(book_id);
            if(byId==null){
                res.put("code",-2);
                res.put("msg","所选账本不存在.")
                ;
                return res;
            }


            AccountMoney accountMoney = this.accountMoneyRepository.findById(id);
            if(accountMoney==null){
                accountMoney = new AccountMoney();
            }
            if(catecory_id>0){
                accountMoney.setCatecoryId(catecory_id);
            }
            if(comment!=null){
                accountMoney.setComment(comment);
            }

            accountMoney.setBookId(book_id);

            accountMoney.setCreatetime((int) (System.currentTimeMillis()/1000));
            accountMoney.setMoney(money);
            accountMoney.setType(type);
            accountMoney.setUpdatetime((int) (System.currentTimeMillis()/1000));
            accountMoney.setUserId(user_id);
            accountMoney.setYear(CallenderUtil.getYear());
            accountMoney.setMonth(CallenderUtil.getMonth());
            accountMoney.setDay(CallenderUtil.getDay());
            if(delete>0){
                accountMoney.setDisable(1);
            }

            AccountMoney accountMoney1 = this.accountMoneyRepository.saveAndFlush(accountMoney);

            res.put("code", 0);
            res.put("msg", "success");
            res.put("data", null);
        } catch (JSONException e) {
            e.printStackTrace();
            res.put("msg", "json解析失败");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public JSONObject account_money_detail(String money_id1) {
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", "server fail");

        int money_id = Integer.parseInt(money_id1);

        AccountMoney byId = this.accountMoneyRepository.findById(money_id);

        res.put("msg", "success");
        res.put("code", 0);
        res.put("data", byId);

        return res;
    }

    public JSONObject account_money_list(String user_id1, String year1, String month1, String bookId1) {
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", "server fail");

        int user_id = Integer.parseInt(user_id1);
        int year = Integer.parseInt(year1)==-1?CallenderUtil.getYear():Integer.parseInt(year1);
        int month = Integer.parseInt(month1)==-1?CallenderUtil.getMonth():Integer.parseInt(month1);
        int bookId = Integer.parseInt(bookId1)==-1?CallenderUtil.getMonth():Integer.parseInt(bookId1);
        if(bookId==-1){
            AccountBookUserMapping initAccountBook = accountBookUserMappingRepository.findInitAccountBook(user_id);
            if(initAccountBook==null){
                //建立新的账本
                //todo

            }
            bookId = initAccountBook.getAccountBookId();
        }

        List<AccountMoney> byId = this.accountMoneyRepository.findAllByUserIdAndYearAndMonthAndBookId(user_id,year,month,bookId);

        res.put("msg", "success");
        res.put("code", 0);
        res.put("data", byId);

        return res;

    }
}
