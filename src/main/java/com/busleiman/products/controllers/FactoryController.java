package com.busleiman.products.controllers;

import com.busleiman.products.domain.dtos.FactoryDTO;
import com.busleiman.products.domain.dtos.responses.FactoryResponse;
import com.busleiman.products.domain.dtos.validationsGroups.Action;
import com.busleiman.products.domain.entities.Factory;
import com.busleiman.products.service.FactoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/factories")
public class FactoryController {
    @Autowired
    private FactoryService factoryService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FactoryResponse> getFactoryById(@PathVariable("id") Long id) throws InterruptedException {

        logger.info("Looking for a factory");

        return ResponseEntity.ok(factoryService.findById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FactoryResponse>> getFactorys() {

        return ResponseEntity.ok(factoryService.findAll());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createFactory(@Validated(Action.Create.class) @RequestBody FactoryDTO factoryDTO) {

        Long factoryId = factoryService.create(factoryDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(factoryId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Factory> updateFactory(@PathVariable("id") Long id,
                                                 @Validated(Action.Update.class) @RequestBody FactoryDTO factoryDTO) {

        factoryService.update(factoryDTO, id);

        return ResponseEntity.status(204).build();
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteFactory(@PathVariable("id") Long id) {

        factoryService.delete(id);

        return ResponseEntity.status(204).build();
    }
}
