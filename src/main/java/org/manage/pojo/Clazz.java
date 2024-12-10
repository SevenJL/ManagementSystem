package org.manage.pojo;

import java.util.ArrayList;
import java.util.List;

public class Clazz {
    private final String major;
    private final String grade;
    private final int maxStudents;
    private final List<Student> students;

    public Clazz(String major, String grade, int maxStudents) {
        this.major = major;
        this.grade = grade;
        this.maxStudents = maxStudents;
        this.students = new ArrayList<>();
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
        students.removeIf(student -> student.getStudentId().equals(studentId));
    }

    public void updateStudent(Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(updatedStudent.getStudentId())) {
                students.set(i, updatedStudent);
                return;
            }
        }
    }

    public Student selectStudentById(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public boolean isStudentExist(String studentId) {
        return students.stream().anyMatch(student -> student.getStudentId().equals(studentId));
    }

    public void printClazzInfo() {
        System.out.println(grade + "年级-" + major + "专业-学生人数：" + students.size());
        for (Student student : students) {
            student.showInfo();
        }
    }
}
