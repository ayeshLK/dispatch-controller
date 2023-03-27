package io.ayesh.sample.repository.impl;

import io.ayesh.sample.model.BatteryCapacity;
import io.ayesh.sample.model.Drone;
import io.ayesh.sample.model.DroneStatus;
import io.ayesh.sample.repository.DroneRepository;
import io.ayesh.sample.repository.impl.mapper.BatteryCapacityRowMapper;
import io.ayesh.sample.repository.impl.mapper.DroneRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class DroneRepositoryImpl implements DroneRepository {
    private static final String CREATE_DRONE_SQL =
            "INSERT INTO drone (serial_number, model, weight_limit, battery_capacity, state) " +
                    "VALUES (:serialNumber, :model, :weightLimit, :batteryCapacity, :state)";
    private static final String UPDATE_DRONE_STATUS = "UPDATE drone SET state = :state WHERE id in (:ids)";
    private static final String DRONE_EXISTS_BY_ID = "SELECT COUNT(*) FROM drone WHERE id= :id";
    private static final String DRONE_EXISTS_BY_SERIAL_NUMBER =
            "SELECT COUNT(*) FROM drone WHERE serial_number= :serialNumber";

    private static final String FIND_DRONE_BY_ID = "SELECT * FROM drone WHERE id= :id";
    private static final String FIND_DRONES_BY_BATTERY_LEVEL =
            "SELECT * FROM drone WHERE battery_capacity < :batteryCapacity";
    private static final String FIND_ALL_DRONES = "SELECT * FROM drone";
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
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("serialNumber", drone.getSerialNumber())
                .addValue("model", drone.getModel().name())
                .addValue("weightLimit", drone.getWeightLimit())
                .addValue("batteryCapacity", drone.getBatteryCapacity())
                .addValue("state", drone.getState().name());
        jdbcTemplate.update(CREATE_DRONE_SQL, parameters, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public int batchUpdateDroneStatus(List<Integer> droneIds, DroneStatus droneStatus) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("state", droneStatus)
                .addValue("ids", droneIds);
        return jdbcTemplate.update(UPDATE_DRONE_STATUS, parameters);
    }

    @Override
    public boolean droneExistsById(int droneId) {
        Integer count = jdbcTemplate.queryForObject(
                DRONE_EXISTS_BY_ID,
                new MapSqlParameterSource("id", droneId),
                Integer.class
        );
        return Objects.nonNull(count) && count == 1;
    }

    @Override
    public boolean droneExistsBySerialNumber(String serialNumber) {
        Integer count = jdbcTemplate.queryForObject(
                DRONE_EXISTS_BY_SERIAL_NUMBER,
                new MapSqlParameterSource("serialNumber", serialNumber),
                Integer.class
        );
        return Objects.nonNull(count) && count == 1;
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
    public List<Drone> getAllDrones() {
        return jdbcTemplate.query(FIND_ALL_DRONES, DRONE_ROW_MAPPER);
    }

    @Override
    public List<Drone> getDronesForStatus(List<DroneStatus> droneStatus) {
        return jdbcTemplate.query(
                FIND_DRONES_BY_STATE,
                new MapSqlParameterSource("states", droneStatus.stream().map(DroneStatus::name).toList()),
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
