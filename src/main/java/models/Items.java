package models;

import java.util.Objects;

public class Items {
    private int id;
    private String name;
    private int price;
    private int storeId;

    public Items(String name, int price, int storeId) {
        this.name = name;
        this.price = price;
        this.storeId = storeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Items)) return false;
        Items items = (Items) o;
        return id == items.id &&
                storeId == items.storeId &&
                Objects.equals(name, items.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,id,storeId);
    }
}
