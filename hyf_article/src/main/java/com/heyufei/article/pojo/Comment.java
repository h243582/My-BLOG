package com.heyufei.article.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 文章评论（mongoDB）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Comment implements Serializable {
    @Id
    private String _id;

    private String articleid;
    private String content;
    private String userid;
    private String parentid;
    private Date publishdate;



}