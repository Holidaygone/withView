package com.ssafy.withview.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PresetDto {
	private Long id;
	private Long userSeq;
	private String stage;
	private String presetName;
	private String presetImgSearchName;
	private LocalDateTime registerTime;
}