package com.example.algamoney.api.resource;

import com.example.algamoney.api.event.ResourceCreatedEvent;
import com.example.algamoney.api.exceptionhandler.AlgamoneyExceptionHandler;
import com.example.algamoney.api.model.Launch;
import com.example.algamoney.api.repository.LaunchRepository;
import com.example.algamoney.api.repository.filter.LaunchFilter;
import com.example.algamoney.api.service.LaunchService;
import com.example.algamoney.api.service.exception.PersonNonExistentOrInactiveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/launchs")
public class LaunchResource {

    @Autowired
    private LaunchRepository launchRepository;

    @Autowired
    private LaunchService launchService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private MessageSource messageSource;

//    @GetMapping
//    public List<Launch> list(){
//        return launchRepository.findAll();
//    }

    @GetMapping
    public List<Launch> search(LaunchFilter launchFilter){
        return launchRepository.search(launchFilter);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Launch> listLaunch(@PathVariable Long code){
       Optional<Launch> launch = launchRepository.findById(code);
        return launch.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Launch> create(@Valid @RequestBody Launch launch, HttpServletResponse response){
        Launch launchSave = launchService.save(launch);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, launchSave.getCode()));
        return ResponseEntity.status(HttpStatus.CREATED).body(launchSave);
    }

    @ExceptionHandler( {PersonNonExistentOrInactiveException.class} )
    public ResponseEntity<Object> handlePersonNonExistentOrInactiveException(PersonNonExistentOrInactiveException ex){
        String messageUser = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
        String messageDeveloper = ex.toString();
        List<AlgamoneyExceptionHandler.Error> errors = Collections.singletonList(new AlgamoneyExceptionHandler.Error(messageUser, messageDeveloper));
        return ResponseEntity.badRequest().body(errors);
    }
}
