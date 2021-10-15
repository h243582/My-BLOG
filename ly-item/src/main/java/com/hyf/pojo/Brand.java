package com.hyf.pojo;

import javax.persistence.*;

@Entity
@Table
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键维护策略
    private Integer id;
    private String name;
    private String image;
    private Character letter;

    public Brand() {
    }

    public Brand(Integer id, String name, String image, Character letter) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.letter = letter;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", letter=" + letter +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }
}
