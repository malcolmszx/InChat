package com.github.malcolmszx.auto;


import com.alibaba.fastjson.JSONArray;
import com.github.malcolmszx.bootstrap.BootstrapServer;
import com.github.malcolmszx.bootstrap.NettyBootstrapServer;
import com.github.malcolmszx.bootstrap.data.InChatToDataBaseService;
import com.github.malcolmszx.bootstrap.verify.InChatVerifyService;
import com.github.malcolmszx.common.bean.InChatMessage;
import com.github.malcolmszx.common.bean.InitNetty;
import com.github.malcolmszx.web.exception.CorsInterceptor;
import com.github.malcolmszx.web.rest.ControllerFactory;
import com.github.malcolmszx.web.rest.controller.ExceptionController;
import com.github.malcolmszx.web.rest.controller.ExceptionHandler;
import com.github.malcolmszx.web.rest.interceptor.Interceptor;
import com.github.malcolmszx.web.rest.interceptor.InterceptorRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by UncleCatMySelf in 2018/12/06
 **/
public class InitServer {

    private final static Logger logger = LoggerFactory.getLogger(InitServer.class);

    private InitNetty serverBean;

    public InitServer(InitNetty serverBean) {
        this.serverBean = serverBean;
    }

    BootstrapServer bootstrapServer;

    /**
     * REST控制器所在包名
     */
    private String controllerBasePackage = "";

    /**
     * 忽略Url列表（不搜索Mapping）
     */
    private static List<String> ignoreUrls = new ArrayList<>(16);

    /**
     * 以上处理器
     */
    private static ExceptionHandler exceptionHandler;


    public String getControllerBasePackage() {
        return this.controllerBasePackage;
    }
    public void setControllerBasePackage(String controllerBasePackage) {
        this.controllerBasePackage = controllerBasePackage;
    }

    public void addInterceptor(Interceptor interceptor) {
        try {
            InterceptorRegistry.addInterceptor(interceptor);
        } catch (Exception e) {
            logger.error("Add filter failed, ", e.getMessage());
        }
    }

    public void addInterceptor(Interceptor interceptor, String... excludeMappings) {
        try {
            InterceptorRegistry.addInterceptor(interceptor, excludeMappings);
        } catch (Exception e) {
            logger.error("Add filter failed, ", e.getMessage());
        }
    }

    public void open(){
        if(serverBean!=null){
            bootstrapServer = new NettyBootstrapServer();
            bootstrapServer.setServerBean(serverBean);
            bootstrapServer.start();
        }
    }

    public void close(){
        if(bootstrapServer!=null){
            bootstrapServer.shutdown();
        }
    }

    public static List<String> getIgnoreUrls() {
        return ignoreUrls;
    }

    public static ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public static void setExceptionHandler(ExceptionHandler handler) {
        exceptionHandler = handler;
    }


    public static void main( String[] args ){

        // 忽略指定url
        InitServer.getIgnoreUrls().add("/favicon.ico");

        // 全局异常处理
        InitServer.setExceptionHandler(new ExceptionController());

        InitServer initServer = new InitServer(new InitNetty()) ;

        // 设置Controller所在包
        initServer.setControllerBasePackage("com.github.malcolmszx.web.rest.controller");

        // 注册所有REST Controller
        new ControllerFactory().registerController(initServer.controllerBasePackage);

        // 添加拦截器，按照添加的顺序执行。
        // 跨域拦截器
        initServer.addInterceptor(new CorsInterceptor(), "/不用拦截的url");

        ConfigFactory.inChatToDataBaseService = new InChatToDataBaseService() {

            @Override
            public Boolean writeMapToDB(InChatMessage message) {
                // TODO Auto-generated method stub
                return true;
            }
        };


        ConfigFactory.inChatVerifyService = new InChatVerifyService() {

            @Override
            public boolean verifyToken(String token) {
                return true;
            }

            @Override
            public JSONArray getArrayByGroupId(String groupId) {
                //根据群聊id获取对应的群聊人员ID
                JSONArray jsonArray = JSONArray.parseArray("[\"1111\",\"2222\",\"3333\"]");
                return jsonArray;
            }
        };


        initServer.open();
    }

}
