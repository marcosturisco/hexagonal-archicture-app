package com.turisco.learning.adapter.inbound.rest.controller;

import com.turisco.learning.adapter.inbound.rest.dto.AnimalDTO;
import com.turisco.learning.adapter.inbound.rest.enumeration.HTTPStatus;
import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;
import com.turisco.learning.adapter.outbound.report.ReportService;
import com.turisco.learning.adapter.outbound.report.conf.ReportConf;
import com.turisco.learning.domain.exception.InvalidAnimalException;
import com.turisco.learning.domain.service.AnimalService;
import com.turisco.learning.exception.CreateAnimalException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.turisco.learning.adapter.outbound.report.enums.MediaTypeEnum.PDF;
import static com.turisco.learning.adapter.outbound.report.enums.NameReportEnum.ANIMAL_REPORT;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/zoo")
@RequiredArgsConstructor
@Tag(name = "Animal Administration", description = "Endpoints to work with Animal Registration")
public class AnimalController {

    private final AnimalService service;
    private final ReportService report;
    private final PagedResourcesAssembler<AnimalAttributeInterface> pagedResourcesAssembler;

    @GetMapping("/all")
    @RolesAllowed({"USER", "ADMIN"})
    @Operation(summary = "List all animals", description = "Return a list of registered animals")
    public ResponseEntity<CollectionModel<EntityModel<AnimalAttributeInterface>>> exposeAllAnimals() {
        List<AnimalAttributeInterface> animals = service.findAll();

        List<EntityModel<AnimalAttributeInterface>> animalModels = animals.stream()
                .map(animal -> EntityModel.of(animal,
                        linkTo(methodOn(AnimalController.class).exposeAnimal(animal.getId()))
                                .withSelfRel()
                                .withType(HTTPStatus.GET.name())
                ))
                .toList();

        CollectionModel<EntityModel<AnimalAttributeInterface>> collectionModel = CollectionModel.of(animalModels,
                linkTo(methodOn(AnimalController.class).exposeAllAnimals()).withSelfRel()
                        .withType(HTTPStatus.GET.name()),
                linkTo(methodOn(AnimalController.class).exposeReportAnimals()).withRel("report-animals")
                        .withType(HTTPStatus.GET.name())
        );

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping
    @RolesAllowed({"USER", "ADMIN"})
    @Operation(summary = "Paginated List of all animals", description = "Return a paginated list of registered animals")
    public ResponseEntity<PagedModel<EntityModel<AnimalAttributeInterface>>> exposePaginatedAnimals(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<AnimalAttributeInterface> page = service.findAll(pageable);

        PagedModel<EntityModel<AnimalAttributeInterface>> pagedModel =
                pagedResourcesAssembler.toModel(page, animal ->
                        EntityModel.of(animal,
                                linkTo(methodOn(AnimalController.class).exposeAnimal(animal.getId()))
                                        .withSelfRel()
                                        .withType(HTTPStatus.GET.name())
                        )
                );
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/report")
    @RolesAllowed("ADMIN")
    @Operation(summary = "Report all animals", description = "Return a report of animals")
    public ResponseEntity<byte[]> exposeReportAnimals() {
        List<AnimalAttributeInterface> animals = service.findAll();
        var conf = ReportConf.builder().nameReportEnum(ANIMAL_REPORT).mediaType(PDF).build();
        return report.getPdfResponse(conf, Map.of(), animals);
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    @Operation(summary = "Create an animal", description = "Create an animal and classify the species by name")
    public ResponseEntity<EntityModel<AnimalAttributeInterface>> exposeCreateAnimal(
            @Valid @RequestBody AnimalDTO animalDTO) {
        try {
            service.generateSpeciesName(animalDTO, false);
            AnimalAttributeInterface animal = service.create(animalDTO);
            log.info("Animal Created Successfully!");

            var hateoas = EntityModel.of(animal,
                    linkTo(methodOn(AnimalController.class).exposeAnimal(animal.getId()))
                            .withSelfRel()
                            .withType(HTTPStatus.GET.name()),
                    linkTo(methodOn(AnimalController.class).exposeAllAnimals())
                            .withRel("all-animals")
                            .withType(HTTPStatus.GET.name())
            );
            return ResponseEntity
                    .created(linkTo(methodOn(AnimalController.class).exposeAnimal(animal.getId())).toUri())
                    .body(hateoas);
        } catch (InvalidAnimalException | ExecutionException e) {
            log.error("Invalid Animal!");
            throw new CreateAnimalException(animalDTO);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Species Classifier was Interrupted!");
            throw new CreateAnimalException(animalDTO);
        }
    }

    @GetMapping("/{id}")
    @RolesAllowed("USER")
    @Operation(summary = "Get an animal by id", description = "Return an registered animal by id")
    public EntityModel<AnimalAttributeInterface> exposeAnimal(@PathVariable Long id) {
        var animal = service.get(id);

        return EntityModel.of(animal,
                linkTo(methodOn(AnimalController.class).exposeAnimal(id)).withSelfRel(),
                linkTo(methodOn(AnimalController.class).exposeAllAnimals()).withRel("all-animals")
        );
    }
}
