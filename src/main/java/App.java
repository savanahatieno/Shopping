import com.google.gson.Gson;
import dao.Sql2oItemsDao;
import dao.Sql2oStoreDao;
import exceptions.ApiException;
import models.Items;
import models.Store;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        Sql2oStoreDao storeDao;
        Sql2oItemsDao itemsDao;
        Connection conn;
        Gson gson = new Gson();

        String connectString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:DB/create.sql'";
        Sql2o sql2o = new Sql2o(connectString, "", "");

        storeDao = new Sql2oStoreDao(sql2o);
        itemsDao = new Sql2oItemsDao(sql2o);
        conn = sql2o.open();

        //create
        post("/items/new", "application/json", (request, response) -> {
            Items item = gson.fromJson(request.body(), Items.class);
            itemsDao.add(item);
            response.status(201);
            return gson.toJson(item);
        });

        post("stores/new", "application.json", (request, response) -> {
            Store store = gson.fromJson(request.body(), Store.class);
            storeDao.add(store);
            response.status(201);
            return gson.toJson(store);
        });

        post("/stores/:storeId/item/:itemId", "application/json", (request, response) -> {
            int storeId = Integer.parseInt(request.params("storeId"));
            int itemId = Integer.parseInt(request.params("itemId"));
            Store store = storeDao.findById(storeId);
            Items item = itemsDao.findById(itemId);

            if (store != null && item != null) {
                itemsDao.addItemToStore(item, store);
                response.status(201);
                return gson.toJson(String.format("'%s' has been added to '%s'.", item.getName(), store.getName()));
            } else {
                throw new ApiException(404, String.format("Item or Store does not exist"));
            }
        });

        //read
        get("/store/:id/items", "application/json", (request, response) -> {
            int storeId = Integer.parseInt(request.params("id"));
            Store storeToFind = storeDao.findById(storeId);

            if (storeToFind == null) {
                throw new ApiException(404, String.format("No store with the id: \"%s\" exists.", request.params("id")));
            } else if (storeDao.getAllItemsByStore(storeId).size() == 0) {
                return "{\"message\":\"I'm sorry, but no items are listed for this store.\"}";
            } else {
                return gson.toJson(storeDao.getAllItemsByStore(storeId));
            }
        });

        get("/items/:id/stores", "application/json", (request, response) -> {
            int itemId = Integer.parseInt(request.params("id"));
            Items itemToFind = itemsDao.findById(itemId);
            if (itemToFind == null) {
                throw new ApiException(404, String.format("No item with the id: \"%s\" exists", request.params("id")));
            } else if (itemsDao.getAllStoresForItem(itemId).size() == 0) {
                return "{\"message\":\"I'm sorry, but no stores appear to have this item.\"}";
            } else {
                return gson.toJson(itemsDao.getAllStoresForItem(itemId));
            }
        });

        get("/stores", "application.json", (request, response) -> {
            System.out.println(storeDao.getAll());

            if (storeDao.getAll().size() > 0) {
                return gson.toJson(storeDao.getAll());
            } else {
                return "{\"message\":\"I'm sorry, but no stores are currently listed in the database.\"}";
            }
        });

        get("/items", "application.json", (request, response) -> {
            return gson.toJson(itemsDao.getAll());
        });

        //update
        get("/stores/:id", "application.json", (request, response) -> {
            int storeId = Integer.parseInt(request.params("id"));
            return gson.toJson(storeDao.findById(storeId));
        });

        //filter
        after((request, response) -> {
            response.type("application/json");
        });

        exception(ApiException.class, (exception, req, res) -> {
            ApiException err = exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });

    }
}
