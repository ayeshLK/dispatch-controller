package io.ayesh.sample.repository.impl.mapper;

import io.ayesh.sample.model.Drone;
import io.ayesh.sample.model.DroneModel;
import io.ayesh.sample.model.DroneStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DroneRowMapper implements RowMapper<Drone> {

    @Override
    public Drone mapRow(ResultSet result, int rowNum) throws SQLException {
        Drone drone = new Drone();
        drone.setId(result.getInt("id"));
        drone.setSerialNumber(result.getString("serial_number"));
        drone.setModel(DroneModel.valueOf(result.getString("model")));
        drone.setWeightLimit(result.getDouble("weight_limit"));
        drone.setBatteryCapacity(result.getDouble("battery_capacity"));
        drone.setState(DroneStatus.valueOf(result.getString("state")));
        return drone;
    }
}
