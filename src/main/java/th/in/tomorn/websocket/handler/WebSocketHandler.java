package th.in.tomorn.websocket.handler;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    ArrayList<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("Welcome session id=" + session.getId());
        session.sendMessage(new TextMessage("{\"name\":\"\",\"message\":\"session id : " + session.getId() + "\"}"));
        sessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        LOGGER.info("session id:" + session.getId() + "  message:" + message.getPayload());
        TextMessage msg = new TextMessage("" + message.getPayload());
        // session.sendMessage(msg);
        for (WebSocketSession s : sessions) {
            s.sendMessage(msg);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        LOGGER.info("session id:" + session.getId() + " Closed.");
        sessions.remove(session);
    }
}