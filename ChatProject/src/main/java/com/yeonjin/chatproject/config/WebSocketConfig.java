package com.yeonjin.chatproject.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@Slf4j
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

@Override
public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic");  //메시지를 전달할 경로
    config.setApplicationDestinationPrefixes("/app");  //클라이언트에서 보낼 메세지의 prefix
}

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat")  //websocket 엔드포인트
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS();  //sockjs 지원
    }
}