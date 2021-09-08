package com.busleiman.products.controllers;

import com.busleiman.products.domain.dtos.SectionDTO;
import com.busleiman.products.domain.dtos.responses.SectionResponse;
import com.busleiman.products.domain.dtos.validationsGroups.Action;
import com.busleiman.products.domain.entities.Section;
import com.busleiman.products.service.SectionService;
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
@RequestMapping("/sections")
public class SectionController {
    @Autowired
    private SectionService sectionService;

    Logger logger = LoggerFactory.getLogger(SectionController.class);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SectionResponse> getSectionById(@PathVariable("id") Long id) throws InterruptedException {

        logger.info("Looking for a section");

        return ResponseEntity.ok(sectionService.findById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SectionResponse>> getSections() {

        return ResponseEntity.ok(sectionService.findAll());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createSection(@Validated(Action.Create.class) @RequestBody SectionDTO sectionDTO) {

        Long sectionId = sectionService.create(sectionDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(sectionId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Section> updateSection(@PathVariable("id") Long id,
                                                 @Validated(Action.Update.class) @RequestBody SectionDTO sectionDTO) {

        sectionService.update(sectionDTO, id);

        return ResponseEntity.status(204).build();
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteSection(@PathVariable("id") Long id) {

        sectionService.delete(id);

        return ResponseEntity.status(204).build();
    }
}
