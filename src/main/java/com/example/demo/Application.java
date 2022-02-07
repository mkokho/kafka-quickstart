package com.example.demo;

import com.example.demo.iot.TemperatureEvent;
import com.example.demo.iot.TemperatureSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean("sensorsScheduler")
    public ScheduledExecutorService sensorsScheduler() {
        return Executors.newScheduledThreadPool(4);
    }

    @Bean
    public ApplicationRunner runner(
            ApplicationConfig config,
            @Qualifier("sensorsScheduler") ScheduledExecutorService scheduler,
            KafkaProducer kafkaProducer
    ) {
        return args -> {
            // create mock sensors
            var sensors = IntStream.range(0, config.sensorCount)
                    .mapToObj(i -> new TemperatureSensor("sensor_" + i))
                    .collect(Collectors.toList());
            LOGGER.info("Created {} mock sensors", config.sensorCount);

            // every second collect measurement from all sensors and send them these to the Kafka cluster;
            // this a simple approach, if there are too many sensors, one measurement cycle will take more than 1 second
            // maximum throughput depends on network latency and configuration of the producer and the cluster.
            scheduler.scheduleAtFixedRate(() -> {
                var startTime = System.currentTimeMillis();
                sensors.parallelStream()
                        .map(TemperatureEvent::collect)
                        .forEach(kafkaProducer::sendTemperatureEvent);
                var endTime = System.currentTimeMillis();
                LOGGER.info("{}ms elapsed while sending {} mock events", endTime - startTime, sensors.size());
            }, 1, 1, TimeUnit.SECONDS);
        };
    }

}
