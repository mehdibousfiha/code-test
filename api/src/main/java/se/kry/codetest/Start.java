package se.kry.codetest;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class Start {

    public static void main(String[] args) {
//        DeploymentOptions options = new DeploymentOptions().setInstances(2);
        Vertx.vertx().deployVerticle(new MainVerticle());
        Vertx.vertx().deployVerticle(new BackgroundPoller());
    }
}
