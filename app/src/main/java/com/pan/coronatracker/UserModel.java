package com.pan.coronatracker;

import java.io.Serializable;

public class UserModel implements Serializable {

    private String id;

    private String username;
    private String country;
    private String city_address;
    private String temperature;
    private String nationalID;
    private String probability;

    private Boolean high_temp;
    private Boolean coughing;
    private Boolean headache;
    private Boolean short_breath;
    private Boolean sore_throat;
    private Boolean infected;


    public UserModel() {

    }

    public UserModel(String id, String username, String country, String city_address, String temperature, String nationalID, String probability, Boolean high_temp, Boolean coughing, Boolean headache, Boolean short_breath, Boolean sore_throat, Boolean infected) {
        this.id = id;
        this.username = username;
        this.country = country;
        this.city_address = city_address;
        this.temperature = temperature;
        this.nationalID = nationalID;
        this.probability = probability;
        this.high_temp = high_temp;
        this.coughing = coughing;
        this.headache = headache;
        this.short_breath = short_breath;
        this.sore_throat = sore_throat;
        this.infected = infected;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity_address() {
        return city_address;
    }

    public void setCity_address(String city_address) {
        this.city_address = city_address;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public Boolean getHigh_temp() {
        return high_temp;
    }

    public void setHigh_temp(Boolean high_temp) {
        this.high_temp = high_temp;
    }

    public Boolean getCoughing() {
        return coughing;
    }

    public void setCoughing(Boolean coughing) {
        this.coughing = coughing;
    }

    public Boolean getHeadache() {
        return headache;
    }

    public void setHeadache(Boolean headache) {
        this.headache = headache;
    }

    public Boolean getShort_breath() {
        return short_breath;
    }

    public void setShort_breath(Boolean short_breath) {
        this.short_breath = short_breath;
    }

    public Boolean getSore_throat() {
        return sore_throat;
    }

    public void setSore_throat(Boolean sore_throat) {
        this.sore_throat = sore_throat;
    }

    public Boolean getInfected() {
        return infected;
    }

    public void setInfected(Boolean infected) {
        this.infected = infected;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }
}
