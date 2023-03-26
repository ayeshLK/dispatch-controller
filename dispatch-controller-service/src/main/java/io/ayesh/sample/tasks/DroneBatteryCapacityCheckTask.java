package io.ayesh.sample.tasks;

import io.ayesh.sample.log.audit.AuditLog;
import io.ayesh.sample.model.Drone;
import io.ayesh.sample.model.DroneStatus;
import io.ayesh.sample.repository.DroneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class DroneBatteryCapacityCheckTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(DroneBatteryCapacityCheckTask.class);

    private final DroneRepository droneRepository;

    @Autowired
    public DroneBatteryCapacityCheckTask(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Scheduled(initialDelay = 5, fixedDelay = 10, timeUnit = TimeUnit.MINUTES)
    public void checkBatteryCapacity() {
        try {
            LOGGER.info("Running drone batter-capacity test task");
            List<Drone> drones = droneRepository.getAllDrones();
            drones.forEach(AuditLog::log);
            List<Integer> drainedDrones = drones.stream()
                    .filter(this::isDrained)
                    .map(Drone::getId).toList();
            int result = droneRepository.batchUpdateDroneStatus(drainedDrones, DroneStatus.DRAINED);
        } catch (DataAccessException e) {
            LOGGER.error("Error occurred while processing drained drones");
        }
    }

    private boolean isDrained(Drone drone) {
        // check whether it is already marked as drained
        if (DroneStatus.DRAINED.equals(drone.getState())) {
            return false;
        }
        return drone.getBatteryCapacity() < 0.25;
    }
}
