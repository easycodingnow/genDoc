package com.easycodingnow.template;

import com.easycodingnow.reflect.Annotation;
import com.easycodingnow.reflect.Class;
import com.easycodingnow.reflect.Method;
import com.easycodingnow.template.vo.SpringMvcMethod;
import com.easycodingnow.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lihao
 * @since 2018/3/9
 */
public class ConvertHelper {

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

    public static Class convertToSpringMvcClass(Class cls){
        if(!CollectionUtils.isEmpty(cls.getMethods())){
            List<Method> methodList = new ArrayList<Method>();

            for(Method method:cls.getMethods()){
                if(method.isPublic() && isSpringAction(method)){
                    methodList.add(new SpringMvcMethod(method));
                }
            }

            cls.setMethods(methodList);
        }

        return cls;
    }
}
