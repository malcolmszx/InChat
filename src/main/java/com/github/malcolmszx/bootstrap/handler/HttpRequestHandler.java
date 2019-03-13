package com.github.malcolmszx.bootstrap.handler;

import com.github.malcolmszx.auto.InitServer;
import com.github.malcolmszx.web.rest.RequestDispatcher;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.RejectedExecutionHandlers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Http 请求处理器
 * 
 * @author Leo
 * @date 2018/3/16
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    /**
     * 业务线程池线程数
     * 可通过 -Dhttp.executor.threads 设置
     */
    private static int eventExecutorGroupThreads = 0;

    /**
     * 业务线程池队列长度
     * 可通过 -Dhttp.executor.queues 设置
     */
    private static int eventExecutorGroupQueues = 0;

    static {
        eventExecutorGroupThreads = Integer.getInteger("http.executor.threads", 0);
        if(eventExecutorGroupThreads == 0) {
            eventExecutorGroupThreads = Runtime.getRuntime().availableProcessors() * 2;
        }

        eventExecutorGroupQueues = Integer.getInteger("http.executor.queues", 0);
        if(eventExecutorGroupQueues == 0) {
            eventExecutorGroupQueues = 1024;
        }
    }
    /**
     * 业务线程组
     */
    public static final EventExecutorGroup eventExecutorGroup = new DefaultEventExecutorGroup(
            eventExecutorGroupThreads, new ThreadFactory() {
        private AtomicInteger threadIndex = new AtomicInteger(0);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "HttpRequestHandlerThread_" + this.threadIndex.incrementAndGet());
        }
    }, eventExecutorGroupQueues, RejectedExecutionHandlers.reject());

    private final static Logger logger = LoggerFactory.getLogger(HttpRequestHandler.class);
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage());
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if  (acceptInboundMessage(request)) {
            if (InitServer.getIgnoreUrls().contains(request.uri())) {
                return;
            }
            new RequestDispatcher().doDispatch(request, ctx);
        } else {
            ctx.fireChannelRead(request);
        }


    }

}
