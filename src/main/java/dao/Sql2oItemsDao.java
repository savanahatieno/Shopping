package dao;

import models.Items;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oItemsDao implements ItemsDao {
    private final Sql2o sql2o;

    public Sql2oItemsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Items item) {
        String sql = "INSERT INTO items (name, storeid) VALUES(:name, :storeid)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(item)
                    .executeUpdate()
                    .getKey();
            item.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Items> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM items")
                    .executeAndFetch(Items.class);
        }
    }

    @Override
    public List<Items> getAllItemsByStore(int storeId) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM items WHERE storeid = :storeid")
                    .addParameter("storeId",storeId)
                    .executeAndFetch(Items.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from items WHERE id = :id";
        try(Connection con =sql2o.open()){
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from items";
        try (Connection con = sql2o.open()){
            con.createQuery(sql).executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}
