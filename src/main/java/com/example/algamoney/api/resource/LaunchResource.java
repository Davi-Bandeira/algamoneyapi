package com.example.algamoney.api.resource;

import com.example.algamoney.api.event.ResourceCreatedEvent;
import com.example.algamoney.api.exceptionhandler.AlgamoneyExceptionHandler;
import com.example.algamoney.api.model.Launch;
import com.example.algamoney.api.repository.LaunchRepository;
import com.example.algamoney.api.repository.filter.LaunchFilter;
import com.example.algamoney.api.repository.projection.LaunchResume;
import com.example.algamoney.api.service.LaunchService;
import com.example.algamoney.api.service.exception.PersonNonExistentOrInactiveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANÇAMENTO') and #oauth2.hasScope('read')")
    public Page<Launch> search(LaunchFilter launchFilter, Pageable pageable){
        return launchRepository.search(launchFilter, pageable);
    }

    @GetMapping(params = "resume")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANÇAMENTO') and #oauth2.hasScope('read')")
    public Page<LaunchResume> resume(LaunchFilter launchFilter, Pageable pageable){
        return launchRepository.resume(launchFilter, pageable);
    }


    @GetMapping("/{code}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANÇAMENTO') and #oauth2.hasScope('read')")
    public ResponseEntity<Launch> listLaunch(@PathVariable Long code){
       Optional<Launch> launch = launchRepository.findById(code);
        return launch.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANÇAMENTO') and #oauth2.hasScope('write')")
    public ResponseEntity<Launch> create(@Valid @RequestBody Launch launch, HttpServletResponse response){
        Launch launchSave = launchService.save(launch);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, launchSave.getCode()));
        return ResponseEntity.status(HttpStatus.CREATED).body(launchSave);
    }

    @DeleteMapping("{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_REMOVER_LANÇAMENTO') and #oauth2.hasScope('write')")
    public void delete(@PathVariable Long code){
        launchRepository.deleteById(code);
    }

    @ExceptionHandler( {PersonNonExistentOrInactiveException.class} )
    public ResponseEntity<Object> handlePersonNonExistentOrInactiveException(PersonNonExistentOrInactiveException ex){
        String messageUser = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
        String messageDeveloper = ex.toString();
        List<AlgamoneyExceptionHandler.Error> errors = Collections.singletonList(new AlgamoneyExceptionHandler.Error(messageUser, messageDeveloper));
        return ResponseEntity.badRequest().body(errors);
    }
}
