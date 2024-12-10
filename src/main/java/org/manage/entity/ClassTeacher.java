package org.manage.entity;

import org.manage.exception.InvalidInputException;

import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

public class ClassTeacher implements Person {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    private String gender;

    public ClassTeacher(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    @Override
    public String showInfo()  {
        return "班主任信息：" + "姓名：" + name + "，年龄：" + age + "，性别：" + gender;
    }

    @Override
    public void check(String id,String name , int age, String sex, Date birthday) throws InvalidInputException {
        if (age < 1 || age > 100) {
            throw new InvalidInputException("年龄应该在1-100之间");
        }
        if (name == null || name.isEmpty()) {
            throw new InvalidInputException("名字不能为空");
        }
        if (!sex.equals("男") && !sex.equals("女")) {
            throw new InvalidInputException("性别只能输入男或女！");
        }
        if (!Pattern.matches("\\d{4}\\.\\d{2}\\.\\d{2}", birthday.toString())) {
            throw new InvalidInputException("出生日期格式错误，必须为yyyy.MM.dd！");
        }
        System.out.println("检查通过");
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