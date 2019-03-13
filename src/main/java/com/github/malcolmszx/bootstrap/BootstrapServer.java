package com.github.malcolmszx.bootstrap;

import com.github.malcolmszx.common.bean.InitNetty;

/**
 * Create by UncleCatMySelf in 2018/12/06
 **/
public interface BootstrapServer {

    void shutdown();

    void setServerBean(InitNetty serverBean);

    void start();

}
