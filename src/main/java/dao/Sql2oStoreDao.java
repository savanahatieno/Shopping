package dao;

import models.Store;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

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
        try(Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
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
