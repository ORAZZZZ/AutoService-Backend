package com.oraz.saken.demo.service;

import com.oraz.saken.demo.dto.car.CarRequest;
import com.oraz.saken.demo.dto.car.CarResponse;
import com.oraz.saken.demo.entity.Car;
import com.oraz.saken.demo.entity.User;
import com.oraz.saken.demo.exception.ForbiddenException;
import com.oraz.saken.demo.exception.NotFoundException;
import com.oraz.saken.demo.repository.CarRepository;
import com.oraz.saken.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarService {
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public CarService(CarRepository carRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<CarResponse> getUserCars(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        return carRepository.findByOwnerId(user.getId())
                .stream()
                .map(car -> new CarResponse(car.getId(), car.getBrand(), car.getModel(), car.getLicensePlate()))
                .collect(Collectors.toList());
    }

    public CarResponse createCar(CarRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Car car = new Car();
        car.setBrand(request.getBrand());
        car.setModel(request.getModel());
        car.setLicensePlate(request.getLicensePlate());
        car.setOwner(user);

        carRepository.save(car);

        return new CarResponse(car.getId(), car.getBrand(), car.getModel(), car.getLicensePlate());
    }

    public CarResponse updateCar(Long carId, CarRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Машина не найдена"));

        if (!car.getOwner().getId().equals(user.getId())) {
            throw new ForbiddenException("Вы не можете редактировать чужую машину");
        }

        car.setBrand(request.getBrand());
        car.setModel(request.getModel());
        car.setLicensePlate(request.getLicensePlate());

        carRepository.save(car);

        return new CarResponse(car.getId(), car.getBrand(), car.getModel(), car.getLicensePlate());
    }

    public void deleteCar(Long carId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Машина не найдена"));

        if (!car.getOwner().getId().equals(user.getId())) {
            throw new ForbiddenException("Вы не можете удалить чужую машину");
        }

        carRepository.delete(car);
    }
}
