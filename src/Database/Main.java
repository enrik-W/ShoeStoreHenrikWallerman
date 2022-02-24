package Database;

import java.util.List;
import java.util.Scanner;

public class Main {
    private Scanner sc = new Scanner(System.in);
    private Repository r = new Repository();
    private Customer currentCustomer;
    private Shoe shoe;
    private List<Shoe> shoesInStock;
    private List<Order> shoesInOrders;

    public Main() {
        while (true) {
            System.out.print("Enter your first name: ");
            String firstName = sc.nextLine().trim();
            System.out.print("Enter your last name: ");
            String lastName = sc.nextLine().trim();
            System.out.print("Enter your password: ");
            String password = sc.nextLine().trim();
            currentCustomer = r.findIdOfCustomer(firstName, lastName, password);

            if (currentCustomer == null) {
                System.out.println("Invalid customer details. Try again");
            } else {
                selections();
            }
        }
    }

    public void selections() {
        String response;
        while (true) {
            System.out.println("What would you like to do?\n (1)View products in stock \n (2)View my orders");
            response = sc.nextLine().trim();

            if (response.equals("1")) {
                shoesInStock = r.getAllShoesInStock();
                printProductsInStock();
            } else if (response.equals("2")) {
                shoesInOrders = r.getAllShoesInCustomersOrder(currentCustomer.getCustomerId());
                printOrders();
            } else {
                System.out.println("Invalid input, try again.");
            }
        }
    }

    public void printProductsInStock() {
        for (Shoe value : shoesInStock) {
            System.out.println("Colour: " + value.getColour() +
                    " Shoe size: " + value.getShoeSize() + " Prize: "
                    + value.getPrize() + " kr" + " Brand: " + value.getBrand());
        }
        selectShoeAndAddToOrder();
    }

    public void selectShoeAndAddToOrder() {
        while (true) {
            System.out.println("Fill in the properties of the shoe you would like to add to your order.");
            System.out.print("Colour: ");
            String color = sc.nextLine().trim();
            System.out.print("Shoe size: ");
            int shoeSize = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Prize: ");
            int prize = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Brand: ");
            String brand = sc.nextLine().trim();
            shoe = r.findIdOfShoe(color, shoeSize, prize, brand);

            if (shoe == null) {
                System.out.println("Shoe could not be found. Try again!");
            } else {
                addShoeToCart();
            }
        }
    }

    public void addShoeToCart() {
        System.out.println(r.addToCart(currentCustomer.getCustomerId(), null, shoe.getShoeId()));
        selections();
    }

    public void printOrders() {
        for (Order value : shoesInOrders) {
            System.out.println("Date of order: " + value.getOrderDate() + " Colour: " + value.getColour() +
                    " Shoe size: " + value.getShoeSize() + " Prize: "
                    + value.getPrize() + " kr" + " Brand: " + value.getBrand());
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}
