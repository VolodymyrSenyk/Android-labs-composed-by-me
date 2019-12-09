package com.senyk.volodymyr.lab5;

public class ContactPojo implements Comparable<ContactPojo> {
    private final String name;
    private final String phoneNumber;

    public ContactPojo(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return this.name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    @Override
    public int compareTo(ContactPojo o) {
        return this.name.compareTo(o.name);
    }
}
