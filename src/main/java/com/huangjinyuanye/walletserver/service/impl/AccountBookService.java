package com.huangjinyuanye.walletserver.service.impl;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.huangjinyuanye.walletserver.dao.*;
import com.huangjinyuanye.walletserver.pojo.*;
import com.huangjinyuanye.walletserver.utils.CallenderUtil;
import com.huangjinyuanye.walletserver.utils.IThreadPoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class AccountBookService {

    private static final Logger logger = LoggerFactory.getLogger(AccountBookService.class);
    @Autowired
    UserRepository userRepository;
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

    private AccountBookService accountBookService;

    @PostConstruct
    public void init () {
        accountBookService = this;
    }


    public JSONObject add_account_book(String body) {
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", "server fail");
        try {

            JSONObject params = JSONObject.parseObject(body);

            String token = params.getString("token");
            String name = params.getString("name");
            String msg = params.getString("msg");
            String owner_id = params.getString("owner_id");

            User byId = this.userRepository.findById(Integer.parseInt(owner_id.trim()));
            if(byId==null){
                res.put("code",-2);
                res.put("msg","没有对应的user_id，请检查");
                return res;
            }

            AccountBook accountBook = new AccountBook();
            accountBook.setName(name.trim());
            accountBook.setMsg(msg.trim());
            accountBook.setOwnerId(Integer.parseInt(owner_id.trim()));
            accountBook.setCreatime((int) (System.currentTimeMillis()/1000));
            accountBook.setDisable(-1);

            AccountBook accountBook1 = this.accountBookRepository.saveAndFlush(accountBook);

            AccountBookUserMapping accountBookUserMapping = new AccountBookUserMapping();
            accountBookUserMapping.setUserId(Integer.parseInt(owner_id.trim()));
            accountBookUserMapping.setAccountBookId(accountBook1.getId());
            accountBookUserMapping.setCreatetime((int) (System.currentTimeMillis()/1000));
            this.accountBookUserMappingRepository.saveAndFlush(accountBookUserMapping);


            res.put("code", 0);
            res.put("msg", "success");
            res.put("data", accountBook1);
        } catch (JSONException e) {
            e.printStackTrace();
            res.put("msg", "json解析失败");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public JSONObject account_book_add_user(String body) {
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", "server fail");
        try {

            JSONObject params = JSONObject.parseObject(body);

            String token = params.getString("token");
            String account_book_id = params.getString("account_book_id");
            String msg = params.getString("msg");
            String user_id = params.getString("user_id");

            AccountBookUserMapping byAccountBookIdAndUserId = this.accountBookUserMappingRepository.findByAccountBookIdAndUserId(Integer.parseInt(account_book_id.trim()), Integer.parseInt(user_id.trim()));
            if(byAccountBookIdAndUserId!=null){
                res.put("code",-2);
                res.put("msg","您已经在申请的记账本中，请勿重复申请");
                return res;
            }

            List<AccountBookUserMappingAduit> allByUserIdAndAccountBookId = this.accountBookUserMappingAduitRepository.findAllByUserIdAndAccountBookId(Integer.parseInt(user_id.trim()), Integer.parseInt(account_book_id.trim()));
            if(allByUserIdAndAccountBookId!=null){
                for(AccountBookUserMappingAduit accountBookUserMappingAduit :allByUserIdAndAccountBookId){
                    if(accountBookUserMappingAduit.getType()==0){
                        res.put("code",-2);
                        res.put("msg","您已经申请加入记账本，请等待审批");
                        return res;
                    }
                }
            }

            AccountBookUserMappingAduit accountBookUserMappingAduit = new AccountBookUserMappingAduit();
            accountBookUserMappingAduit.setAccountBookId(Integer.parseInt(account_book_id.trim()));
            accountBookUserMappingAduit.setUserId(Integer.parseInt(user_id));
            accountBookUserMappingAduit.setCreatetime((int) (System.currentTimeMillis()/1000));
            accountBookUserMappingAduit.setDisable(-1);
            accountBookUserMappingAduit.setType(0);

            AccountBookUserMappingAduit accountBookUserMappingAduit1 = accountBookUserMappingAduitRepository.saveAndFlush(accountBookUserMappingAduit);

            res.put("code", 0);
            res.put("msg", "success");
            res.put("data", accountBookUserMappingAduit1);
        } catch (JSONException e) {
            e.printStackTrace();
            res.put("msg", "json解析失败");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public JSONObject account_book_add_user_list(String id, String type1) {
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", "server fail");

        int userId = Integer.parseInt(id);
        logger.info(String.valueOf(userId));

        int type = Integer.parseInt(type1);
        List<AccountBookUserMappingAduit> aduits = null;
        if(type<=2){
            aduits = this.accountBookUserMappingAduitRepository.findAllByUserIdAndType(userId,type);
        }else{
            aduits = this.accountBookUserMappingAduitRepository.findAllByUserId(userId);
        }

        res.put("msg", "success");
        res.put("code", 0);
        res.put("data", aduits);

        return res;
    }

    public JSONObject account_book_auditing(String body) {
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", "server fail");
        try {

            JSONObject params = JSONObject.parseObject(body);

            String token = params.getString("token");
            String auditing_id = params.getString("auditing_id");
            String msg = params.getString("msg");
            String user_id = params.getString("user_id");
            String type = params.getString("type");

            AccountBookUserMappingAduit accountBookUserMappingAduit = this.accountBookUserMappingAduitRepository.findById(Integer.parseInt(auditing_id.trim()));
            logger.info(accountBookUserMappingAduit.toString());
            if(accountBookUserMappingAduit.getType()!=0){
                res.put("code","-2");
                res.put("msg","该申请已做处理，请您查看");
                res.put("data",accountBookUserMappingAduit);
                return res;
            }

            //验证user_id是否为记账本的所有者
            AccountBookUserMapping byAccountBookIdAndUserId = this.accountBookUserMappingRepository.findByAccountBookIdAndUserId(accountBookUserMappingAduit.getAccountBookId(), Integer.parseInt(user_id.trim()));
            if(byAccountBookIdAndUserId==null){
                res.put("code","-2");
                res.put("msg","当前user_id不属于该记账本的所有者，不能审批");
                res.put("data",null);
                return res;
            }


            int i = Integer.parseInt(type);
            if(i==1 || i==-1){
                accountBookUserMappingAduit.setType(i);
                this.accountBookUserMappingAduitRepository.saveAndFlush(accountBookUserMappingAduit);
                //如果通过，将某人添加到账本中
                AccountBookUserMapping accountBookUserMapping = new AccountBookUserMapping();
                accountBookUserMapping.setAccountBookId(accountBookUserMappingAduit.getAccountBookId());
                accountBookUserMapping.setUserId(accountBookUserMappingAduit.getUserId());
                accountBookUserMapping.setCreatetime((int) (System.currentTimeMillis()/1000));
                accountBookUserMapping.setDisable(-1);

                this.accountBookUserMappingRepository.saveAndFlush(accountBookUserMapping);

            }else{
                res.put("code","-2");
                res.put("msg","请传入正常审批指令");
                res.put("data",null);
                return res;
            }


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

    public JSONObject account_book_add_user_aduit_list(String userId1, String type1) {
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", "server fail");

        int userId = Integer.parseInt(userId1);

        int type = Integer.parseInt(type1);
        List<AccountBookUserMappingAduit> aduits = null;
        if(type<=2){
            aduits = this.accountBookUserMappingAduitRepository.findAllByUserIdAndTypeNeedAduit(userId,type);
        }else{
            aduits = this.accountBookUserMappingAduitRepository.findAllByUserIdNeedAduit(userId);
        }

        res.put("msg", "success");
        res.put("code", 0);
        res.put("data", aduits);

        return res;
    }

    public JSONObject account_book_user_list(String bookId1) {
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", "server fail");

        int bookId = Integer.parseInt(bookId1);

        List<AccountBookUserMapping> mappings = this.accountBookUserMappingRepository.findAllByAccountBookId(bookId);

        res.put("msg", "success");
        res.put("code", 0);
        res.put("data", mappings);

        return res;
    }

    public JSONObject account_book_user_all(String user_id) {
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", "server fail");

        int userId = Integer.parseInt(user_id);

        List<AccountBookUserMapping> mappings = this.accountBookUserMappingRepository.findAllByUserId(userId);

        res.put("msg", "success");
        res.put("code", 0);
        res.put("data", mappings);

        return res;
    }
}
