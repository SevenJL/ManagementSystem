package org.manage.pojo;

import org.manage.exception.InvalidInputException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Student implements Person {
    private static final long serialVersionUID = 1L;
    private static int idNumber = 0;
    private int studentNumber = 0; // 学生编号计数
    private String id; // 学号
    private String name; // 姓名
    private Date birthday; // 出生日期
    private int age; // 年龄
    private String sex; // 性别
    private Clazz clazz; // 班级
    private ClassTeacher classTeacher; // 班主任

    // 带参数构造方法
    @Override
    public String showInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return "学生信息-系统编号" + studentNumber +
                " 学号：" + id +
                ", 姓名：" + name +
                ", 性别：" + sex +
                ", 年龄：" + age +
                ", 出生日期：" + sdf.format(birthday);
    }
    @Override
    public void check(String id, String name, int age, String sex, Date birthday) throws InvalidInputException {
        if (!id.matches("S\\d{3}")) {
            throw new InvalidInputException("学号格式错误，必须以'S'开头，后接3位数字！");
        }
        if (name == null || name.isEmpty()) {
            throw new InvalidInputException("姓名不能为空！");
        }
        if (age < 1 || age > 100) {
            throw new InvalidInputException("年龄必须在1到100之间！");
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
    public Student(String systemNumber,String id, String name, int age, String sex, Date birthday) throws ParseException {
        this.studentNumber = Integer.parseInt(systemNumber);
        check(id, name, age, sex, birthday);
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.birthday = birthday;
    }

    public Student(String id, String name, int age, String sex, Date birthday) throws ParseException {
        this.studentNumber = idNumber++;
        check(id, name, age, sex, birthday);
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.birthday = birthday;
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

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public ClassTeacher getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(ClassTeacher classTeacher) {
        this.classTeacher = classTeacher;
    }

}
