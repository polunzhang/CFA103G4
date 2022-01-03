package idv.order.websocket.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/OrderStatusChange/{fromWhere}", configurator = ServletAwareConfig.class)

public class OrderStautsChange {
	private static final Set<Session> connectedSessions = Collections.synchronizedSet(new HashSet<>());
	private EndpointConfig config;

	/*
	 * �p�G�Q���oHttpSession�PServletContext������@
	 * ServerEndpointConfig.Configurator.modifyHandshake()�A
	 * �Ѧ�https://stackoverflow.com/questions/21888425/accessing-servletcontext-and-
	 * httpsession-in-onmessage-of-a-jsr-356-serverendpoint
	 */
	@OnOpen
	public void onOpen(@PathParam("fromWhere") String fromWhere, Session userSession) throws IOException {
		connectedSessions.add(userSession);
		String text = String.format("Session ID = %s, connected; userName = %s", userSession.getId(), fromWhere);
		System.out.println(text);
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
//		HttpSession httpSession = (HttpSession) config.getUserProperties().get("httpSession");
//		ServletContext servletContext = httpSession.getServletContext();
//		httpSession.getAttribute(message);
		for (Session session : connectedSessions) {
			if (session.isOpen())
				session.getAsyncRemote().sendText(message);
		}
		System.out.println("Message received: " + message);
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		connectedSessions.remove(userSession);
		String text = String.format("session ID = %s, disconnected; close code = %d; reason phrase = %s",
				userSession.getId(), reason.getCloseCode().getCode(), reason.getReasonPhrase());
		System.out.println(text);
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

}
