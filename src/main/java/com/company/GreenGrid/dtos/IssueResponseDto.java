package com.company.GreenGrid.dtos;


import com.company.GreenGrid.enums.IssueStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class IssueResponseDto {

    private Long id;
    private String title;
    private String description;
    private String category;
    private Double latitude;
    private Double longitude;
    private String imageUrl;
    private IssueStatus status;
    private LocalDateTime createdAt;
}
