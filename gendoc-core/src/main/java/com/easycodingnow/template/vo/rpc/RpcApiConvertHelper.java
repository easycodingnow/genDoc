package com.easycodingnow.template.vo.rpc;

import com.easycodingnow.reflect.Annotation;
import com.easycodingnow.reflect.Class;
import com.easycodingnow.reflect.Method;
import com.easycodingnow.template.vo.DocApiMethod;
import com.easycodingnow.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lihao
 * @since 2018/3/9
 */
public class RpcApiConvertHelper {

    private static boolean  isRpcAction(Method method){

        List<Annotation> annotations = method.getAnnotations();

        if(annotations != null && annotations.size() > 0){
            for(Annotation annotation:annotations){
                if(annotation.getName().equals("Api")){
                    return true;
                }
            }
        }
        return false;
    }

    public static RpcApiClass convertToRpcApi(Class cls){
        List<DocApiMethod> methodList = new ArrayList<DocApiMethod>();

        if(!CollectionUtils.isEmpty(cls.getMethods())){

            for(Method method:cls.getMethods()){
                if(isRpcAction(method)){
                    methodList.add(new RpcMethod(method));
                }
            }
        }

        return new RpcApiClass(methodList, cls);
    }
}
