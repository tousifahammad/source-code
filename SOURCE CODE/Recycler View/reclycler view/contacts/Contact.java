package com.app.guardian.contacts.view;

public class Contact {
    String relation;
    String name;
    String email;
    String phone_number;

    public Contact(String relation, String name, String email, String phone_number) {
        this.relation = relation;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
