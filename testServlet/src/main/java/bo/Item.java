package bo;

import db.ItemDB;

import java.util.Collection;

public class Item {
    private String id;
    private String name;
    private String desc;

    public static Collection searchItems(String group) {
        return ItemDB.searchItems(group);
    }

    protected Item(String id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
