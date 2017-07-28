package com.bw.wangruixue.mongodb;

import com.bw.entity.EntityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;


/**
 * Created by lenovo on 2017/7/20.
 * 将数据存到mongodb中进行增删改查
 */
@Component
public class UserEntityMongoRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     *
     */
    public void saveUser(EntityUser user){
        mongoTemplate.save(user);
    }

    /**
     * 根据用户名查询对象
     */
    public EntityUser findUserByUserNamae(String userName){
        Query query = new Query(Criteria.where("userName").is(userName));
        EntityUser user =  mongoTemplate.findOne(query , EntityUser.class);
        return user;

    }

    /**
     * 跟新对象
     */
    public void updateUser(EntityUser user){
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update().set("userName",user.getUserName()).set("passWord",user.getPassWord());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,EntityUser.class);

    }

    /**
     * 删除对象
     */
    public void deleteUserById(Long id){
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,EntityUser.class);
    }

}
