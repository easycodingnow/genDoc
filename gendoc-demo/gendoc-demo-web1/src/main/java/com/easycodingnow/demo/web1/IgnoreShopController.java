package com.easycodingnow.demo.web1;

import com.easycodingnow.demo.domain.Shop;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//添加#ignore#也会跳过此controller的解析
/**
 * @author lihao
 * @since 2018/3/21
 */
@Controller
@RequestMapping("/shop")
public class IgnoreShopController {


    /**
     * 获取商店
     * 通过名称获取商店信息
     * @param shopName 商店名称(模糊查询)
     * @param shopNum  商店数量
     * @return 商品实体
     */
    @RequestMapping("getShop")
    public Shop getShopsV2(String shopName, int shopNum){
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
