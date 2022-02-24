package Database;

public class Shoe {
    private int shoeId;
    private String colour;
    private int shoeSize;
    private int prize;
    private String brand;

    public Shoe(int shoeId, String colour, int shoeSize, int prize, String brand) {
        this.shoeId = shoeId;
        this.colour = colour;
        this.shoeSize = shoeSize;
        this.prize = prize;
        this.brand = brand;
    }

    public int getShoeId() {
        return shoeId;
    }

    public String getColour() {
        return colour;
    }

    public int getShoeSize() {
        return shoeSize;
    }

    public int getPrize() {
        return prize;
    }

    public String getBrand() {
        return brand;
    }
}
