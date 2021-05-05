
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

        Sql2oItemsDao itemDao;
        Sql2oStoreDao storeDao;
        Connection conn;
        Gson gson = new Gson();
        String connectionString = "jdbc:postgresql://localhost:5432/shopping";
        Sql2o sql2o = new Sql2o(connectionString, "postgres", "");

        storeDao = new Sql2oStoreDao(sql2o);
        itemDao = new Sql2oItemsDao(sql2o);
        conn = sql2o.open();

        //CREATE
        post("/stores/new", "application/json", (req, res) -> {
            Store store = gson.fromJson(req.body(), Store.class);
            storeDao.add(store);
            res.status(201);
            return gson.toJson(store);
        });
        post("/items/new", "application/json", (req, res) -> {
            Items item = gson.fromJson(req.body(), Items.class);
            itemDao.add(item);
            res.status(201);
            return gson.toJson(item);
        });

        post("/stores/:storeId/items/:itemId", "application/json", (req, res) -> {
            int storeId = Integer.parseInt(req.params("storeId"));
            int itemId = Integer.parseInt(req.params("itemId"));
            Store store = storeDao.findById(storeId);
            Items item = itemDao.findById(itemId);


            if (store != null && item != null){
                //both exist and can be associated
                itemDao.addItemToStore(item, store);
                res.status(201);
                return gson.toJson(String.format("Store '%s' and Item '%s' have been associated",item.getName(), store.getName()));
            }
            else {
                throw new ApiException(404, String.format("Store or Item does not exist"));
            }
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