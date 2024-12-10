package org.manage.pojo;

import org.manage.InvalidInputException;

import java.time.LocalDate;

public interface Person {
    void showInfo();
    LocalDate validateInput(String id, int age, String sex, String birthday) throws InvalidInputException;
}
