package com.pet.petManage.entity;


public class BriefPetInfoReturn {
    public String petID;
    public String name;
    public String type;
    public int age;
    public String photo;
    public String status;
    public Integer price;

    public BriefPetInfoReturn(String name, String type, int age, String photo,String petID,String status,Integer price)
    {
        this.age=age;
        this.name=name;
        this.type=type;
        this.photo=photo;
        this.petID=petID;
        this.status=status;
        this.price=price;
    }
}
