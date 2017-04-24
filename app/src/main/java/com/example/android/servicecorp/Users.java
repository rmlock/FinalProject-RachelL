package com.example.android.servicecorp;

/**
 * Created by Rachel Lockerman on 4/16/2017.
 */

public class Users {
    String name;
    String email;
    String placement;


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




    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }
    public Users(){

    }

    public Users(String name, String email, String placement){
        this.placement=placement;
        this.name= name;
        this.email=email;


    }
}
