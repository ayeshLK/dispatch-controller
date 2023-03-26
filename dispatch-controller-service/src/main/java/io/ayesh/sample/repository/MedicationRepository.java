package io.ayesh.sample.repository;

import io.ayesh.sample.model.Medication;

import java.util.List;

public interface MedicationRepository {
    void insertMedications(List<Medication> medications);

    List<Medication> getLoadedMedicationItems(int shipmentId);
}
