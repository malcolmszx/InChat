package com.github.malcolmszx.auto;

import com.github.malcolmszx.bootstrap.data.InChatToDataBaseService;
import com.github.malcolmszx.bootstrap.verify.InChatVerifyService;

/**
 * 默认配置工厂
 * Created by MySelf on 2018/12/21.
 */
public class ConfigFactory {

    public static InChatVerifyService inChatVerifyService;

    public static InChatToDataBaseService inChatToDataBaseService;

    public InChatVerifyService getInChatVerifyService() {
        return inChatVerifyService;
    }

    public void setInChatVerifyService(InChatVerifyService inChatVerifyService) {
        this.inChatVerifyService = inChatVerifyService;
    }

    public InChatToDataBaseService getInChatToDataBaseService() {
        return inChatToDataBaseService;
    }

    public void setInChatToDataBaseService(InChatToDataBaseService inChatToDataBaseService) {
        this.inChatToDataBaseService = inChatToDataBaseService;
    }
}
