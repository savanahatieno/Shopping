package dao;

import models.Items;
import models.Store;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oStoreDao implements StoreDao {
    private final Sql2o sql2o;

    public Sql2oStoreDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Store store) {
        String sql = "INSERT INTO stores (name, address, website) VALUES (:name, :address, :website)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(store)
                    .executeUpdate()
                    .getKey();
            store.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addStoreToItem(Store store, Items item) {
        String sql = "INSERT INTO stores_items (storeid, itemid) VALUES (:storeId, :itemId)";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("storeId",store.getId())
                    .addParameter("itemId", item.getId())
                    .executeUpdate();
        }catch(Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Store> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT *FROM stores")
                    .executeAndFetch(Store.class);
        }
    }

    @Override
    public Store findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM stores WHERE id =:id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Store.class);
        }
    }

    @Override
    public List<Items> getAllItemsByStore(int storeId) {
        ArrayList<Items> items = new ArrayList<>();

        String joinQuery = "SELECT itemid FROM stores_items WHERE storeid = :storeId";

        try(Connection con = sql2o.open()) {
            List<Integer> allItemsIds = con.createQuery(joinQuery)
                    .addParameter("storeId", storeId)
                    .executeAndFetch(Integer.class);
            for(Integer itemId : allItemsIds) {
                String itemQuery = "SELECT * FROM items WHERE id = :itemId";
                items.add(
                        con.createQuery(itemQuery)
                        .addParameter("itemId",itemId)
                        .executeAndFetchFirst(Items.class));
            }
        }catch(Sql2oException ex){
            System.out.println(ex);
        }
        return items;
    }

    @Override
    public void update(int id, String name, String address, String website) {
        String sql = "UPDATE squads SET (name, address, website) = (:name, :address, :website) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("address", address)
                    .addParameter("website", website)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from stores WHERE id = :id";
        String deleteJoin = "DELETE from stores_items WHERE storeid = :storeId";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("storeId",id)
                    .executeUpdate();
        }catch(Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from stores";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
