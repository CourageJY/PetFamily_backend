package com.pet.models;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LogisticId implements Serializable {
    private static final long serialVersionUID = -4065550890353250592L;
    @Column(name = "order_ID", nullable = false, length = 256)
    private String orderId;
    @Column(name = "current_location", nullable = false, length = 256)
    private String currentLocation;

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, currentLocation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LogisticId entity = (LogisticId) o;
        return Objects.equals(this.orderId, entity.orderId) &&
                Objects.equals(this.currentLocation, entity.currentLocation);
    }
}