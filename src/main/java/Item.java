public class Item {
    private String serialNumber;
    private ItemType type;
    private String quality;
    private double price;

    public Item(String serialNumber, ItemType type, String quality, double price) {
        this.serialNumber = serialNumber;
        this.quality = quality;
        this.price = price;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Item{" +
                "serialNumber='" + serialNumber + '\'' +
                ", type=" + type +
                ", quality='" + quality + '\'' +
                ", price=" + price +
                '}';
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public ItemType getType() {
        return this.type;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
