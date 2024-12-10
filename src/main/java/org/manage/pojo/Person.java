package org.manage.pojo;

import org.manage.exception.InvalidInputException;

import java.io.Serializable;
import java.util.Date;

public interface Person extends Serializable {
    
    String showInfo();
    
    void check(String id, String name, int age, String sex, Date birthday) throws InvalidInputException;
}
