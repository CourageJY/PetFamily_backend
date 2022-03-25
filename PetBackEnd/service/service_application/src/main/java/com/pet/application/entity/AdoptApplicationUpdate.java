package com.pet.application.entity;


public class AdoptApplicationUpdate extends Update{
    public String economicCondition;

    public String reason;

    public String area;

    public String getEconomicCondition() {
        return economicCondition;
    }

    public void setEconomicCondition(String economicCondition) {
        this.economicCondition = economicCondition;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
