package com.mycompany.inviter.customerInviter.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;

public class Customer {

    private String name;

    @JsonProperty("user_id")
    private Long id;

    @JsonUnwrapped
    private Location location;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equal(getName(), customer.getName()) &&
                Objects.equal(getId(), customer.getId()) &&
                Objects.equal(getLocation(), customer.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName(), getId(), getLocation());
    }

    @Override
    public String toString() {
        return "Customer {" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
