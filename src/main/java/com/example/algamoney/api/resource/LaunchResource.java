package com.example.algamoney.api.resource;

import com.example.algamoney.api.model.Launch;
import com.example.algamoney.api.repository.LaunchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/launchs")
public class LaunchResource {

    @Autowired
    private LaunchRepository launchRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Launch> list(){
        return launchRepository.findAll();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Launch> listLaunch(@PathVariable Long code){
       Optional<Launch> launch = launchRepository.findById(code);
        return launch.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
