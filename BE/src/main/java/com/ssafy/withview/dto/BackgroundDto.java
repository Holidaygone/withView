package com.ssafy.withview.dto;

import java.time.LocalDateTime;

import com.ssafy.withview.entity.BackgroundEntity;
import com.ssafy.withview.entity.StickerEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BackgroundDto {
    private Long seq;
    private String writer;
    private String originalName;
    private String searchName;
    private Integer count;
    private LocalDateTime createTime;
    private Long userSeq;
    private String keyword;

    public static BackgroundEntity toEntity(BackgroundDto stickerDto){
        if(stickerDto == null){
            return null;
        }
        return BackgroundEntity.builder()
            .writer(stickerDto.getWriter())
            .count(stickerDto.getCount())
            .originalName(stickerDto.getOriginalName())
            .searchName(stickerDto.getSearchName())
            .userSeq(stickerDto.getUserSeq())
            .build();
    }
}
