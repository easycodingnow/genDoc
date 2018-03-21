package com.easycodingnow.template.vo.spring;

import com.easycodingnow.reflect.Annotation;
import com.easycodingnow.reflect.Class;
import com.easycodingnow.reflect.Method;
import com.easycodingnow.template.vo.DocMethod;
import com.easycodingnow.template.vo.spring.SpringMvcClass;
import com.easycodingnow.template.vo.spring.SpringMvcMethod;
import com.easycodingnow.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lihao
 * @since 2018/3/9
 */
public class SpringConvertHelper {

    private static boolean  isSpringAction(Method method){

        List<Annotation> annotations = method.getAnnotations();

        if(annotations != null && annotations.size() > 0){
            for(Annotation annotation:annotations){
                if(annotation.getName().equals("RequestMapping")){
                    return true;
                }
            }
        }
        return false;
    }

    public static SpringMvcClass convertToSpringMvcClass(Class cls){
        List<DocMethod> methodList = new ArrayList<DocMethod>();

        if(!CollectionUtils.isEmpty(cls.getMethods())){

            for(Method method:cls.getMethods()){
                if(method.isPublic() && isSpringAction(method)){
                    methodList.add(new SpringMvcMethod(method));
                }
            }
        }

        return new SpringMvcClass(methodList, cls);
    }
}
