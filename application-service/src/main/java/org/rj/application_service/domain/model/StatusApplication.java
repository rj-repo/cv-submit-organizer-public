package org.rj.application_service.domain.model;

public enum StatusApplication {
    APPLIED, IN_PROGRESS, REJECTED;

    public static StatusApplication getStatusApplication(String name) {
        validate(name);
        return StatusApplication.valueOf(name.toUpperCase());
    }

    private static void validate(String name) {
        for (StatusApplication statusApplication : StatusApplication.values()) {
            if (statusApplication.name().equalsIgnoreCase(name)) {
                return;
            }
        }
        throw new IllegalArgumentException("Invalid status application: " + name);
    }
}
