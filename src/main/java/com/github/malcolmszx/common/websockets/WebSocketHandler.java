package com.github.malcolmszx.common.websockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * Create by UncleCatMySelf in 2018/12/06
 */
public abstract class WebSocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
	
	private final Logger log = LoggerFactory.getLogger(WebSocketHandler.class);

    WebSocketHandlerApi webSocketHandlerApi;

    public WebSocketHandler(WebSocketHandlerApi webSocketHandlerApi){
        this.webSocketHandlerApi = webSocketHandlerApi;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        if (acceptInboundMessage(msg)){
            textdoMessage(ctx,(TextWebSocketFrame)msg);
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    protected abstract void webdoMessage(ChannelHandlerContext ctx, WebSocketFrame msg);

    protected abstract void textdoMessage(ChannelHandlerContext ctx, TextWebSocketFrame msg);

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("【DefaultWebSocketHandler：channelInactive】"+ctx.channel().localAddress().toString()+"关闭成功");
        webSocketHandlerApi.close(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if(evt instanceof IdleStateEvent){
//            webSocketHandlerApi.doTimeOut(ctx.channel(),(IdleStateEvent)evt);
//        }
        super.userEventTriggered(ctx, evt);
    }
}
