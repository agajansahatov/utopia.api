package api.utopia.entities;

public class PurchasedProduct {
    private long id;
    private long user;
    private long product;

    private String destination;
    private long quantity;
    private String status;
    private String date;

    public PurchasedProduct() {
    }

    public PurchasedProduct(long id, long user, long product, String destination, long quantity, String status, String date) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.destination = destination;
        this.status = status;
        this.date = date;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getProduct() {
        return product;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
