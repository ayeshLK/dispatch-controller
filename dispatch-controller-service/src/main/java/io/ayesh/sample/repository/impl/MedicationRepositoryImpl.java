package io.ayesh.sample.repository.impl;

import io.ayesh.sample.model.Medication;
import io.ayesh.sample.repository.MedicationRepository;
import io.ayesh.sample.repository.impl.mapper.MedicationRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicationRepositoryImpl implements MedicationRepository {
    private static final String CREATE_MEDICATIONS = "INSERT INTO medication (shipment_id, code, name, weight, image) " +
            "VALUES (:shipmentId, :code, :name, :weight, :image)";
    private static final String FIND_MEDICATIONS_FOR_SHIPMENT =
            "SELECT * FROM medication WHERE shipment_id = :shipmentId";
    private static final RowMapper<Medication> MEDICATION_ROW_MAPPER = new MedicationRowMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public MedicationRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int[] createMedications(List<Medication> medications) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(medications);
        return jdbcTemplate.batchUpdate(CREATE_MEDICATIONS, batch);
    }

    @Override
    public List<Medication> getLoadedMedicationItems(int shipmentId) {
        return jdbcTemplate.query(
                FIND_MEDICATIONS_FOR_SHIPMENT,
                new MapSqlParameterSource("shipmentId", shipmentId),
                MEDICATION_ROW_MAPPER
        );
    }
}
