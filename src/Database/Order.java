package Database;

public class Order extends Shoe {
    String orderDate;

    public Order(int shoeId, String colour, int shoeSize, int prize, String brand, String orderDate) {
        super(shoeId, colour, shoeSize, prize, brand);
        this.orderDate = orderDate;
    }

    public String getOrderDate() {
        return orderDate;
    }
}
