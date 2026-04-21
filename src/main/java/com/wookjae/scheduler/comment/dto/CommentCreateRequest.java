package com.wookjae.scheduler.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentCreateRequest {

    @NotBlank
    @Size(max = 200)
    private String content;
}