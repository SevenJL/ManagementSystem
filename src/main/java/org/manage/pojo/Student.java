package org.manage.pojo;

import org.manage.InvalidInputException;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.regex.Pattern;

public class Student implements Person{
    private static int idCounter = 0;
    private int id = 0;
    private String studentId;
    private String name;
    private int age;
    private String gender;
    private String className;
    private ClassTeacher classTeacher;
    private String birthday;

    public Student(String studentId, String name, int age, String gender,String birthday) throws ParseException {
        // 从0开始自增
        this.id = idCounter++;
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.birthday = birthday;
    }

    @Override
    public void showInfo() {
        System.out.println("学生信息：系统编号 " + id + ",学号：" + studentId +
                ",姓名 " + name + ",性别：" + gender + ",年龄 " + age +
                ",出生日期：" +birthday);
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
        // 正则表达式校验格式（严格格式：2024.02.10）
        if (!Pattern.matches("\\d{4}\\.\\d{2}\\.\\d{2}", birthday)) {
            throw new InvalidInputException("输入的日期格式错误，输入格式必须为：2024.02.10");
        }

        // 尝试将日期解析为 LocalDate
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            return LocalDate.parse(birthday, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("输入的日期无效，请确保日期合法，例如：2024.02.29（闰年日期）。");
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", className='" + className + '\'' +
                ", classTeacher=" + classTeacher +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && age == student.age && Objects.equals(studentId, student.studentId) && Objects.equals(name, student.name) && Objects.equals(gender, student.gender) && Objects.equals(className, student.className) && Objects.equals(classTeacher, student.classTeacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentId, name, age, gender, className, classTeacher);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ClassTeacher getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(ClassTeacher classTeacher) {
        this.classTeacher = classTeacher;
    }
}