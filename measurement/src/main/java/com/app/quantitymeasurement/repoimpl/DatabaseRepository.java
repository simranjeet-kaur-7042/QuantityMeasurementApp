package com.app.quantitymeasurement.repoimpl;

import com.app.quantitymeasurement.database.ConnectionPool;
import com.app.quantitymeasurement.entity.Entity;
import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepository implements Repository {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseRepository.class);
    private final ConnectionPool connectionPool;

    public DatabaseRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void save(Entity entity) {
    	String sql = "INSERT INTO quantity_measurement (\"value\", unit, type) VALUES (?, ?, ?)";
        Connection conn = connectionPool.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Mapping: Entity fields are Strings, DB columns are DOUBLE/VARCHAR
            pstmt.setString(1, entity.getInput());     // value
            pstmt.setString(2, entity.getResult());    // unit
            pstmt.setString(3, entity.getOperation()); // type
            
            pstmt.executeUpdate();
            LOGGER.info("DB Success: Record saved for operation {}", entity.getOperation());
        } catch (SQLException e) {
            LOGGER.error("DB Error: Failed to save record - {}", e.getMessage());
            throw new DatabaseException("Save failed", e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    @Override
    public List<Entity> getAllMeasurements() {
        LOGGER.info("Querying database for all measurements...");
        List<Entity> list = new ArrayList<>();
        String sql = "SELECT \"value\", unit, type FROM quantity_measurement";
        Connection conn = connectionPool.getConnection();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Entity(
                    rs.getString("type"),   // operation
                    rs.getString("value"),  // input
                    rs.getString("unit")    // result
                ));
            }
            LOGGER.info("DB Success: Retrieved {} records", list.size());
            return list;
        } catch (SQLException e) {
            LOGGER.error("DB Error: Fetch failed - {}", e.getMessage());
            throw new DatabaseException("Fetch failed", e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM quantity_measurement";
        Connection conn = connectionPool.getConnection();
        try (Statement stmt = conn.createStatement()) {
            int deletedRows = stmt.executeUpdate(sql);
            LOGGER.warn("DB Cleanup: Deleted {} total rows from database.", deletedRows);
        } catch (SQLException e) {
            LOGGER.error("DB Error: Cleanup failed - {}", e.getMessage());
            throw new DatabaseException("Delete all failed", e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }
}