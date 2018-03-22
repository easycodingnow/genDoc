package com.easycodingnow.demo.web1;

import com.easycodingnow.demo.domain.Shop;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
     * 获取商店
     * 通过名称获取商店信息
     * @param shopName 商店名称(模糊查询)
     * @param shopNum  商店数量
     * @return 商品实体#type:com.easycodingnow.demo.domain.Shop,com.easycoding.demo.domain.User#
     */
    @RequestMapping("getShop")
    public Object getShopsV2(String shopName, int shopNum){
        return null;
    }


    //参数实体的方式，需要标注参数的类型
    /**
     * 创建商店
     * @param shop  #type:com.easycodingnow.demo.domain.Shop,com.easycodingnow.demo.domain.Shop,com.easycodingnow.demo.domain.Shop#
     * @return #type:com.easycodingnow.demo.domain.Shop,com.easycodingnow.demo.domain.Shop#
     */
    @RequestMapping("createShop")
    public Object createShop(Shop shop){
        return null;
    }


    //PathVariable 的方式
    /**
     * 删除商店
     * @param id 商店id
     * @return 是否成功
     */
    @RequestMapping("deleteShop/{id}")
    public Object deleteShop(@PathVariable(name="id") long id){
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
