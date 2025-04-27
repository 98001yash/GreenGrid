package com.company.GreenGrid.service;


import com.company.GreenGrid.dtos.IssueRequestDto;
import com.company.GreenGrid.dtos.IssueResponseDto;
import com.company.GreenGrid.entities.Issue;
import com.company.GreenGrid.enums.IssueStatus;
import com.company.GreenGrid.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueService {

    private final IssueRepository issueRepository;
    private final ModelMapper modelMapper;

    public IssueResponseDto reportIssue(IssueRequestDto dto, Long userId){
        log.info("Reporting a new issue: {}",dto.getTitle());

        Issue issue = modelMapper.map(dto,Issue.class);
        issue.setStatus(IssueStatus.PENDING);
        issue.setCreatedAt(LocalDateTime.now());
        issue.setCreatedBy(userId);

        Issue savedIssue = issueRepository.save(issue);

        log.info("Issue saved successfully with ID: {}",savedIssue.getId());
        return modelMapper.map(savedIssue, IssueResponseDto.class);
    }

    public List<IssueResponseDto> getAllIssues() {
        log.info("Fetching all issues...");

        List<Issue> issues = issueRepository.findAll();
        return issues.stream()
                .map(issue -> modelMapper.map(issue, IssueResponseDto.class))
                .collect(Collectors.toList());
    }
}
