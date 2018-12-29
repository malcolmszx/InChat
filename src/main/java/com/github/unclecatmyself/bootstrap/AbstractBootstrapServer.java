package com.github.unclecatmyself.bootstrap;


import java.util.concurrent.TimeUnit;

import com.github.unclecatmyself.auto.ConfigFactory;
import com.github.unclecatmyself.bootstrap.channel.WebSocketHandlerService;
import com.github.unclecatmyself.bootstrap.handler.DefaultWebSocketHandler;
import com.github.unclecatmyself.common.bean.InitNetty;
import com.github.unclecatmyself.task.DataAsynchronousTask;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Create by UncleCatMySelf in 2018/12/06
 **/
public abstract class AbstractBootstrapServer implements BootstrapServer {

    /**
     * @param channelPipeline  channelPipeline
     * @param serverBean  服务配置参数
     */
    protected void initHandler(ChannelPipeline channelPipeline, InitNetty serverBean){
        intProtocolHandler(channelPipeline,serverBean);
        //入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
        channelPipeline.addLast(new IdleStateHandler(serverBean.getHeart(), 0, 0,TimeUnit.SECONDS));
        channelPipeline.addLast(new DefaultWebSocketHandler(new WebSocketHandlerService(new DataAsynchronousTask(ConfigFactory.inChatToDataBaseService),ConfigFactory.inChatVerifyService)));
    }

    private void intProtocolHandler(ChannelPipeline channelPipeline,InitNetty serverBean){
    	// 编解码 http请求
        channelPipeline.addLast("httpCode",new HttpServerCodec());
        // 保证接收的 Http请求的完整性
        channelPipeline.addLast("aggregator", new HttpObjectAggregator(serverBean.getMaxContext()));
        // 写文件内容
        channelPipeline.addLast("chunkedWrite",new ChunkedWriteHandler());
        // 处理其他的 WebSocketFrame
        channelPipeline.addLast("webSocketHandler",new WebSocketServerProtocolHandler(serverBean.getWebSocketPath()));
    }

}
