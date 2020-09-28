package com.example.algamoney.api.repository;

import com.example.algamoney.api.model.Launch;
import com.example.algamoney.api.repository.Launch.LaunchRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaunchRepository extends JpaRepository<Launch, Long>, LaunchRepositoryQuery{
}
