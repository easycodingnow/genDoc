package com.easycodingnow.demo.web1;

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


    //此方法的返回类型在其他的模块里面，测试跨模块的实体解析
    /**
     * 通过用户id获取用户
     * @param id 用户id
     * @return 用户实体
     */
    @RequestMapping("getUserById")
    public Object getUser(@RequestParam(name = "userId") Long id){
        return null;
    }
}
