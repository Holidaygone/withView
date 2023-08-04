package com.ssafy.withview.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.ssafy.withview.service.pubsub.RedisSubscriber;

@Configuration
public class RedisConfig {

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(host, port);
	}

	@Bean
	public ChannelTopic chatRoomChannelTopic() {
		return new ChannelTopic("chatroom");
	}

	@Bean
	public ChannelTopic channelValueChannelTopic() {
		return new ChannelTopic("channelValue");
	}

	// redis pub/sub 메시지를 처리하는 listener 설정
	@Bean
	public RedisMessageListenerContainer redisMessageListener(RedisConnectionFactory connectionFactory,
		MessageListenerAdapter chatRoomListenerAdapter, MessageListenerAdapter channelValueListenerAdapter,
		ChannelTopic chatRoomChannelTopic, ChannelTopic channelValueChannelTopic) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(chatRoomListenerAdapter, chatRoomChannelTopic);
		container.addMessageListener(channelValueListenerAdapter, channelValueChannelTopic);
		return container;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
		return redisTemplate;
	}

	@Bean
	public MessageListenerAdapter chatRoomListenerAdapter(RedisSubscriber subscriber) {
		return new MessageListenerAdapter(subscriber);
	}

	@Bean
	public MessageListenerAdapter channelValueListenerAdapter(RedisSubscriber subscriber) {
		return new MessageListenerAdapter(subscriber, "sendChannelValue");
	}
}
