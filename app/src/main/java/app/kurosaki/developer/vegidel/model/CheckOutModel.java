package app.kurosaki.developer.vegidel.model;

import java.util.ArrayList;

public class CheckOutModel {

    ArrayList<CartData> cartData;
    int total;
    String Address;

    public CheckOutModel() {
    }

    public ArrayList<CartData> getCartData() {
        return cartData;
    }

    public void setCartData(ArrayList<CartData> cartData) {
        this.cartData = cartData;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
