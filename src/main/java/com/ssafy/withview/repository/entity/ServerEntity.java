package com.ssafy.withview.repository.entity;

import javax.persistence.*;

import com.ssafy.withview.repository.dto.ServerDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.catalina.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "server")
public class ServerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long seq;
	private String name;
	private int limitChannel=5;
	private int hostSeq;
	private String backgroundImgSearchName;
	private String backgroundImgOriginalName;

	@OneToMany(mappedBy="userEntity")
	private List<UserServerEntity> users = new ArrayList<>();

	@Builder
	public ServerEntity(String name, int limitChannel, int hostSeq, String backgroundImgSearchName, String backgroundImgOriginalName) {
		this.name = name;
		this.limitChannel = limitChannel;
		this.hostSeq = hostSeq;
		this.backgroundImgSearchName = backgroundImgSearchName;
		this.backgroundImgOriginalName = backgroundImgOriginalName;
	}

	public static ServerDto toDto(ServerEntity serverEntity){
		if(serverEntity == null){
			return null;
		}
		return ServerDto.builder()
				.seq(serverEntity.getSeq())
			.name(serverEntity.getName())
			.limitChannel(serverEntity.getLimitChannel())
			.hostSeq(serverEntity.getHostSeq())
			.backgroundImgSearchName(serverEntity.getBackgroundImgSearchName())
			.backgroundImgOriginalName(serverEntity.getBackgroundImgOriginalName())
			.build();
	}
}