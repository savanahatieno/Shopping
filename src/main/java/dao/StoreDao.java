package dao;

import models.Store;

import java.util.List;

public interface StoreDao {
    //create
    void add (Store store);

    //read
    List<Store> getAll();
    Store findById(int id);
//    List<Items> getAllItemsInStore(int storeId);

    //update
    void update(int id, String name, String address, String website);

    //delete
    void deleteById(int id);
    void clearAll();
}
