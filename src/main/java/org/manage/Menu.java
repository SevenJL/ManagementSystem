package org.manage;

import org.manage.exception.InvalidInputException;
import org.manage.pojo.Clazz;
import org.manage.pojo.Lecturer;
import org.manage.pojo.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {
    private final String[][] menus; // 存储一级和二级菜单内容
    private final Scanner scanner;
    private final Clazz clazz = new Clazz("软件工程", "2024", 50);

    public static void main(String[] args) throws ParseException {
        new Menu().run();
    }

    public Menu() {
        // 定义一级菜单和二级菜单
        menus = new String[][]{
                {"班级信息", "学生管理", "教师管理", "保存学生信息", "保存教师信息", "读取文件", "退出系统"},
                {"学生信息", "添加学生", "删除学生", "修改学生", "根据学号查询", "返回上级菜单", "退出系统"},
                {"教师信息", "添加教师", "删除教师", "修改教师", "根据工号查询", "返回上级菜单", "退出系统"}
        };
        scanner = new Scanner(System.in);
    }

    public void displayMenu(String[] menu) {
        System.out.println();
        System.out.println("=====================");
        System.out.println("欢迎使用班级信息管理系统！");
        System.out.println("=====================");
        for (int i = 0; i < menu.length; i++) {
            System.out.println("【" + (i + 1) + "】" + menu[i]);
        }
        System.out.println("=====================");
    }


    public void run() throws ParseException {
        while (true) {
            displayMenu(menus[0]); // 显示一级菜单
            System.out.print("请输入序号使用功能：");
            int choice = getUserChoice(menus[0].length);
            switch (choice) {
                case 1 -> displayClassInfo();
                case 2 -> studentManagement();
                case 3 -> teacherManagement();
                case 4 -> saveStudentInfo();
                case 5 -> saveTeacherInfo();
                case 6 -> readFile();
                case 7 -> {
                    System.out.println("系统退出成功，欢迎下次使用！");
                    return;
                }
                default -> System.out.println("无效选择，请重新输入！");
            }
        }
    }

    private void displayClassInfo() {
        clazz.printClazzInfo(); // 打印班级信息
    }

    private void studentManagement() throws ParseException {
        while (true) {
            displayMenu(menus[1]); // 显示学生管理二级菜单
            System.out.print("请输入序号使用功能：");
            int choice = getUserChoice(menus[1].length);
            switch (choice) {
                case 1 -> displayClassInfo();
                case 2 -> addStudent();
                case 3 -> deleteStudent();
                case 4 -> updateStudent();
                case 5 -> searchStudent();
                case 6 -> {
                    System.out.println("返回上级菜单！");
                    return;
                }
                case 7 -> {
                    System.out.println("系统退出成功，欢迎下次使用！");
                    System.exit(0);
                }
                default -> System.out.println("无效选择，请重新输入！");
            }
        }
    }

    private int getUserChoice(int maxOption) {
        int choice;
        try {
            choice = scanner.nextInt();
            if (choice < 1 || choice > maxOption) {
                throw new InvalidInputException("无效选择，请输入1-" + maxOption + "之间的数字！");
            }
        } catch (Exception e) {
            System.out.println("输入无效，请输入1-" + maxOption + "之间的数字！");
            scanner.nextLine(); // 清除错误输入
            choice = -1; // 返回无效值
        }
        return choice;
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
        System.out.print("请输入学生出生日期(格式：2024.02.10)：");
        String birthday = scanner.next();
        if (!Pattern.matches("\\d{4}\\.\\d{2}\\.\\d{2}", birthday)) {
            System.out.println("出生日期格式错误！");
            return;
        }
        try {
            // 格式化为 Date 类型
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            Date birthdayDate = dateFormat.parse(birthday);
            // 创建 Student 对象
            Student student = new Student(id, name, age, gender, birthdayDate);
            clazz.addStudent(student);
        } catch (ParseException e) {
            System.out.println("日期解析错误：" + e.getMessage());
        }
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
        System.out.print("请输入学生出生日期(格式：2024.02.10)：");
        String birthday = scanner.next();
        if (!Pattern.matches("\\d{4}\\.\\d{2}\\.\\d{2}", birthday)) {
            System.out.println("出生日期格式错误！");
            return;
        }
        try {
            // 格式化为 Date 类型
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            Date birthdayDate = dateFormat.parse(birthday);
            // 创建 Student 对象
            Student student = new Student(id, name, age, gender, birthdayDate);
            clazz.updateStudent(student);
        } catch (ParseException e) {
            System.out.println("日期解析错误：" + e.getMessage());
        }
    }

    private void searchStudent() {
        System.out.print("请输入要查询的学生学号：");
        String id = scanner.next();
        Student student = clazz.selectStudentById(id);
        if (student != null) {
            String s = student.showInfo();
            System.out.println(s);
        } else {
            System.out.println("未找到对应学号的学生！");
        }
    }

    private void teacherManagement() throws ParseException {
        while (true) {
            displayMenu(menus[2]); // 显示教师管理二级菜单
            System.out.print("请输入序号使用功能：");
            int choice = getUserChoice(menus[2].length);
            switch (choice) {
                case 1 -> clazz.printTeacherInfo(); // 显示教师信息
                case 2 -> addTeacher(); // 添加教师
                case 3 -> deleteTeacher(); // 删除教师
                case 4 -> updateTeacher(); // 修改教师
                case 5 -> searchTeacher(); // 根据工号查询教师
                case 6 -> {
                    System.out.println("返回上级菜单！");
                    return;
                }
                case 7 -> {
                    System.out.println("系统退出成功，欢迎下次使用！");
                    System.exit(0);
                }
                default -> System.out.println("无效选择，请重新输入！");
            }
        }
    }

    private void addTeacher() throws ParseException {
        System.out.print("请输入教师工号（A开头4位，例如：A001）：");
        String id = scanner.next();
        System.out.print("请输入教师姓名：");
        String name = scanner.next();
        System.out.print("请输入教师年龄（25-65）：");
        int age = scanner.nextInt();
        System.out.print("请输入教师性别（男/女）：");
        String sex = scanner.next();
        System.out.print("请输入新的教师担任课程：");
        String course = scanner.next();
        System.out.print("请输入教师出生日期(格式：2024.02.12)：");
        String birthday = scanner.next();
        try {
            // 格式化为 Date 类型
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            Date birthdayDate = dateFormat.parse(birthday);
            // 创建 Student 对象
            Lecturer addTeacher = new Lecturer(id, name, age, sex, course, birthdayDate);
            clazz.addTeacher(addTeacher);
        } catch (ParseException e) {
            System.out.println("日期解析错误：" + e.getMessage());
        }
    }

    private void deleteTeacher() {
        System.out.print("请输入要删除的教师工号：");
        String id = scanner.next();
        if (clazz.isTeacherExist(id)) {
            clazz.deleteTeacher(id);
            System.out.println("教师删除成功！");
        } else {
            System.out.println("工号不存在，删除失败！");
        }
    }

    private void updateTeacher() throws ParseException {
        System.out.print("请输入要修改的教师工号：");
        String id = scanner.next();
        if (!clazz.isTeacherExist(id)) {
            System.out.println("工号不存在，无法修改！");
            return;
        }
        System.out.print("请输入新的教师姓名：");
        String name = scanner.next();
        System.out.print("请输入新的教师年龄：");
        int age = scanner.nextInt();
        System.out.print("请输入新的教师性别（男/女）：");
        String sex = scanner.next();
        System.out.print("请输入新的教师担任课程：");
        String course = scanner.next();
        // 创建更新后的教师对象并更新信息
        System.out.print("请输入新的教师出生日期(格式：2024.02.12)：");
        String birthday = scanner.next();
        try {
            // 格式化为 Date 类型
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            Date birthdayDate = dateFormat.parse(birthday);
            // 创建 Student 对象
            Lecturer updatedTeacher = new Lecturer(id, name, age, sex, course, birthdayDate);
            clazz.updateTeacher(updatedTeacher);
        } catch (ParseException e) {
            System.out.println("日期解析错误：" + e.getMessage());
        }
        System.out.println("教师信息修改成功！");
    }

    private void searchTeacher() {
        System.out.print("请输入要查询的教师工号：");
        String id = scanner.next();
        Lecturer teacher = clazz.selectTeacherById(id);
        if (teacher != null) {
            System.out.println(teacher.showInfo());
        } else {
            System.out.println("未找到对应工号的教师！");
        }
    }

    private void saveStudentInfo() {
        System.out.println("请输入文件保存的全路径，形如（“d://1.txt”）：");
        String path = scanner.next();
        try {
            clazz.saveStudentData(path);
            System.out.println("数据保存到位置：" + path + ", 保存成功！");
        } catch (Exception e) {
            System.out.println("保存失败：" + e.getMessage());
        }
    }

    private void saveTeacherInfo() {
        System.out.println("请输入文件保存的全路径，形如（“d://1.txt”）：");
        String path = scanner.next();
        try {
            clazz.saveTeacherData(path);
            System.out.println("数据保存到位置：" + path + ", 保存成功！");
        } catch (Exception e) {
            System.out.println("保存失败：" + e.getMessage());
        }
    }

    private void readFile() {
        System.out.println("请输入读取文件的全路径，形如（“d://1.txt”）：");
        String path = scanner.next();
        try {
            clazz.readData(path);
            System.out.println("读取路径：" + path + ", 数据如下：");
            clazz.printClazzInfo();
        } catch (Exception e) {
            System.out.println("读取失败：" + e.getMessage());
        }
    }
    
}
