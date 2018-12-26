package app;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.alibaba.fastjson.JSONArray;
import com.github.unclecatmyself.auto.ConfigFactory;
import com.github.unclecatmyself.auto.InitServer;
import com.github.unclecatmyself.bootstrap.channel.WebSocketChannelService;
import com.github.unclecatmyself.bootstrap.data.InChatToDataBaseService;
import com.github.unclecatmyself.bootstrap.verify.InChatVerifyService;
import com.github.unclecatmyself.common.bean.InChatMessage;
import com.github.unclecatmyself.common.bean.InitNetty;

import io.netty.channel.Channel;

public class NettyWebSocketStart {
	
	 public static void main( String[] args ){
		 
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
				return null;
			}
		};
		
		InitServer initServer = new InitServer(new InitNetty()) ;
		initServer.open();
		
	
				
			

		
		 
	}

}
