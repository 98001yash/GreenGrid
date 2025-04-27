package com.company.GreenGrid.repository;

import com.company.GreenGrid.entities.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue,Long> {
}
