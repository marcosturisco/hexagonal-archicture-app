package com.turisco.learning.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/diagnostic")
public class DiagnosticController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> exposeDiagnostic() {
        return ResponseEntity.ok(Map.of("status", "connected"));
    }
}