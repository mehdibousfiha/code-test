package se.kry.codetest;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import se.kry.codetest.model.ServiceMapper;

public class MainVerticle extends AbstractVerticle {

    private ServiceHandler serviceHandler;
    private ServiceMapper serviceMapper;


    @Override
    public void start(Future<Void> startFuture) {
        this.serviceHandler = new ServiceHandler(new DBConnector(vertx));
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        setRoutes(router);
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080, result -> {
                    if (result.succeeded()) {
                        System.out.println("KRY code test service started");
                        startFuture.complete();
                    } else {
                        startFuture.fail(result.cause());
                    }
                });
    }

    private void setRoutes(Router router) {
        router.route("/*").handler(StaticHandler.create());

        router.get("/api/service").handler(routingCtx -> {
            serviceHandler
                    .retrieveAll()
                    .setHandler(ar -> {
                        HttpServerResponse resp = routingCtx.response().putHeader("content-type", "application/json");
                        if (ar.succeeded()) {
                            routingCtx.response()
                                    .putHeader("content-type", "application/json")
                                    .end(new JsonArray(ar.result()).encode());
                        } else {
                            ar.cause().printStackTrace();
                            resp.setStatusCode(500).end(ar.cause().getMessage());
                        }
                    });


        });
        router.put("/api/service/:serviceId").handler(routingCtx -> {
            JsonObject jsonBody = routingCtx.getBodyAsJson();
            serviceHandler
                    .updateService(
                            Integer.parseInt(routingCtx.pathParam("serviceId")),
                            jsonBody)
                    .setHandler(ar -> {
                        HttpServerResponse resp = routingCtx.response().putHeader("content-type", "application/json");
                        if (ar.succeeded()) {
                            resp.end(ServiceMapper.mapObject(ar.result()).encode());
                        } else {
                            ar.cause().printStackTrace();
                            resp.setStatusCode(404).end(ar.cause().getMessage());
                        }
                    });
        });
        router.post("/api/service").handler(req -> {
            JsonObject jsonBody = req.getBodyAsJson();
            serviceHandler
                    .createService(jsonBody)
                    .setHandler(ar -> {
                        HttpServerResponse resp = req.response().putHeader("content-type", "application/json");
                        if (ar.succeeded()) {
                            resp.end(ServiceMapper.mapObject(ar.result()).encode());
                        } else {
                            ar.cause().printStackTrace();
                            resp.setStatusCode(400).end(ar.cause().getMessage());
                        }
                    });
        });
        router.delete("/api/service/:serviceId").handler(routingCtx -> {
            serviceHandler
                    .deleteService(Integer.parseInt(routingCtx.pathParam("serviceId")))
                    .setHandler(ar -> {
                        HttpServerResponse resp = routingCtx.response().putHeader("content-type", "application/json");
                        if (ar.succeeded()) {
                            resp.end(new JsonObject().put("deleted", ar.result()).encode());
                        } else {
                            ar.cause().printStackTrace();
                            resp.setStatusCode(404).end(ar.cause().getMessage());
                        }
                    });
        });

    }

}



