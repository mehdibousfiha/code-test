package se.kry.codetest;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import se.kry.codetest.model.Status;

import java.net.HttpURLConnection;
import java.net.URL;

public class BackgroundPoller extends AbstractVerticle {
    private ServiceHandler serviceHandler;
    private HttpURLConnection connection = null;

    @Override
    public void start(Future<Void> startFuture) {
        DBConnector connector = new DBConnector(vertx);
        this.serviceHandler = new ServiceHandler(new DBConnector(vertx));
        vertx.setPeriodic(5000, timerId -> this.pollServices());
    }

    public void pollServices() {
        this.serviceHandler.retrieveAll().setHandler(ar -> {
            ar.result().forEach(service -> {
                boolean check = this.checkHost(service.getUrl());
                this.serviceHandler.updateServiceStatus(service.getId(), check ? Status.OK : Status.KO);
            });
        });
    }

    private boolean checkHost(String host) {
        try {
            this.connection = (HttpURLConnection) new URL(host).openConnection();
            this.connection.setRequestMethod("HEAD");
            return this.connection.getResponseCode() == 200 |
                    this.connection.getResponseCode() == 301 |
                    this.connection.getResponseCode() == 503;
        } catch (Exception e) {
            return false;
        }
    }
}
