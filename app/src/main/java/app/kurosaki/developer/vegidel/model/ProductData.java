package app.kurosaki.developer.vegidel.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductData implements Serializable {
    String image;
    String name;
    String price;
    float totalQuantity;
    float selectedQuantity;
    int type;
    ArrayList<Variant> variants;

    public ProductData(String image, String name, String price, float totalQuantity, float selectedQuantity, int type, ArrayList<Variant> variants) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.totalQuantity = totalQuantity;
        this.selectedQuantity = selectedQuantity;
        this.type = type;
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

    public float getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(float totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public float getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(float selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
                ", totalQuantity=" + totalQuantity +
                ", selectedQuantity=" + selectedQuantity +
                ", type=" + type +
                ", variants=" + variants +
                '}';
    }
}

