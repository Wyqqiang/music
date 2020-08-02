package com.wyq.music.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 留言的实体类
 */
@Entity
public class Message implements Serializable {
    /**
     * 自增的id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     *留言的信息
     */
    @Column
    private String message;
    /**
     *留言的日期
     */
    @Column
    private Date date;

    public Message() {
    }

    public Message(String message, Date date) {
        this.message = message;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
