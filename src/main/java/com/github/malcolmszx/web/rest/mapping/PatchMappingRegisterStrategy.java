package com.github.malcolmszx.web.rest.mapping;


import com.github.malcolmszx.web.annotation.PatchMapping;

import java.lang.reflect.Method;

/**
 * PATCH 请求映射注册策略类
 * 
 * @author Leo
 * @date 2018/3/16
 */
public final class PatchMappingRegisterStrategy extends AbstractRequestMappingRegisterStrategy implements RequestMappingRegisterStrategy {
    
    /**
     * 得到控制器方法的Url
     * @param method
     * @return
     */
    @Override
    public String getMethodUrl(Method method) {
        if(method.getAnnotation(PatchMapping.class) != null) {
            return method.getAnnotation(PatchMapping.class).value();
        }
        return "";
    }

    /**
     * 得到Http请求的方法类型
     * @return
     */
    @Override
    public String getHttpMethod() {
        return "PATCH";
    }
    
    /**
     * 注册Mapping
     * @param url
     * @param mapping
     */
    @Override
    public void registerMapping(String url, ControllerMapping mapping) {
        ControllerMappingRegistry.getPatchMappings().put(url, mapping);
    }

}
