package io.ayesh.sample.repository.impl;

import io.ayesh.sample.model.BatteryCapacity;
import io.ayesh.sample.model.Drone;
import io.ayesh.sample.repository.DroneRepository;
import io.ayesh.sample.repository.impl.mapper.BatteryCapacityRowMapper;
import io.ayesh.sample.repository.impl.mapper.DroneRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class DroneRepositoryImpl implements DroneRepository {
    private static final String CREATE_DRONE_SQL =
            "INSERT INTO drone (serial_number, model, weight_limit, battery_capacity, state) " +
                    "VALUES (:serialNumber, :model, :weightLimit, :batteryCapacity, :state)";
    private static final String DRONE_EXISTS_BY_ID = "SELECT COUNT(*) FROM drone WHERE id= :id";
    private static final String DRONE_EXISTS_BY_SERIAL_NUMBER =
            "SELECT COUNT(*) FROM drone WHERE serial_number= :serialNumber";

    private static final String FIND_DRONE_BY_ID = "SELECT * FROM drone WHERE id= :id";
    private static final String FIND_DRONES_BY_BATTERY_LEVEL =
            "SELECT * FROM drone WHERE battery_capacity < :batteryCapacity";
    private static final String FIND_DRONES_BY_STATE = "SELECT * FROM drone WHERE state in (:states)";
    private static final String FIND_DRONE_BATTERY_CAPACITY = "SELECT id, battery_capacity FROM drone WHERE id= :id";
    private static final RowMapper<Drone> DRONE_ROW_MAPPER = new DroneRowMapper();
    private static final RowMapper<BatteryCapacity> BATTERY_CAPACITY_ROW_MAPPER = new BatteryCapacityRowMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public DroneRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createDrone(Drone drone) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("serialNumber", drone.getSerialNumber())
                .addValue("model", drone.getModel())
                .addValue("weightLimit", drone.getWeightLimit())
                .addValue("batteryCapacity", drone.getBatteryCapacity())
                .addValue("state", drone.getState());
        return jdbcTemplate.update(CREATE_DRONE_SQL, parameters);
    }

    @Override
    public boolean droneExistsById(int droneId) {
        Integer count = jdbcTemplate.queryForObject(
                DRONE_EXISTS_BY_ID,
                new MapSqlParameterSource("id", droneId),
                Integer.class
        );
        return Objects.nonNull(count) && count == 0;
    }

    @Override
    public boolean droneExistsBySerialNumber(String serialNumber) {
        Integer count = jdbcTemplate.queryForObject(
                DRONE_EXISTS_BY_SERIAL_NUMBER,
                new MapSqlParameterSource("serialNumber", serialNumber),
                Integer.class
        );
        return Objects.nonNull(count) && count == 0;
    }

    @Override
    public Drone findDroneById(int droneId) {
        return jdbcTemplate.queryForObject(
                FIND_DRONE_BY_ID,
                new MapSqlParameterSource("id", droneId),
                DRONE_ROW_MAPPER
        );
    }

    @Override
    public List<Drone> findDronesByBatteryLevelLessThan(double batteryLevel) {
        return jdbcTemplate.query(
                FIND_DRONES_BY_BATTERY_LEVEL,
                new MapSqlParameterSource("batteryCapacity", batteryLevel),
                DRONE_ROW_MAPPER
        );
    }

    @Override
    public List<Drone> getDronesForStatus(List<String> droneStatus) {
        return jdbcTemplate.query(
                FIND_DRONES_BY_STATE,
                new MapSqlParameterSource("states", droneStatus),
                DRONE_ROW_MAPPER
        );
    }

    @Override
    public BatteryCapacity getDroneBatterCapacity(int droneId) {
        return jdbcTemplate.queryForObject(
                FIND_DRONE_BATTERY_CAPACITY,
                new MapSqlParameterSource("id", droneId),
                BATTERY_CAPACITY_ROW_MAPPER
        );
    }
}
