package fys.fysserver.api.dtos.announcements;

import fys.fysmodel.Field;

public class FieldDto {
    private Integer id;
    private String name;

    public FieldDto(Field field) {
        this.id = field.getId();
        this.name = field.getName();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static FieldDto build(Field field) {
        return new FieldDto(field);
    }
}
