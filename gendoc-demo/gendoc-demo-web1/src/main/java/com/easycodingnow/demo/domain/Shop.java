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

    //商店名称
    @JSONField(name = "shop_name")
    private String shopName;


    //商品数量
    @JsonProperty("good_nums")
    private Long goodNums;

    /**
     * 商店介绍
     */
    @SerializedName("description")
    private String desc;
}
