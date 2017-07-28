package com.bw.wangruixue.controller;

import com.bw.wangruixue.entity.SwaggerUser;
import com.bw.wangruixue.repository.SwaggerMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by lenovo on 2017/7/20.
 */
/**
 *  模块九 : 用Swagger2提供restful API==>SwaggerController
 */
@RestController
@RequestMapping("swagger")
public class SwaggerController {

    //创建swagger2的实例,调用查询方法
    @Autowired
    private SwaggerMapper swaggerMapper;


    /**
     * 创建一个异常信息,通过访问,在springboot提供的aop管理的web请求日志
     * 中调用mongodbTemplate锁提供的对mongodb增删改查的方法
     */
    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    @ResponseBody
    public String ex  () throws Exception{

        return "Hello 异常 我来了! 哈哈";
    }



    /**
     * springBoot中提供的AOP统一管理web请求日志
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello(@RequestParam String name) {

        return "Hello" +name;
    }

    //创建一个map容器来存储前台传来的数据
    static Map<Long, SwaggerUser> users = Collections.synchronizedMap(new HashMap<Long, SwaggerUser>());

    @ApiOperation(value="获取用户列表", notes="")
    @RequestMapping(value={""}, method= RequestMethod.GET)
    public List<SwaggerUser> getUserList() {

        List<SwaggerUser> r = new ArrayList<SwaggerUser>(users.values());
       List<SwaggerUser> r2 = swaggerMapper.findAll();
        return r2;
    }

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "swaggerUser", value = "用户详细实体user", required = true, dataType = "SwaggerUser")
    @RequestMapping(value="", method= RequestMethod.POST)
    public String postUser(@RequestBody SwaggerUser swaggerUser) {
        //users.put(swaggerUser.getId(), swaggerUser);
        System.out.println(swaggerUser);
        swaggerMapper.save(swaggerUser);
        return "success";
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public SwaggerUser getUser(@PathVariable Long id) {
        return users.get(id);
    }

    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "swaggerUser", value = "用户详细实体user", required = true, dataType = "SwaggerUser")
    })
    @RequestMapping(value="/{id}", method= RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @RequestBody SwaggerUser swaggerUser) {
        SwaggerUser u = users.get(id);
        u.setName(swaggerUser.getName());
        u.setAge(swaggerUser.getAge());
        users.put(id, u);
        return "success";
    }

    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        //users.remove(id);

        System.out.println("删除用户id为:"+id);

        swaggerMapper.delete(id);
        return "success";
    }


    private class Throws {
    }
}
