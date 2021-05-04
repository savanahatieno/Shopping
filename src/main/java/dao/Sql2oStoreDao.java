package dao;

import models.Store;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oStoreDao implements StoreDao{
    private final Sql2o sql2o;

    public Sql2oStoreDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Store store) {

    }

    @Override
    public List<Store> getAll() {
        return null;
    }

    @Override
    public Store findById(int id) {
        return null;
    }

    @Override
    public void update(int id, String name, String address, String website) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void clearAll() {

    }
}
