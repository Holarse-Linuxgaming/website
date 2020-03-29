package de.holarse.backend.miracle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rating 
{
    @JsonProperty("age")
    private String age;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}