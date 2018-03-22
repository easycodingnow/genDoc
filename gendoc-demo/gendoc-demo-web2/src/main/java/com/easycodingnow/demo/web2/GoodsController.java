package com.easycodingnow.demo.web2;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//restful风格的controller
/**
 * 商品
 * @author lihao
 * @since 2018/3/22
 */
@RestController
public class GoodsController {


    /**
     * 获取商品
     * @param id 商品id
     * @return 商品实体#type:com.easycoding.demo.domain.Food#
     */
    @RequestMapping("getGoods/{goodsId}")
    public Object getGood(@PathVariable("goodsId") Long id){
        return null;
    }

}
