package se.kry.codetest.model;

import io.vertx.core.json.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ServiceMapper {


    public static JsonObject mapObject(Service service) {
        JsonObject mappedService = new JsonObject();

        mappedService.put("id", service.getId());
        mappedService.put("name", service.getName());
        mappedService.put("url", service.getUrl());
        mappedService.put("status", service.getStatus());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mappedService.put("created_at", formatter.format(service.getCreatedAt()));

        return mappedService;
    }

    public static Service mapJsonObject(JsonObject service) {

        Service mappedService = new Service();
        mappedService.setId(service.getInteger("id"));
        mappedService.setName(service.getString("name"));
        mappedService.setUrl(service.getString("url"));
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mappedService.setCreatedAt(formatter.parse(service.getString("created_at")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String status = service.getString("status");
        if (status != null && status.equals("OK")) {
            mappedService.setStatus(Status.OK);
        } else if (status != null && status.equals("KO")) {
            mappedService.setStatus(Status.KO);
        } else {
            mappedService.setStatus(Status.NOD);
        }

        return mappedService;
    }
}
