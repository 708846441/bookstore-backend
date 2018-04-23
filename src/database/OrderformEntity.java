package database;

public class OrderformEntity {
    private String orderId;
    private int amount;
    private CustomerEntity customerByUsername;
    private BookEntity bookByBookId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderformEntity that = (OrderformEntity) o;

        if (orderId != that.orderId) return false;
        if (amount != that.amount) return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 31 * result + amount;
        return result;
    }

    public CustomerEntity getCustomerByUsername() {
        return customerByUsername;
    }

    public void setCustomerByUsername(CustomerEntity customerByUsername) {
        this.customerByUsername = customerByUsername;
    }

    public BookEntity getBookByBookId() {
        return bookByBookId;
    }

    public void setBookByBookId(BookEntity bookByBookId) {
        this.bookByBookId = bookByBookId;
    }
}
