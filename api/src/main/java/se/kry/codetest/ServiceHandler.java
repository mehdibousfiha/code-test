package se.kry.codetest;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import se.kry.codetest.model.Service;
import se.kry.codetest.model.ServiceMapper;
import se.kry.codetest.model.Status;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceHandler {
    private final DBConnector connector;
    private ServiceMapper serviceMapper;

    public ServiceHandler(DBConnector connector) {
        this.connector = connector;
    }

    public Future<List<Service>> retrieveAll() {
        Future<List<Service>> resultFuture = Future.future();
        this.connector
                .query("SELECT * FROM service")
                .setHandler(ar -> {
                    if (ar.succeeded() && ar.result() != null) {
                        resultFuture.complete(ar
                                .result()
                                .getRows()
                                .stream()
                                .map(ServiceMapper::mapJsonObject)
                                .collect(Collectors.toList())
                        );
                    } else {
                        resultFuture.fail("ERROR WHILE RETRIEVING SERVICES");
                    }
                });
        return resultFuture;
    }

    public Future<Service> retrieve(Integer serviceId) {
        Future<Service> resultFuture = Future.future();
        this.connector
                .query("SELECT * FROM service WHERE id = ?", new JsonArray().add(serviceId))
                .setHandler(ar -> {
                    if (ar.succeeded() && !ar.result().getResults().isEmpty()) {
                        resultFuture.complete(
                                ServiceMapper.mapJsonObject(ar.result()
                                        .getRows()
                                        .get(0)
                                )
                        );
                    } else {
                        resultFuture.fail(String.format("Service with id (%d) not found", serviceId));
                    }
                });
        return resultFuture;
    }

    public Future<Boolean> updateServiceStatus(Integer serviceId, Status status) {
        Future<Boolean> resultFuture = Future.future();
        this.connector
                .update("UPDATE service SET status = ? WHERE id = ?",
                        new JsonArray()
                                .add(status)
                                .add(serviceId))
                .setHandler(ar -> {
                    if (ar.succeeded() && ar.result() != null && ar.result().getUpdated() != 0) {
                        resultFuture.complete();
                    } else {
                        resultFuture.fail(String.format("Service with id (%d) not found", serviceId));
                    }
                });
        return resultFuture;
    }

    public Future<Service> updateService(Integer serviceId, JsonObject service) {
        Future<Service> resultFuture = Future.future();
        this.connector
                .update("UPDATE service SET name = ?, url = ? WHERE id = ?",
                        new JsonArray()
                                .add(service.getString("name"))
                                .add(service.getString("url"))
                                .add(serviceId))
                .setHandler(ar -> {
                    if (ar.succeeded() && ar.result() != null && ar.result().getUpdated() != 0) {
                        this.retrieve(serviceId)
                                .setHandler(ar2 -> {
                                    resultFuture.complete(ar2.result());
                                });
                    } else {
                        resultFuture.fail(String.format("Service with id (%d) not found", serviceId));
                    }
                });
        return resultFuture;
    }

    public Future<Service> createService(JsonObject service) {
        Future<Service> resultFuture = Future.future();
        this.connector
                .update("INSERT INTO service (name, url, status) VALUES (?, ?, null)",
                        new JsonArray()
                                .add(service.getString("name"))
                                .add(service.getString("url")))
                .setHandler(ar -> {
                    if (ar.succeeded() && ar.result() != null && ar.result().getUpdated() != 0) {
                        this.retrieve(ar.result().getKeys().getInteger(0))
                                .setHandler(ar2 -> {
                                    resultFuture.complete(ar2.result());
                                });
                    } else {
                        resultFuture.fail("SERVICE MALFORMED");
                    }
                });
        return resultFuture;
    }

    public Future<Integer> deleteService(Integer serviceId) {
        Future<Integer> resultFuture = Future.future();
        this.connector
                .update("DELETE FROM service where id = ?", new JsonArray().add(serviceId))
                .setHandler(ar -> {
                    if (ar.succeeded() && ar.result() != null && ar.result().getUpdated() != 0) {
                        resultFuture.complete(serviceId);
                    } else {
                        resultFuture.fail(String.format("Service with id (%d) not found", serviceId));
                    }
                });
        return resultFuture;
    }

}
