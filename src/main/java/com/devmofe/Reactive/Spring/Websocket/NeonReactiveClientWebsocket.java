package com.devmofe.Reactive.Spring.Websocket;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;

public class NeonReactiveClientWebsocket {
    public static void main(String[] args) throws Exception {

        String websocketURI = "ws://localhost:8080/emitter";
        String message = "event-spring-reactive-client-websocket";

        WebSocketClient neonWebsocketClient = new ReactorNettyWebSocketClient();
        neonWebsocketClient.execute(
                URI.create(websocketURI),
                        session -> session.send(Mono.just(session.textMessage(message)))
                                .thenMany(session.receive()
                                        .map(WebSocketMessage::getPayloadAsText).log())
                                .then())
                .block();
    }
}
