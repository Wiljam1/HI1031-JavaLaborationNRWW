package bo;

import ui.ItemInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CartHandler {

    public static Collection<ItemInfo> addToCart(String itemId, String itemName, String itemDesc, String itemAmount, String itemPrice, String itemCategory, Collection<ItemInfo> cartItems){

        int amount = Integer.parseInt(itemAmount);

        if(cartItems == null) {
            cartItems = new ArrayList<>();
        }
        boolean itemExists = false;
        for(ItemInfo item : cartItems) {
            if(item.getName().equals(itemName)) {
                if(amount > item.getQuantity())
                    item.incrementQuantity();
                itemExists = true;
                break;
            }
        }

        if(!itemExists && amount > 0) {
            cartItems.add(new ItemInfo(itemId, itemName, itemDesc, "1", itemAmount, itemPrice, itemCategory));
        }
        return cartItems;
    }

    public static Collection<ItemInfo> removeFromCart(String itemIdToRemove, Collection<ItemInfo> cart){
        Iterator<ItemInfo> iterator = cart.iterator();
        while (iterator.hasNext()) {
            ItemInfo item = iterator.next();
            if (item.getId().equals(itemIdToRemove)) {
                if (item.getQuantity() > 1) {
                    item.decrementQuantity();
                } else {
                    iterator.remove();
                }
                break;
            }
        }
        return cart;
    }

    public static int calculatePrice(Collection<ItemInfo> cart) {
        int price = 0;
        for(ItemInfo item : cart) {
            price += item.getPrice() * item.getQuantity();
        }
        return price;
    }
}
