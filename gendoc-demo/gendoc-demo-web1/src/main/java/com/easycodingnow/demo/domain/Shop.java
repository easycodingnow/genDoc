package com.easycodingnow.demo.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * 商店
 * @author lihao
 * @since 2018/3/21
 */
public class Shop {


    private String shopName;//商店名称


    //商品数量
    private Long goodNums;

    /**
     * 商店介绍
     */
    private String desc;
}
