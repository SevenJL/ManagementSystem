package org.manage.pojo;

import org.manage.InvalidInputException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

public class Lecture implements Person {
    private String id;
    private String name;
    private int age;
    private String gender;
    private String course;
    private Date birthday;

    
    

    @Override
    public void showInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        System.out.println("任课教师工号：" + id + ",名字：" + name + ",年龄：" + age +
                ",性别：" + gender + ",担任课程：" + course + ",出生日期：" + sdf.format(birthday));
    }
    @Override
    public LocalDate validateInput(String id, int age, String sex, String birthday) throws InvalidInputException {
        if (!Pattern.matches("S\\d{3}", id)) {
            throw new InvalidInputException("学号必须以S字母开头,一共4位,形如S001！");
        }
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
        return "Lecture{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", course='" + course + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture lecture = (Lecture) o;
        return age == lecture.age && Objects.equals(id, lecture.id) && Objects.equals(name, lecture.name) && Objects.equals(gender, lecture.gender) && Objects.equals(course, lecture.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, gender, course);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}