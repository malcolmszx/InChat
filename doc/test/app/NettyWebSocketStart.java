package app;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.alibaba.fastjson.JSONArray;
import com.github.malcolmszx.auto.ConfigFactory;
import com.github.malcolmszx.auto.InitServer;
import com.github.malcolmszx.bootstrap.channel.WebSocketChannelService;
import com.github.malcolmszx.bootstrap.data.InChatToDataBaseService;
import com.github.malcolmszx.bootstrap.verify.InChatVerifyService;
import com.github.malcolmszx.common.bean.InChatMessage;
import com.github.malcolmszx.common.bean.InitNetty;

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
				 //根据群聊id获取对应的群聊人员ID
		        JSONArray jsonArray = JSONArray.parseArray("[\"1111\",\"2222\",\"3333\"]");
		        return jsonArray;
			}
		};
		
		InitServer initServer = new InitServer(new InitNetty()) ;
		initServer.open();
		
	
				
			

		
		 
	}

}
