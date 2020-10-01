package com.example.algamoney.api.repository.Launch;

import com.example.algamoney.api.model.Launch;
import com.example.algamoney.api.repository.filter.LaunchFilter;
import com.example.algamoney.api.repository.projection.LaunchResume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LaunchRepositoryQuery {

    Page<Launch> search(LaunchFilter launchFilter, Pageable pageable);
    Page<LaunchResume> resume(LaunchFilter launchFilter, Pageable pageable);

}

