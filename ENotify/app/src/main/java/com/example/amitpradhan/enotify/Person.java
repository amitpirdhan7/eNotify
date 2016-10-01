package com.example.amitpradhan.enotify;

import java.io.Serializable;

/**
 * Created by AMITPRADHAN on 05-Jun-15.
 */
public class Person implements Serializable
{
    String name;
    String email;
    public Person()
    {
        this.name=" ";
        this.email=" ";
    }
    public Person(String name1,String email1)
     {
         this.name=name1;
         this.email=email1;
     }
    public String getName()
    {
        return this.name;
    }
    public String getEmail()
    {
        return this.email;
    }
}
