package io.ayesh.sample;

import io.ayesh.sample.hateoas.DroneHateoasModel;
import io.ayesh.sample.model.BatteryCapacity;
import io.ayesh.sample.model.Drone;
import io.ayesh.sample.model.DroneModel;
import io.ayesh.sample.model.DroneStatus;
import io.ayesh.sample.model.Medication;
import io.ayesh.sample.responses.ServiceResponses;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DispatchControllerServiceAppTest {
    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void registerDrone() {
        Drone drone = new Drone();
        drone.setSerialNumber("S123");
        drone.setModel(DroneModel.Lightweight);
        drone.setWeightLimit(300.00);
        drone.setBatteryCapacity(0.75);
        DroneHateoasModel responseBody = restTemplate.postForObject(
                String.format("http://localhost:%d/dispatch-controller/drones", port),
                drone,
                DroneHateoasModel.class
        );
        assertThat(responseBody.getId()).isNotNull().isGreaterThan(0);
        assertThat(DroneStatus.valueOf(responseBody.getState())).isEqualTo(DroneStatus.IDLE);
    }

    @Test
    public void getDronesAvailableForLoading() {
        ResponseEntity<CollectionModel<DroneHateoasModel>> responseEntity = restTemplate.exchange(
                String.format("http://localhost:%d/dispatch-controller/drones", port),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        CollectionModel<DroneHateoasModel> responsePayload = responseEntity.getBody();
        assertThat(responsePayload).isNotNull();
        assertThat(responsePayload.getContent().size()).isGreaterThan(0);
    }

    @Test
    public void getBatteryCapacity() {
        ResponseEntity<BatteryCapacity> responseEntity = restTemplate.exchange(
                String.format("http://localhost:%d/dispatch-controller/drones/2/battery-capacity", port),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        BatteryCapacity responsePayload = responseEntity.getBody();
        assertThat(responsePayload).isNotNull();
        assertThat(responsePayload.getDroneId()).isNotNull().isGreaterThan(0);
        assertThat(responsePayload.getBatteryPercentage()).isNotNull();
    }

    @Test
    public void getMedications() {
        ResponseEntity<List<Medication>> responseEntity = restTemplate.exchange(
                String.format("http://localhost:%d/dispatch-controller/drones/2/medications", port),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Medication> responsePayload = responseEntity.getBody();
        assertThat(responsePayload).isNotNull();
        assertThat(responsePayload.size()).isGreaterThan(0);
    }

    @Test
    public void loadMedications() {
        Medication med1 = new Medication();
        med1.setName("Medication_1");
        med1.setWeight(30.0);
        med1.setCode("MED001");
        med1.setImage("https://example.com/medication1.png");
        Medication med2 = new Medication();
        med2.setName("Medication_2");
        med2.setWeight(70.0);
        med2.setCode("MED002");
        med2.setImage("https://example.com/medication2.png");
        HttpEntity<List<Medication>> requestEntity = new HttpEntity<>(Arrays.asList(med1, med2));
        ResponseEntity<ServiceResponses.CommonResponse> responseEntity = restTemplate.exchange(
                String.format("http://localhost:%d/dispatch-controller/drones/1/medications", port),
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {}
        );
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        List<Medication> responsePayload = requestEntity.getBody();
        assertThat(responsePayload).isNotNull();
    }
}
