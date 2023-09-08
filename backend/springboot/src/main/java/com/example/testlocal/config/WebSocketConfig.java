package com.example.testlocal.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //endpoint 등록
    //연결시 CORS(CrossOrigin) 허용
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/websocket","websocket2").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 클라이언트에서 보낸 메시지를 받을 prefix
        registry.setApplicationDestinationPrefixes("/pub"); //app
        // 해당 주소를 구독하고 있는 클라이언트들에게 메시지 전달
        registry.enableSimpleBroker("/sub"); //topic
        //registry.setApplicationDestinationPrefixes("/app"); //app
        //registry.enableSimpleBroker("/topic"); //topic
    }

}
