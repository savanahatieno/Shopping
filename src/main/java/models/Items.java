package models;

import java.util.Objects;

public class Items {
    private int id;
    private String name;
    private int price;

    public Items(String name, int price) {
        this.name = name;
        this.price = price;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Items)) return false;
        Items items = (Items) o;
        return id == items.id &&
                price == items.price &&
                Objects.equals(name, items.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,id);
    }
}

