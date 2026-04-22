package com.wookjae.scheduler.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentCreateRequest {

    @NotBlank(message = "내용은 필수입니다.")
    @Size(max = 200, message = "200자 이하여야 합니다.")
    private String content;
}