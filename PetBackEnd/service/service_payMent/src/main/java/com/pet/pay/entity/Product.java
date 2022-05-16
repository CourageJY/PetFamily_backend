package com.pet.pay.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.pet.models.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("Pet")
public class Product extends BaseEntity{

    private String id;

    private String name;

    private String type;

    private LocalDate time;

    private User adoptUser;

    private LocalDate adoptTime;

    private Integer age;

    private Boolean sex;

    private String color;

    private String description;

    private String adoptState;

    private String photo;

    private String area;

    private Integer price;
}
