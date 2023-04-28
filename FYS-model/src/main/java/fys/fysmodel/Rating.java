package fys.fysmodel;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="ratings")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Rating extends Identifiable<Integer> {
    private Integer score;
    private String comment;

    public Rating() {}

    public Rating(Integer score, String comment) {
        this.score = score;
        this.comment = comment;
    }

    public Rating(Integer integer, Integer score, String comment) {
        super(integer);
        this.score = score;
        this.comment = comment;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
