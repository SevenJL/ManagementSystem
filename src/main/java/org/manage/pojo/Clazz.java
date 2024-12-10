package org.manage.pojo;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Clazz {
    // 班级专业
    private final String major;
    // 班级年级
    private final String grade;
    // 班级最大学生人数
    private final int maxStudents;
    // 学生列表
    private final List<Student> students = new ArrayList<>();
    // 教师列表
    private final List<Lecturer> teachers = new ArrayList<>();

    public String getMajor() {
        return major;
    }

    public String getGrade() {
        return grade;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Lecturer> getTeachers() {
        return teachers;
    }

    public Clazz(String major, String grade, int maxStudents) {
        this.major = major;
        this.grade = grade;
        this.maxStudents = maxStudents;
    }

    public void addStudent(Student student) {
        if (students.size() >= maxStudents) {
            System.out.println("班级已满，无法添加更多学生！");
            return;
        }
        students.add(student);
        System.out.println("学生添加成功！当前学生人数：" + students.size());
    }

    public void deleteStudent(String studentId) {
        students.removeIf(student -> student.getId().equals(studentId));
    }

    public void updateStudent(Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(updatedStudent.getId())) {
                students.set(i, updatedStudent);
                return;
            }
        }
    }

    public Student selectStudentById(String studentId) {
        
        for (Student student : students) {
            if (student.getId().equals(studentId)) {
                return student;
            }else {
                System.out.println("未找到该学生");
            }
        }
        return null;
    }

    public boolean isStudentExist(String studentId) {
        return students.stream().anyMatch(student -> student.getId().equals(studentId));
    }

    public void printClazzInfo() {
        System.out.println(grade + "年级-" + major + "专业-学生人数：" + students.size());
        for (Student student : students) {
            System.out.println(student.showInfo());
        }
    }

    // 添加教师
    public void addTeacher(Lecturer teacher) {
        if (!isFull()) {
            teachers.add(teacher);
            System.out.println("教师添加成功！");
        } else {
            System.out.println("教师人数已满，无法添加！");
        }
    }

    // 删除教师
    public void deleteTeacher(String teacherId) {
        teachers.removeIf(teacher -> teacher.getId().equals(teacherId));
    }

    // 更新教师信息
    public void updateTeacher(Lecturer updatedTeacher) {
        for (int i = 0; i < teachers.size(); i++) {
            if (teachers.get(i).getId().equals(updatedTeacher.getId())) {
                teachers.set(i, updatedTeacher);
                return;
            }
        }
        System.out.println("未找到该教师，无法更新！");
    }

    // 根据工号查询教师
    public Lecturer selectTeacherById(String teacherId) {
        for (Lecturer teacher : teachers) {
            if (teacher.getId().equals(teacherId)) {
                return teacher;
            }
        }
        return null;
    }

    // 判断教师是否存在
    public boolean isTeacherExist(String teacherId) {
        return teachers.stream().anyMatch(teacher -> teacher.getId().equals(teacherId));
    }

    // 显示教师信息
    public void printTeacherInfo() {
        if (teachers.isEmpty()) {
            System.out.println("暂无专业教师信息！");
            return;
        }
        System.out.println("专业教师信息：");
        for (Lecturer teacher : teachers) {
            System.out.println(teacher.showInfo());
        }
    }
    
    // 判断班级是否已满
    private boolean isFull() {
        return teachers.size() >= 3;
    }

    // 保存学生数据
    public void saveStudentData(String path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Student student : students) {
                writer.write(student.showInfo());
                writer.newLine();
            }
        }
    }

    // 保存教师数据
    public void saveTeacherData(String path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Lecturer teacher : teachers) {
                writer.write(teacher.showInfo());
                writer.newLine();
            }
        }
    }

    public void readData(String path) throws IOException, ParseException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 简单解析数据，创建对象并添加到列表
                if (line.contains("学生信息")) {
                    Student student = parseStudent(line);
                    students.add(student);
                } else if (line.contains("教师信息")) {
                    Lecturer teacher = parseTeacher(line);
                    teachers.add(teacher);
                }
            }
        }
    }

    private Student parseStudent(String line) throws ParseException {
        String[] parts = line.split(", ");
        String[] parts0Arr = parts[0].split(" ");
        String systemNumber = parts0Arr[0].substring(parts0Arr[0].indexOf("系统编号") + 4).trim();
        String id = parts0Arr[1].split("：")[1];
        String name = parts[1].split("：")[1];
        String sex = parts[2].split("：")[1];
        int age = Integer.parseInt(parts[3].split("：")[1]);
        String birthdayStr = parts[4].split("：")[1];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date birthday = dateFormat.parse(birthdayStr);
        return new Student(systemNumber, id, name, age, sex, birthday);
    }


    private Lecturer parseTeacher(String line) throws ParseException {
        // 解析教师信息的字符串，假设格式固定
        String[] parts = line.split(", ");
        String id = parts[0].split("：")[1]; // 获取工号
        String name = parts[1].split("：")[1]; // 获取名字
        int age = Integer.parseInt(parts[2].split("：")[1]); // 获取年龄
        String sex = parts[3].split("：")[1]; // 获取性别
        String birthdayStr = parts[4].split("：")[1]; // 获取出生日期（格式如 1991年12月01日）
        String course = parts[5].split("：")[1]; // 获取担任课程

        // 将生日字符串转换为 Date 类型
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date birthday = dateFormat.parse(birthdayStr);

        return new Lecturer(id, name, age, sex, course, birthday);
    }


}
