package io.ayesh.sample.log.audit;

import io.ayesh.sample.model.Drone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditLog {
    private static final Logger AUDIT_LOGGER = LoggerFactory.getLogger("audit.log");

    public static void log(Drone context) {
        AUDIT_LOGGER.info("{}|{}|{}",context.getId(), context.getSerialNumber(), context.getBatteryCapacity());
    }
}
