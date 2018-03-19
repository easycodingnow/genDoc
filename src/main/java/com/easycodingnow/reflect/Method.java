package com.easycodingnow.reflect;

import com.easycodingnow.utils.CollectionUtils;
import com.easycodingnow.utils.StringUtils;
import lombok.Data;

import java.util.List;
import java.util.Optional;

/**
 * @author lihao
 * @since 2018/3/8
 */
@Data
public class Method  extends Member{

    private List<MethodParam> params;

}
