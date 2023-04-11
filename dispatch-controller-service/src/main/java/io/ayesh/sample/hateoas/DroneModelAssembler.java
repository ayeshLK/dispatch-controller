package io.ayesh.sample.hateoas;

import io.ayesh.sample.controller.DispatchController;
import io.ayesh.sample.model.Drone;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DroneModelAssembler extends RepresentationModelAssemblerSupport<Drone, DroneModel> {

    public DroneModelAssembler() {
        super(DispatchController.class, DroneModel.class);
    }

    @Override
    public DroneModel toModel(Drone entity) {
        DroneModel droneModel = instantiateModel(entity);
        droneModel.setId(entity.getId());
        droneModel.setModel(entity.getModel().name());
        droneModel.setBatteryCapacity(entity.getBatteryCapacity());
        droneModel.setSerialNumber(entity.getSerialNumber());
        droneModel.setState(entity.getState().name());
        droneModel.setWeightLimit(entity.getWeightLimit());
        droneModel.add(
                linkTo(methodOn(DispatchController.class).getDrone(entity.getId()))
                        .withSelfRel()
        );
        droneModel.add(
                linkTo(methodOn(DispatchController.class).getBatteryCapacity(entity.getId()))
                        .withRel("battery-capacity")
        );
        droneModel.add(
                linkTo(methodOn(DispatchController.class).getMedications(entity.getId()))
                        .withRel("medications")
        );
        return droneModel;
    }

    @Override
    public CollectionModel<DroneModel> toCollectionModel(Iterable<? extends Drone> entities) {
        CollectionModel<DroneModel> droneModels = super.toCollectionModel(entities);
        droneModels.add(
                linkTo(methodOn(DispatchController.class).getDronesAvailableForLoading())
                        .withSelfRel()
        );
        return droneModels;
    }
}
