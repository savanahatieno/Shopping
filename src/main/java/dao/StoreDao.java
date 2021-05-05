package dao;

import models.Items;
import models.Store;

import java.util.List;

public interface StoreDao {
    //create
    void add (Store store);
    void addStoreToItem(Store store, Items item);

    //read
    List<Store> getAll();
    Store findById(int id);
    List<Items> getAllItemsByStore(int storeId);

    //update
    void update(int id, String name, String address, String website);

    //delete
    void deleteById(int id);
    void clearAll();
}
