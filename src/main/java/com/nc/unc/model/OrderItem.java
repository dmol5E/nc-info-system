package com.nc.unc.model;


import com.nc.unc.dao.annotation.Attribute;
import com.nc.unc.dao.annotation.PrimaryKey;
import com.nc.unc.dao.annotation.Table;
import lombok.*;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(value = "order_item", schema = "store")
public class OrderItem {

    @PrimaryKey("id")
    private int id;

    @Attribute("name")
    private String name;

    @Attribute("price")
    private float price;

    @Attribute("count")
    private int count;

    @Attribute("order_id")
    private int orderId;

    @Attribute("product_history_id")
    private int productHistoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Float.compare(orderItem.price, price) == 0 &&
                count == orderItem.count &&
                Objects.equals(name, orderItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, count);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
