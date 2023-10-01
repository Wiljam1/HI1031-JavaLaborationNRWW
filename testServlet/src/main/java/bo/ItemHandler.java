package bo;


import ui.ItemInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ItemHandler {

    public static Collection<ItemInfo> getItemsWithGroup(String s) {
        Collection c = Item.searchItems(s);
        ArrayList<ItemInfo> items = new ArrayList<ItemInfo>();
        for (Object o : c) {
            Item item = (Item) o;
            items.add(new ItemInfo(item.getDesc()));
        }
        return items;
    }
}
