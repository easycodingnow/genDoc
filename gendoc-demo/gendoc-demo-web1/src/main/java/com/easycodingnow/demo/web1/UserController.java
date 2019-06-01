package com.easycodingnow.demo.web1;

import com.easycodingnow.demo.domain.Man;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户
 * @author lihao
 * @since 2018/3/21
 */
@Controller
@RequestMapping("/user")
public class UserController {


    /**
     * 通过用户id获取用户
     * @param id 用户id
     * @return 用户实体
     */
    @RequestMapping("getUserById")
    public Object getUser(@RequestParam(name = "userId") Long id){
        return null;
    }


    /**
     * 内部类解析
     * @param id 用户id
     * @return 用户实体
     */
    @RequestMapping("getMan")
    public Man getMan(@RequestParam(name = "userId") Long id){
        return null;
    }

    /**
     * 使用完全限定名解析
     * @param id 用户id
     * @return 用户实体
     */
    @RequestMapping("getMan")
    public com.easycodingnow.demo.domain.Man2 getMan2(@RequestParam(name = "userId") Long id){
        return null;
    }
}
