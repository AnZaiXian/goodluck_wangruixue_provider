package com.bw.wangruixue.repository;

import com.bw.wangruixue.entity.GoodluckUser;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by WangRuiXue on 2017/7/28.
 */
@CacheConfig(cacheNames = "goodluckUser")
public interface UserRepository extends JpaRepository<GoodluckUser ,Integer>{

    //根据用户名和密码查询用户,登录
    GoodluckUser findByNameAndPwd(String name, String pwd);

    /**
     * 修改好友的信息
     * @Transactional注解 添加事物的注解,对查询超时的时设置
     *  @Modifying 他是API在执行增删改时需要加载@query()注解上的
     */
    @CachePut(key = "#p0")
    @Transactional
    @Modifying
    @Query("update GoodluckUser f set f.name = ?1,f.pwd=?2,f.age=?3 ,f.sex=?4,f.hobby=?5 where f.id=?6")
    void updateById(String name, String pwd,Integer age, String sex, String hobby, Integer id);

    /**
     * 根据用户名进行模糊查询
     */
    @Cacheable
    @Query(value="select id, name,pwd,sex,get,hobby from goodluck_user where name like ?1 ",nativeQuery = true)
    List<GoodluckUser> selectUserNmae(String name);

}
