package org.manage.entity;

import org.manage.exception.InvalidInputException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lecturer implements Person {
    private static final long serialVersionUID = 1L;
    private String id; // 工号
    private String name; // 姓名
    private Date birthday; // 出生日期
    private int age; // 年龄
    private String sex; // 性别
    private String course; // 所教课程


    // 带参数构造方法
    public Lecturer(String id, String name, int age, String sex, String course, Date birthday) throws ParseException {
        check(id, name, age, sex, birthday);
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.course = course;
        this.birthday = birthday;
    }

    @Override
    public String showInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        return "任课教师-工号：" + id +
                ", 名字：" + name +
                ", 年龄：" + age +
                ", 性别：" + sex +
                ", 出生日期：" + sdf.format(birthday) +
                ", 担任课程：" + course;
    }

    @Override
    public void check(String id, String name, int age, String sex, Date birthday) throws InvalidInputException {
        if (!id.matches("A\\d{3}")) {
            throw new InvalidInputException("工号格式错误，必须以'A'开头，后接3位数字！");
        }
        if (name == null || name.isEmpty()) {
            throw new InvalidInputException("姓名不能为空！");
        }
        if (age < 25 || age > 65) {
            throw new InvalidInputException("年龄必须在25到65之间！");
        }
        if (!sex.equals("男") && !sex.equals("女")) {
            throw new InvalidInputException("性别只能是'男'或'女'！");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String string = sdf.format(birthday);
        if (!string.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new InvalidInputException("出生日期格式错误，必须为yyyy-MM-dd！");
        }

    }
    // Getter 和 Setter 方法

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

}
