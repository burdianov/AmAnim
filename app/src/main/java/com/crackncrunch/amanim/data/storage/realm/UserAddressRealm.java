package com.crackncrunch.amanim.data.storage.realm;

import com.crackncrunch.amanim.data.storage.dto.UserAddressDto;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserAddressRealm extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;
    private String street;
    private String house;
    private String apartment;
    private int floor;
    private String comment;
    private boolean favorite;

    public UserAddressRealm() {
    }

    public UserAddressRealm(UserAddressDto userAddressDto) {
        this.id = userAddressDto.getId();
        this.name = userAddressDto.getName();
        this.street = userAddressDto.getStreet();
        this.house = userAddressDto.getHouse();
        this.apartment = userAddressDto.getApartment();
        this.floor = userAddressDto.getFloor();
        this.comment = userAddressDto.getComment();
        this.favorite = userAddressDto.isFavorite();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public String getApartment() {
        return apartment;
    }

    public int getFloor() {
        return floor;
    }

    public String getComment() {
        return comment;
    }

    public boolean getFavorite() {
        return favorite;
    }
}
