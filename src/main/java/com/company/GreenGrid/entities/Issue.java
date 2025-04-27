package com.company.GreenGrid.entities;


import com.company.GreenGrid.enums.IssueStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "issues")
@Builder
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String category;
    private Double latitude;
    private Double longitude;
    private String imageUrl;


    @Enumerated(EnumType.STRING)
    private IssueStatus status;

    private LocalDateTime createdAt;
    private long createdBy;
}
