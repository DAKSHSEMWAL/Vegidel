package app.kurosaki.developer.vegidel.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductData implements Serializable {
    String image;
    String name;
    String price;
    ArrayList<Variant> variants;

    public ProductData(String image, String name, String price, ArrayList<Variant> variants) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.variants = variants;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public ArrayList<Variant> getVariants() {
        return variants;
    }

    public void setVariants(ArrayList<Variant> variants) {
        this.variants = variants;
    }

    @Override
    public String toString() {
        return "ProductData{" +
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", variants=" + variants +
                '}';
    }
}

