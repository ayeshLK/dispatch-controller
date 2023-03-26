package io.ayesh.sample.repository.impl.mapper;

import io.ayesh.sample.model.Medication;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicationRowMapper implements RowMapper<Medication> {

    @Override
    public Medication mapRow(ResultSet rs, int rowNum) throws SQLException {
        Medication medication = new Medication();
        medication.setId(rs.getInt("id"));
        medication.setName(rs.getString("name"));
        medication.setCode(rs.getString("code"));
        medication.setShipmentId(rs.getInt("shipment_id"));
        medication.setImage(rs.getString("image"));
        return medication;
    }
}
