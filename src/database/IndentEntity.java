package database;

import java.util.Objects;

public class IndentEntity {
    private int indentId;
    private int createTime;

    public int getIndentId() {
        return indentId;
    }

    public void setIndentId(int indentId) {
        this.indentId = indentId;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndentEntity that = (IndentEntity) o;
        return createTime == that.createTime &&
                Objects.equals(indentId, that.indentId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(indentId, createTime);
    }
}
