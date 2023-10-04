package ui;

import java.util.Collection;

public class OrderInfo {
    private String id;
    private String date;
    private Collection<ItemInfo> items;
    private String totalCost;
    private String assignedStaff;

    public OrderInfo(String id, String date, Collection<ItemInfo> items, String totalCost, String assignedStaff) {
        this.id = id;
        this.date = date;
        this.items = items;
        this.totalCost = totalCost;
        this.assignedStaff = assignedStaff;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Collection<ItemInfo> getItems() {
        return items;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public String getAssignedStaff() {
        return assignedStaff;
    }
}
