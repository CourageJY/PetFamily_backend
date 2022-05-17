package com.pet.petManage.entity;

public class AddPetRequest {
    public String name;
    public String type;
    public Integer age;
    public Boolean sex;
    public String color;
    public String description;
    public String photo;
    public String area;
    public Integer price;

    public AddPetRequest(String name, String type, Integer age, Boolean sex, String color, String description, String photo, String area,Integer price) {
        this.name = name;
        this.type = type;
        this.age = age;
        this.sex = sex;
        this.color = color;
        this.description = description;
        this.photo = photo;
        this.area = area;
        this.price=price;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Integer getAge() {
        return age;
    }

    public Boolean getSex() {
        return sex;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }

    public String getArea() {
        return area;
    }

    public Integer getPrice() {
        return price;
    }
}
