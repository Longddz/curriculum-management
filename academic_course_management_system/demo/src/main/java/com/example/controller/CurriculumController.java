// package com.example.controller;

// import com.example.dto.CurriculumDTO;
// import com.example.model.Curriculum;
// import com.example.service.CurriculumService;
// import jakarta.validation.Valid;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/curriculums")
// public class CurriculumController {

//     private static final Logger logger = LoggerFactory.getLogger(CurriculumController.class);

//     @Autowired
//     private CurriculumService curriculumService;

//     @PostMapping("/add")
//     public ResponseEntity<Curriculum> addCurriculum(@Valid @RequestBody CurriculumDTO dto) {
//         logger.info("POST /api/curriculums - Adding curriculum with code: {}", dto.getCurriculumCode());
//         Curriculum curriculum = curriculumService.addCurriculum(dto);
//         return new ResponseEntity<>(curriculum, HttpStatus.CREATED);
//     }

//     @PutMapping("/update/{code}")
//     public ResponseEntity<Curriculum> updateCurriculum(@PathVariable String code, @Valid @RequestBody CurriculumDTO dto) {
//         logger.info("PUT /api/curriculums/{} - Updating curriculum", code);
//         Curriculum updated = curriculumService.updateCurriculum(code, dto);
//         return ResponseEntity.ok(updated);
//     }

//     @DeleteMapping("/delete/{code}")
//     public ResponseEntity<Void> deleteCurriculum(@PathVariable String code) {
//         logger.info("DELETE /api/curriculums/{} - Deleting curriculum", code);
//         curriculumService.deleteCurriculum(code);
//         return ResponseEntity.noContent().build();
//     }

//     @GetMapping("/get")
//     public ResponseEntity<List<Curriculum>> getAllCurriculums() {
//         logger.info("GET /api/curriculums - Retrieving all curriculums");
//         List<Curriculum> curriculums = curriculumService.getAllCurriculums();
//         return ResponseEntity.ok(curriculums);
//     }

//     @GetMapping("/get/{code}")
//     public ResponseEntity<Curriculum> getCurriculumByCode(@PathVariable String code) {
//         logger.info("GET /api/curriculums/{} - Retrieving curriculum", code);
//         Curriculum curriculum = curriculumService.getCurriculumByCode(code);
//         return ResponseEntity.ok(curriculum);
//     }

//     @GetMapping("/filter")
//     public ResponseEntity<List<Curriculum>> filterCurriculums(
//             @RequestParam(defaultValue = "") String name,
//             @RequestParam(defaultValue = "") String lecturer,
//             @RequestParam(defaultValue = "") String identifier,
//             @RequestParam(defaultValue = "") String subject,
//             @RequestParam(defaultValue = "") String branch,
//             @RequestParam(defaultValue = "") String department) {
//         logger.info("GET /api/curriculums/filter - Filtering with criteria: name={}, lecturer={}, identifier={}, subject={}, branch={}, department={}",
//                 name, lecturer, identifier, subject, branch, department);
//         List<Curriculum> curriculums = curriculumService.filterCurriculums(name, lecturer, identifier, subject, branch, department);
//         return ResponseEntity.ok(curriculums);
//     }
// }

/*----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

package com.example.controller;

import com.example.dto.CurriculumDTO;
import com.example.model.Curriculum;
import com.example.service.CurriculumService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/curriculums")
public class CurriculumController {

    private static final Logger logger = LoggerFactory.getLogger(CurriculumController.class);

    private final CurriculumService curriculumService;

    public CurriculumController(CurriculumService curriculumService) {
        this.curriculumService = curriculumService;
    }

    @PostMapping(value = "/add", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DEAN')") 
    public ResponseEntity<Curriculum> addCurriculum(
            @Valid @ModelAttribute CurriculumDTO dto,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        logger.info("POST /api/curriculums - Adding curriculum with code: {}", dto.getCurriculumCode());
        Curriculum curriculum = curriculumService.addCurriculum(dto, file);
        return new ResponseEntity<>(curriculum, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{code}", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DEAN')")
    public ResponseEntity<Curriculum> updateCurriculum(
            @PathVariable String code,
            @Valid @ModelAttribute CurriculumDTO dto,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        logger.info("PUT /api/curriculums/{} - Updating curriculum", code);
        Curriculum updated = curriculumService.updateCurriculum(code, dto, file);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{code}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DEAN')")
    public ResponseEntity<Void> deleteCurriculum(@PathVariable String code) {
        logger.info("DELETE /api/curriculums/{} - Deleting curriculum", code);
        curriculumService.deleteCurriculum(code);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get")
    public ResponseEntity<List<Curriculum>> getAllCurriculums() {
        logger.info("GET /api/curriculums - Retrieving all curriculums");
        List<Curriculum> curriculums = curriculumService.getAllCurriculums();
        return ResponseEntity.ok(curriculums);
    }

    @GetMapping("/get/{code}")
    public ResponseEntity<Curriculum> getCurriculumByCode(@PathVariable String code) {
        logger.info("GET /api/curriculums/{} - Retrieving curriculum", code);
        Curriculum curriculum = curriculumService.getCurriculumByCode(code);
        return ResponseEntity.ok(curriculum);
    }

    // @GetMapping("/filter")
    // public ResponseEntity<List<Curriculum>> filterCurriculums(
    //         @RequestParam(defaultValue = "") String name,
    //         @RequestParam(defaultValue = "") String lecturer,
    //         @RequestParam(defaultValue = "") String identifier,
    //         @RequestParam(defaultValue = "") String subject,
    //         @RequestParam(defaultValue = "") String branch,
    //         @RequestParam(defaultValue = "") String department) {
    //     logger.info("GET /api/curriculums/filter - Filtering with criteria: name={}, lecturer={}, identifier={}, subject={}, branch={}, department={}",
    //             name, lecturer, identifier, subject, branch, department);
    //     List<Curriculum> curriculums = curriculumService.filterCurriculums(name, lecturer, identifier, subject, branch, department);
    //     return ResponseEntity.ok(curriculums);
    // }

    @GetMapping("/filter")
    public ResponseEntity<List<Curriculum>> filterCurriculums(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String lecturer,
            @RequestParam(defaultValue = "") String identifier,
            @RequestParam(defaultValue = "") String subject,
            @RequestParam(defaultValue = "") String branch,
            @RequestParam(defaultValue = "") String department) {
        logger.info("GET /api/curriculums/filter - Filtering with criteria: name={}, lecturer={}, identifier={}, subject={}, branch={}, department={}",
                name, lecturer, identifier, subject, branch, department);
        List<Curriculum> curriculums = curriculumService.filterCurriculums(name, lecturer, identifier, subject, branch, department);
        return ResponseEntity.ok(curriculums);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        logger.error("Error: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Error: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        logger.error("Error: {}", ex.getMessage());
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
