package nl.oscar.dpi.donutrater.client.service;

import java.security.SecureRandom;
import java.util.Objects;

public class ClientIdGenerator {

    private static ClientIdGenerator instance;
    private final long id;

    public ClientIdGenerator() {
        id = new SecureRandom().nextLong();
    }

    public static long getId() {
        if (Objects.isNull(instance)) {
            instance = new ClientIdGenerator();
        }

        return instance.id;
    }
}
