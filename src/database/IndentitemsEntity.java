package database;

import java.util.Objects;

public class IndentitemsEntity {
    private int bookId;
    private String username;
    private int indentId;
    private int amount;
    private int pk;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIndentId() {
        return indentId;
    }

    public void setIndentId(int indentId) {
        this.indentId = indentId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndentitemsEntity that = (IndentitemsEntity) o;
        return bookId == that.bookId &&
                indentId == that.indentId &&
                amount == that.amount &&
                pk == that.pk &&
                Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {

        return Objects.hash(bookId, username, indentId, amount, pk);
    }
}
