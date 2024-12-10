package org.manage;

import org.manage.pojo.Clazz;
import org.manage.pojo.Student;

import java.text.ParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {
    private final String[] menuItems;
    private final Scanner scanner;
    private final Clazz clazz = new Clazz("软件工程", "2024", 50);

    public static void main(String[] args) throws ParseException {
        new Menu().run();
    }

    public Menu() {
        menuItems = new String[] {
                "显示学生信息", "添加学生信息", "删除学生信息",
                "修改学生信息", "根据学号查询", "退出系统"
        };
        scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("=====================");
        System.out.println("欢迎使用班级信息管理系统！");
        System.out.println("=====================");
        for (int i = 0; i < menuItems.length; i++) {
            System.out.println((i + 1) + "." + menuItems[i]);
        }
        System.out.println("=====================");
    }

    public void run() throws ParseException {
        while (true) {
            displayMenu();
            System.out.print("请输入序号使用功能：");
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("输入无效，请输入数字1-" + menuItems.length + "之间的序号！");
                scanner.nextLine(); // 清除无效输入
                continue;
            }
            if (choice >= 1 && choice <= menuItems.length) {
                switch (choice) {
                    case 1:
                        clazz.printClazzInfo();
                        break;
                    case 2:
                        addStudent();
                        break;
                    case 3:
                        deleteStudent();
                        break;
                    case 4:
                        updateStudent();
                        break;
                    case 5:
                        searchStudent();
                        break;
                    case 6:
                        System.out.println("系统退出成功，欢迎下次使用！");
                        return;
                    default:
                        break;
                }
            } else {
                System.out.println("输入功能序号错误，只能输入1-" + menuItems.length + "之间的数字,请重新输入：");
            }
        }
    }

    private void addStudent() throws ParseException {
        System.out.print("请输入学生学号（S开头4位，例如：S001）：");
        String id = scanner.next();
        if (!id.matches("S\\d{3}")) {
            System.out.println("学号格式错误，必须以'S'开头，后接3位数字！");
            return;
        }
        System.out.print("请输入学生姓名：");
        String name = scanner.next();
        System.out.print("请输入学生年龄（1-100）：");
        int age = scanner.nextInt();
        if (age < 1 || age > 100) {
            System.out.println("年龄必须在1到100之间！");
            return;
        }
        System.out.print("请输入学生性别（男/女）：");
        String gender = scanner.next();
        if (!gender.equals("男") && !gender.equals("女")) {
            System.out.println("性别输入无效，只能输入'男'或'女'！");
            return;
        }
        System.out.print("请输入学生出生日期(格式：2022.01.01)：");
        String birthday = scanner.next();
        // 正则表达式校验格式（严格格式：2024.02.10）
        if (!Pattern.matches("\\d{4}\\.\\d{2}\\.\\d{2}", birthday)) {
            throw new InvalidInputException("输入的日期格式错误，输入格式必须为：2024.02.10");
        }
        Student student = new Student(id, name, age, gender, birthday);
        clazz.addStudent(student);
    }

    private void deleteStudent() {
        System.out.print("请输入要删除的学生学号：");
        String id = scanner.next();
        if (clazz.isStudentExist(id)) {
            clazz.deleteStudent(id);
            System.out.println("学生删除成功！");
        } else {
            System.out.println("学号不存在，删除失败！");
        }
    }

    private void updateStudent() throws ParseException {
        System.out.print("请输入要修改的学生学号：");
        String id = scanner.next();
        if (!clazz.isStudentExist(id)) {
            System.out.println("学号不存在，无法修改！");
            return;
        }
        System.out.print("请输入新的学生姓名：");
        String name = scanner.next();
        System.out.print("请输入新的学生年龄：");
        int age = scanner.nextInt();
        System.out.print("请输入新的学生性别（男/女）：");
        String gender = scanner.next();
        System.out.print("请输入新的学生出生日期(格式：2024.02.12)：");
        String birthday = scanner.next();
        Student updatedStudent = new Student(id, name, age, gender, birthday);
        clazz.updateStudent(updatedStudent);
        System.out.println("学生信息修改成功！");
    }

    private void searchStudent() {
        System.out.print("请输入要查询的学生学号：");
        String id = scanner.next();
        Student student = clazz.selectStudentById(id);
        if (student != null) {
            student.showInfo();
        } else {
            System.out.println("未找到对应学号的学生！");
        }
    }
}
