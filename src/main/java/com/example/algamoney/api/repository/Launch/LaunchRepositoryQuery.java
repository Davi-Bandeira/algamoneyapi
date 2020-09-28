package com.example.algamoney.api.repository.Launch;

import com.example.algamoney.api.model.Launch;
import com.example.algamoney.api.repository.filter.LaunchFilter;

import java.util.List;

public interface LaunchRepositoryQuery {

    List<Launch> search(LaunchFilter launchFilter);

}

