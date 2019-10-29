package com.rogerio.multithreads.service;

import com.rogerio.multithreads.domain.Car;
import com.rogerio.multithreads.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Async
    public CompletableFuture<List<Car>> saveCars(final MultipartFile file) throws Exception {
        final long start = System.currentTimeMillis();
        List<Car> cars = parseCSVFile(file);
        log.info("Salvando lista de carros de tamanho {}", cars.size());
        cars = carRepository.saveAll(cars);
        log.info("Tempo decorrido: {}", (System.currentTimeMillis() - start));
        return CompletableFuture.completedFuture(cars);
    }

    private List<Car> parseCSVFile(final MultipartFile file) throws Exception {
        final List<Car> cars=new ArrayList<>();
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line=br.readLine()) != null) {
                    final String[] data=line.split(";");
                    final Car car=new Car();
                    car.setFabricante(data[0]);
                    car.setModelo(data[1]);
                    car.setTipo(data[2]);
                    cars.add(car);
                }
                return cars;
            }
        } catch(final IOException e) {
            log.error("Falha ao criar arquivo CSV {}", e);
            throw new Exception("Falha ao criar arquivo CSV {}", e);
        }
    }

    @Async
    public CompletableFuture<List<Car>> getAllCars() {
        log.info("Requisição para buscar uma lista de carros");
        final List<Car> cars = carRepository.findAll();
        return CompletableFuture.completedFuture(cars);
    }
}
