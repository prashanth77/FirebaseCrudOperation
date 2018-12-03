package sample.firebaseexpense.com;

public class DataModel {
    private String id;
    private String category;
    private String date;
    private String name;
    private String details;
    private int price;
    private String payment;

    public DataModel() {
    }

    public DataModel(String id, String category, String date, String name, String details, int price, String payment) {
        this.id = id;
        this.category = category;
        this.date = date;
        this.name = name;
        this.details = details;
        this.price = price;
        this.payment = payment;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public int getPrice() {
        return price;
    }

    public String getPayment() {
        return payment;
    }
}
