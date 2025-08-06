// UserModel.java

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserModel {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;

    // Other fields and methods...

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Additional methods and annotations as needed.
}