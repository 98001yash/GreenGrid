package com.company.GreenGrid.controller;


import com.company.GreenGrid.dtos.IssueRequestDto;
import com.company.GreenGrid.dtos.IssueResponseDto;
import com.company.GreenGrid.service.IssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
@Slf4j
public class IssueController {

    private final IssueService issueService;

    @PostMapping
    public IssueResponseDto reportIssue(@RequestBody IssueRequestDto issueRequestDto,
                                        @RequestParam Long userId){
        log.info("Api called: Report issue by userId: {}",userId);
        return issueService.reportIssue(issueRequestDto, userId);
    }

    @GetMapping
    public List<IssueResponseDto> getAllIssues(){
        log.info("Api called: Get all Issues");
        return issueService.getAllIssues();
    }
}
