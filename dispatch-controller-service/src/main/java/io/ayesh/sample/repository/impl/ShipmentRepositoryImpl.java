package io.ayesh.sample.repository.impl;

import io.ayesh.sample.model.Shipment;
import io.ayesh.sample.model.ShipmentStatus;
import io.ayesh.sample.repository.ShipmentRepository;
import io.ayesh.sample.repository.impl.mapper.ShipmentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ShipmentRepositoryImpl implements ShipmentRepository {
    private static final String CREATE_SHIPMENT = "INSERT INTO shipment (drone_id, status) VALUES (:droneId, :status)";
    private static final String GET_CURRENT_SHIPMENT = 
            "SELECT * FROM shipment WHERE drone_id = :droneId AND status = 'IN_PROGRESS'";
    private static final RowMapper<Shipment> SHIPMENT_ROW_MAPPER = new ShipmentRowMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ShipmentRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createNewShipment(int droneId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("droneId", droneId)
                .addValue("status", ShipmentStatus.IN_PROGRESS);
        return jdbcTemplate.update(CREATE_SHIPMENT, parameters);
    }

    @Override
    public Optional<Shipment> getCurrentShipment(int droneId) {
        try {
            Shipment shipment = jdbcTemplate.queryForObject(
                    GET_CURRENT_SHIPMENT,
                    new MapSqlParameterSource("droneId", droneId),
                    SHIPMENT_ROW_MAPPER
            );
            return Optional.ofNullable(shipment);
        } catch (EmptyResultDataAccessException emptyResultException) {
            return Optional.empty();
        }
    }
}
