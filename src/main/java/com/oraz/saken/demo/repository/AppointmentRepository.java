package com.oraz.saken.demo.repository;

import com.oraz.saken.demo.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByCarOwnerId(Long ownerId);
    List<Appointment> findByMechanicId(Long mechanicId);
}
