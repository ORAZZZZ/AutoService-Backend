package com.oraz.saken.demo.controller;

import com.oraz.saken.demo.dto.service_item.ServiceItemRequest;
import com.oraz.saken.demo.dto.service_item.ServiceItemResponse;
import com.oraz.saken.demo.service.ServiceItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceItemController {
    private final ServiceItemService serviceItemService;

    public ServiceItemController(ServiceItemService serviceItemService) {
        this.serviceItemService = serviceItemService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceItemResponse>> getAll(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(serviceItemService.getAll(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceItemResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceItemService.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ServiceItemResponse> create(@RequestBody @Valid ServiceItemRequest request) {
        return ResponseEntity.ok(serviceItemService.create(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ServiceItemResponse> update(@PathVariable Long id, @RequestBody @Valid ServiceItemRequest request) {
        return ResponseEntity.ok(serviceItemService.update(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceItemService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
