package org.manage.pojo;

import org.manage.InvalidInputException;

import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;

public class ClassTeacher implements Person {
    private String name;
    private int age;
    private String gender;

    public ClassTeacher(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    @Override
    public void showInfo()  {
        System.out.println("班主任名字：" + name + "-年龄" + age + "-性别" + gender);
    }

    @Override
    public LocalDate validateInput(String id, int age, String sex, String birthday) throws InvalidInputException {
        if (age < 1 || age > 100) {
            throw new InvalidInputException("年龄应该在1-100之间");
        }
        if (!sex.equals("男") && !sex.equals("女")) {
            throw new InvalidInputException("性别只能输入男或女！");
        }
        if (!Pattern.matches("\\d{4}\\.\\d{1,2}\\.\\d{1,2}", birthday)) {
            throw new InvalidInputException("输入的日期格式错误，输入格式如：2024.2.12或者2024.08.01");
        }

        return null;
    }

    @Override
    public String toString() {
        return "ClassTeacher{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassTeacher that = (ClassTeacher) o;
        return age == that.age && Objects.equals(name, that.name) && Objects.equals(gender, that.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, gender);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}