package com.oraz.saken.demo.service;

import com.oraz.saken.demo.dto.appointment.AppointmentRequest;
import com.oraz.saken.demo.dto.appointment.AppointmentResponse;
import com.oraz.saken.demo.dto.appointment.AssignMechanicRequest;
import com.oraz.saken.demo.dto.appointment.UpdateAppointmentStatusRequest;
import com.oraz.saken.demo.dto.car.CarResponse;
import com.oraz.saken.demo.dto.service_item.ServiceItemResponse;
import com.oraz.saken.demo.entity.*;
import com.oraz.saken.demo.exception.ForbiddenException;
import com.oraz.saken.demo.exception.NotFoundException;
import com.oraz.saken.demo.repository.AppointmentRepository;
import com.oraz.saken.demo.repository.CarRepository;
import com.oraz.saken.demo.repository.ServiceItemRepository;
import com.oraz.saken.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final CarRepository carRepository;
    private final ServiceItemRepository serviceItemRepository;
    private final UserRepository userRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, CarRepository carRepository, ServiceItemRepository serviceItemRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.carRepository = carRepository;
        this.serviceItemRepository = serviceItemRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getByCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        List<Appointment> appointments = appointmentRepository.findByCarOwnerId(user.getId());

        return appointments.stream().map(appointment -> mapToResponse(appointment)).toList();
    }

    public AppointmentResponse create(AppointmentRequest request, String email) {
        Car car = carRepository.findById(request.getCarId())
                .orElseThrow(() -> new NotFoundException("Машина не найдена"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (!car.getOwner().getId().equals(user.getId())) {
            throw new ForbiddenException("Вы не владелец этой машины");
        }

        ServiceItem serviceItem = serviceItemRepository.findById(request.getServiceId())
                .orElseThrow(() -> new NotFoundException("Услуга не найдена"));

        Appointment appointment = new Appointment();
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setCar(car);
        appointment.setServiceItem(serviceItem);

        appointmentRepository.save(appointment);

        return mapToResponse(appointment);
    }

    public AppointmentResponse assignMechanic(Long appointmentId, AssignMechanicRequest request) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("Заявка не найдена"));

        User mechanic = userRepository.findById(request.getMechanicId())
                .orElseThrow(() -> new NotFoundException("Механик не найден"));

        boolean isMechanic = mechanic.getRoles().stream()
                .anyMatch(role -> role.getName().equals("MECHANIC"));

        if (!isMechanic) {
            throw new ForbiddenException("Пользователь не является механиком");
        }

        appointment.setMechanic(mechanic);
        appointment.setStatus(AppointmentStatus.APPROVED);

        appointmentRepository.save(appointment);

        return mapToResponse(appointment);
    }

    public AppointmentResponse updateStatus(Long appointmentId, UpdateAppointmentStatusRequest request, String email) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("Заявка не найдена"));

        User mechanic = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (!mechanic.equals(appointment.getMechanic())) {
            throw new ForbiddenException("Вы не назначены на эту заявку");
        }

        appointment.setStatus(request.getStatus());

        appointmentRepository.save(appointment);

        return mapToResponse(appointment);
    }

    private AppointmentResponse mapToResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getAppointmentTime(),
                appointment.getStatus(),
                new CarResponse(
                        appointment.getCar().getId(),
                        appointment.getCar().getBrand(),
                        appointment.getCar().getModel(),
                        appointment.getCar().getLicensePlate()
                ),
                new ServiceItemResponse(
                        appointment.getServiceItem().getId(),
                        appointment.getServiceItem().getName(),
                        appointment.getServiceItem().getDescription(),
                        appointment.getServiceItem().getPrice(),
                        appointment.getServiceItem().getDurationInMinutes()
                ),
                appointment.getMechanic() != null
                        ? appointment.getMechanic().getName() + " " + appointment.getMechanic().getSurname()
                        : null
        );
    }
}
