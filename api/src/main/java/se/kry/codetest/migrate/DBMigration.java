package se.kry.codetest.migrate;

import io.vertx.core.Vertx;
import se.kry.codetest.DBConnector;

public class DBMigration {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        new DBConnector(vertx)
                .query("CREATE TABLE IF NOT EXISTS service (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "url VARCHAR(128) NOT NULL, " +
                        "status VARCHAR(10), " +
                        "created_at DATETIME DEFAULT CURRENT_TIMESTAMP)")
                .setHandler(done -> {
                    if (done.succeeded()) {
                        System.out.println("completed db migrations");
                    } else {
                        done.cause().printStackTrace();
                    }
                    vertx.close(shutdown -> {
                        System.exit(0);
                    });
                });
    }
}
