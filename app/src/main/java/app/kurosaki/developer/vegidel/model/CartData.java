package app.kurosaki.developer.vegidel.model;

public class CartData {
    ProductData productData;
    float quantity;
    int pos;

    public CartData(ProductData productData, float quantity, int pos) {
        this.productData = productData;
        this.quantity = quantity;
        this.pos = pos;
    }

    public ProductData getProductData() {
        return productData;
    }

    public void setProductData(ProductData productData) {
        this.productData = productData;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "CartData{" +
                "productData=" + productData +
                ", quantity=" + quantity +
                ", pos=" + pos +
                '}';
    }
}
