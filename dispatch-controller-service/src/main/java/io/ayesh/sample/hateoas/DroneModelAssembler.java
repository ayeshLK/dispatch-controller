package io.ayesh.sample.hateoas;

import io.ayesh.sample.controller.DispatchController;
import io.ayesh.sample.model.Drone;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DroneModelAssembler extends RepresentationModelAssemblerSupport<Drone, DroneHateoasModel> {

    public DroneModelAssembler() {
        super(DispatchController.class, DroneHateoasModel.class);
    }

    @Override
    public DroneHateoasModel toModel(Drone entity) {
        DroneHateoasModel droneHateoasModel = instantiateModel(entity);
        droneHateoasModel.setId(entity.getId());
        droneHateoasModel.setModel(entity.getModel().name());
        droneHateoasModel.setBatteryCapacity(entity.getBatteryCapacity());
        droneHateoasModel.setSerialNumber(entity.getSerialNumber());
        droneHateoasModel.setState(entity.getState().name());
        droneHateoasModel.setWeightLimit(entity.getWeightLimit());
        droneHateoasModel.add(
                linkTo(methodOn(DispatchController.class).getDrone(entity.getId()))
                        .withSelfRel()
        );
        droneHateoasModel.add(
                linkTo(methodOn(DispatchController.class).getBatteryCapacity(entity.getId()))
                        .withRel("battery-capacity")
        );
        droneHateoasModel.add(
                linkTo(methodOn(DispatchController.class).getMedications(entity.getId()))
                        .withRel("medications")
        );
        return droneHateoasModel;
    }

    @Override
    public CollectionModel<DroneHateoasModel> toCollectionModel(Iterable<? extends Drone> entities) {
        CollectionModel<DroneHateoasModel> droneModels = super.toCollectionModel(entities);
        droneModels.add(
                linkTo(methodOn(DispatchController.class).getDronesAvailableForLoading())
                        .withSelfRel()
        );
        return droneModels;
    }
}
