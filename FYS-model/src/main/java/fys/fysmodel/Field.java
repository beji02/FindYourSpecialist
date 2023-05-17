package fys.fysmodel;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="fields")
@Inheritance(strategy = InheritanceType.JOINED)
public class Field extends Identifiable<Integer> {
    private String name;
    private Float taxPercentage;

    public Field() {}

    public Field(String name, Float taxPercentage) {
        this.name = name;
        this.taxPercentage = taxPercentage;
    }

    public Field(Integer integer, String name, Float taxPercentage) {
        super(integer);
        this.name = name;
        this.taxPercentage = taxPercentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(Float taxPercentage) {
        this.taxPercentage = taxPercentage;
    }
}
