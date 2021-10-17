package com.hyf.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "brand")
@Data
public class Brand implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键维护策略
    private Integer id;


    private String name;
    private String image;
    private Character letter;
}
