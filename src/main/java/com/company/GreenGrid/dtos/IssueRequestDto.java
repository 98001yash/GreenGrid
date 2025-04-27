package com.company.GreenGrid.dtos;


import lombok.Data;

@Data
public class IssueRequestDto {

    private String title;
    private String description;
    private String category;
    private Double latitude;
    private Double longitude;
    private String imageUrl;
}
