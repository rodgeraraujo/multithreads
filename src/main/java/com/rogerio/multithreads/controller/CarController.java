package com.rogerio.multithreads.controller;

import com.rogerio.multithreads.domain.Car;
import com.rogerio.multithreads.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequestMapping("/api/car")
public class CarController {

    @Autowired
    private CarService carService;

    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
                    produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity uploadFile(@RequestParam(value = "files") MultipartFile[] files) {
        try {
            for (final MultipartFile file : files) {
                carService.saveCars(file);
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE},
                    produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity getAllCars() {
        try {
            CompletableFuture<List<Car>> cars1 = carService.getAllCars();
            CompletableFuture<List<Car>> cars2 = carService.getAllCars();
            CompletableFuture<List<Car>> cars3 = carService.getAllCars();

            List<Car> cars = new ArrayList<>();
            cars.addAll(cars1.get());
            cars.addAll(cars2.get());
            cars.addAll(cars3.get());
            return ResponseEntity.status(HttpStatus.OK).body(cars);
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
