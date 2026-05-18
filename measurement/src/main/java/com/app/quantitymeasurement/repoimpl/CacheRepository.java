package com.app.quantitymeasurement.repoimpl;

import com.app.quantitymeasurement.entity.Entity;
import com.app.quantitymeasurement.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class CacheRepository implements Repository {
    // Logger for UC16
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheRepository.class);
    
    // Purana Singleton Logic
    private static CacheRepository instance;
    private final List<Entity> cache = new ArrayList<>();

    private CacheRepository() { }

    public static CacheRepository getInstance() {
        if (instance == null) {
            instance = new CacheRepository();
        }
        return instance;
    }

    @Override
    public void save(Entity entity) {
        cache.add(entity);
        LOGGER.info("Cache: Data saved for operation {}", entity.getOperation());
    }

    // UC16: Naya method implementation
    @Override
    public List<Entity> getAllMeasurements() {
        LOGGER.debug("Cache: Fetching all measurements. Total count: {}", cache.size());
        return new ArrayList<>(cache);
    }

    // UC16: Cleanup method
    @Override
    public void deleteAll() {
        cache.clear();
        LOGGER.warn("Cache: All records cleared.");
    }
}