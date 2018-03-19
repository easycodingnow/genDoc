package com.easycodingnow.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author lihao
 * @since 2018/3/9
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

}
