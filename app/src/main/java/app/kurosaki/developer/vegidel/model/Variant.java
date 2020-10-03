package app.kurosaki.developer.vegidel.model;

import java.io.Serializable;

public class Variant implements Serializable {
    String name;
    String price;

    public Variant(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Variant{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
