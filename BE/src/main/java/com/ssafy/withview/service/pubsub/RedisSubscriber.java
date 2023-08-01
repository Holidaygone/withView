package com.ssafy.withview.service.pubsub;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.withview.repository.dto.ChatMessageDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

	private final ObjectMapper objectMapper;
	private final RedisTemplate redisTemplate;
	private final SimpMessageSendingOperations messagingTemplate;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			// redis에서 발행된 데이터를 받아 deserialize
			String publishMessage = (String)redisTemplate.getStringSerializer().deserialize(message.getBody());
			// ChatMessage 객체로 매핑
			ChatMessageDTO roomMessage = objectMapper.readValue(publishMessage, ChatMessageDTO.class);
			// WebSocket 구독자에게 채팅 메시지 Send
			messagingTemplate.convertAndSend("/api/sub/chat/room/" + roomMessage.getServerSeq(), roomMessage);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
