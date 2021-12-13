package enums;

import lombok.Getter;

public enum CategoryType {
    FOOD(1, "Food");

    @Getter
    private int id;
    @Getter
    private String name;

    CategoryType(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
