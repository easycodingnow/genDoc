package com.easycodingnow.demo.web1;

import com.easycoding.demo.domain.Result;
import com.easycodingnow.demo.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商店
 * @author lihao
 * @since 2018/3/21
 */
@Controller
@RequestMapping("/shop")
public class ShopController {


    /**
     * 泛型解析测试
     * @param shopName 商店名称(模糊查询)
     * @param shopNum  商店数量
     * @return 商品实体
     */
    @RequestMapping("getShop")
    public Result<Shop> getShopsV2(String shopName, int shopNum){
        return null;
    }


    //参数返回值无法通过智能查询找到时，可以通过注释指定 #tyep:...#
    /**
     * 创建商店
     * @param shop
     * @return #type:com.easycodingnow.demo.domain.Shop#
     */
    @PostMapping("createShop")
    public Object createShop(Shop shop){
        return null;
    }


    //PathVariable 的方式
    /**
     * 删除商店
     * @param id 商店id
     * @return 是否成功
     */
    @GetMapping("deleteShop/{id}")
    public Shop deleteShop(@PathVariable(name="id") long id){
        return null;
    }


    //解析将会跳过@Deprecated标识的方法
    @Deprecated
    @RequestMapping("getShop2")
    public Object getShopsv1(String shopName, int shopNum){
        return null;
    }

    //添加#ignore#也会跳过此方法的解析
    /**
     * #ignore#
     * @param shopName
     * @param shopNum
     * @return
     */
    @Deprecated //解析将会调过@Deprecated标识的方法
    @RequestMapping("getShop3")
    public Object getShopsv2(String shopName, int shopNum){
        return null;
    }

}
