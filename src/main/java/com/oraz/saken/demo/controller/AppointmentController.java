package com.oraz.saken.demo.controller;

import com.oraz.saken.demo.dto.appointment.AppointmentRequest;
import com.oraz.saken.demo.dto.appointment.AppointmentResponse;
import com.oraz.saken.demo.dto.appointment.AssignMechanicRequest;
import com.oraz.saken.demo.dto.appointment.UpdateAppointmentStatusRequest;
import com.oraz.saken.demo.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/my")
    public ResponseEntity<List<AppointmentResponse>> getMyAppointments(Principal principal) {
        String email = principal.getName();

        return ResponseEntity.ok(appointmentService.getByCurrentUser(email));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping
    public ResponseEntity<AppointmentResponse> create(@RequestBody @Valid AppointmentRequest request, Principal principal) {
        String email = principal.getName();

        return ResponseEntity.ok(appointmentService.create(request, email));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/assign-mechanic")
    public ResponseEntity<AppointmentResponse> assignMechanic(@PathVariable Long id, @RequestBody @Valid AssignMechanicRequest request) {
        return ResponseEntity.ok(appointmentService.assignMechanic(id, request));
    }

    @PreAuthorize("hasRole('MECHANIC')")
    @PutMapping("/{id}/status")
    public ResponseEntity<AppointmentResponse> updateStatus(@PathVariable Long id, @RequestBody @Valid UpdateAppointmentStatusRequest request, Principal principal) {
        String email = principal.getName();

        return ResponseEntity.ok(appointmentService.updateStatus(id, request, email));
    }
}
