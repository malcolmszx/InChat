package com.github.malcolmszx.bootstrap.channel;

import com.google.gson.Gson;
import com.github.malcolmszx.bootstrap.WsChannelService;
import com.github.malcolmszx.bootstrap.channel.cache.WsCacheMap;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Map;

/**
 * Created by MySelf on 2018/11/26.
 */
public class WebSocketChannelService implements WsChannelService {

    @Override
    public void loginWsSuccess(Channel channel, String token) {
        WsCacheMap.saveWs(token,channel);
        WsCacheMap.saveAd(channel.remoteAddress().toString(),token);
    }

    @Override
    public boolean hasOther(String token) {
        return WsCacheMap.hasToken(token);
    }

    @Override
    public Channel getChannel(String otherOne) {
        return WsCacheMap.getByToken(otherOne);
    }

    @Override
    public void close(Channel channel) {
        String token = WsCacheMap.getByAddress(channel.remoteAddress().toString());
        WsCacheMap.deleteAd(channel.remoteAddress().toString());
        WsCacheMap.deleteWs(token);
        channel.close();
    }

    @Override
    public boolean sendFromServer(Channel channel, Map<String, String> map) {
        Gson gson = new Gson();
        try {
            channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(map)));
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
