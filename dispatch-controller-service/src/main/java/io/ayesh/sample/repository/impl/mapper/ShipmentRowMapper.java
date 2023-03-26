package io.ayesh.sample.repository.impl.mapper;

import io.ayesh.sample.model.Shipment;
import io.ayesh.sample.model.ShipmentStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShipmentRowMapper implements RowMapper<Shipment> {

    @Override
    public Shipment mapRow(ResultSet result, int rowNum) throws SQLException {
        Shipment shipment = new Shipment();
        shipment.setId(result.getInt("id"));
        shipment.setDroneId(result.getInt("drone_id"));
        shipment.setStatus(ShipmentStatus.valueOf(result.getString("status")));
        return shipment;
    }
}
