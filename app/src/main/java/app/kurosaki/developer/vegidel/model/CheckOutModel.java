package app.kurosaki.developer.vegidel.model;

import java.util.ArrayList;

public class CheckOutModel {

    ArrayList<CartData> cartData;
    float total;
    String Address;

    public CheckOutModel() {
    }

    public ArrayList<CartData> getCartData() {
        return cartData;
    }

    public void setCartData(ArrayList<CartData> cartData) {
        this.cartData = cartData;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
