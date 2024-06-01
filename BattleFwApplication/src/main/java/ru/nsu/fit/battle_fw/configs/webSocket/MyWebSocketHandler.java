package ru.nsu.fit.battle_fw.configs.webSocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Обработка входящего текстового сообщения от клиента
        String payload = message.getPayload();
        // Логика обработки сообщения и отправка ответа
        session.sendMessage(new TextMessage("Response to client"));
    }
}