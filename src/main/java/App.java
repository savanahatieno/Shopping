
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


    }
}