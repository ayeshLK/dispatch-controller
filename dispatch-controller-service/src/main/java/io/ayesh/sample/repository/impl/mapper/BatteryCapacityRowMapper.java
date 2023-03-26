package io.ayesh.sample.repository.impl.mapper;

import io.ayesh.sample.model.BatteryCapacity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BatteryCapacityRowMapper implements RowMapper<BatteryCapacity> {
    @Override
    public BatteryCapacity mapRow(ResultSet result, int rowNum) throws SQLException {
        BatteryCapacity batteryCapacity = new BatteryCapacity();
        batteryCapacity.setDroneId(result.getInt("id"));
        batteryCapacity.setBatteryPercentage(result.getDouble("battery_capacity"));
        return batteryCapacity;
    }
}
