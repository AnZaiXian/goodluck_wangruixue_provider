package com.bw.wangruixue.controller;

import com.bw.wangruixue.entity.GoodluckUser;
import com.bw.wangruixue.repository.UserRepository;
import com.bw.wangruixue.util.MD5Util;
import org.apache.poi.hssf.model.Workbook;
//import org.apache.poi.ss.usermodel.Workbook;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by WangRuiXue on 2017/7/28.
 */
@Controller
public class UserController {

    /**
     * 创建user的对象
     */
    @Autowired
    private UserRepository userRepository;
    //创建对配置文件的密码加密属性
    @Autowired
    private StringEncryptor stringEncryptor;

    /**
     * 到注册页面
     */
    @RequestMapping("toregister")
    public String register(){
        return "register";
    }

    /**
     *   模块二:  注册,调用加密的工具类给密码加密
     */
    @RequestMapping("jm")
    public String jm(GoodluckUser user){
        System.out.println("========user"+user);
        String pwd = user.getPwd();
        //调用MD5进行加密
        String mpwd = MD5Util.MD5(pwd);
        user.setPwd(mpwd);
        System.out.println("加密后的密码为:"+mpwd);//6of4420aoM44o32sF0341s53so5sM43s
        userRepository.save(user);
        return  "login";
    }


    /**
     *   模块三:  登录
     */
    @RequestMapping("login")
    public String login(GoodluckUser user, HttpSession session){
        System.out.println("从前台获取的对象:"+user);
        String name = user.getName();
        String pwd = user.getPwd();

        //将代理用户的信息放到session缓存中
        session.setAttribute("name",name);
        session.setAttribute("pwd",pwd);

        //调用MD5对密码进行加密
        String p = MD5Util.MD5(pwd);
        //6of4420aoM44o32sF0341s53so5sM43s==>>5921
        System.out.println("加密后的密码为:"+p);
        //调用根据用户名和密码查询对象findByUserNameOrEmail
        GoodluckUser u = userRepository.findByNameAndPwd(name,p);
        System.out.println(u);
        if(u!=null&&u.getPwd().equals(p)){

            return "redirect:selectUser";
        }else {
            return  "login";
        }
    }


    /**
     *    模块四: 用户列表==>用户信息修改操作
     */
    @RequestMapping("selectUser")
    public String selectUsers(Map<String , Object> map){
        List<GoodluckUser> all = userRepository.findAll();
        for (GoodluckUser user : all){
            System.out.println(user);
        }

        map.put("list",all);

        return "showUser";
    }


    /**
     * 根据id查询对象
     */
    @RequestMapping("hxEmployee")
    @ResponseBody
    public GoodluckUser hxFriends(Integer id){
        System.out.println("=======根据id查询对象==========="+id);
        GoodluckUser one = userRepository.findOne(id);
        System.out.println(one);
        return  one;
    }

    /**
     * 修改好修的信息
     */
    @RequestMapping("updateUser")
    public String updateFriends(GoodluckUser c){
        System.out.println("======修改好修的信息========");
        System.out.println(c);
        String pwd = c.getPwd();
        //调用MD5进行加密
        String mpwd = MD5Util.MD5(pwd);
        c.setPwd(mpwd);
        //void updateById(String name, String pwd,Integer age, String sex, String hobby, Integer id);
        userRepository.updateById(c.getName(),c.getPwd(),c.getAge(),c.getSex(),c.getHobby(),c.getId());
        return "redirect:selectUser";

    }


    /**
     *   模块五:  删除用户
     */
    @RequestMapping("deleteUser")
    public String deleteUser(Integer id){
        System.out.println("==========删除方法=====");
        //调用删除的方法
        userRepository.delete(id);
        //重定向到查询方法
        return  "redirect:selectUser";
    }

    /**
     * 模块六 : 通过用户名查询用户，显示用户列表，可以导出用户到excel文件
     */
     @RequestMapping("getUserByName")
     public String selectByNmae(String name, HttpServletResponse response) throws Exception{

         Workbook wb= new Workbook();
         String[] headers={"编号","姓名","性别"};
         List<GoodluckUser> ulist = userRepository.selectUserNmae(name);
         for (GoodluckUser user : ulist){
             System.out.println(user);
         }

        // ExcelUtil.excelOut(ulist, wb, headers);
         String fileName="导出.xls";//导出生产的工作薄名
         //ExcelUtil.export(response, wb, fileName);
         return null;

     }


    /**
     *  模块七 : 配置文件密码加密
     */
    @RequestMapping("ENC")
    @ResponseBody
    public String enc(){
        String pwd = stringEncryptor.encrypt("root");
        return  pwd;
    }


    /**
     * 模块八 : 数据库操作Redis做缓存，用redis做session共享
     *  OK session共享数据及方法的操作 在==> 请见cache包
     */


    /**
     *  模块九 : 用Swagger2提供restful API==>请见 SwaggerController
     */


    /**
     * 模块十 : 记录Web请求日志到mongodb ok
     */

    /**
     * 模块十一 : 统一处理异常，自定义一个异常，如果捕获自定义异常异步给
        管理员jiaozhiguang@126.com发送邮==> 请见Exceptioncontroller
     */



}
