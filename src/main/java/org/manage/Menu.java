package org.manage;

import org.manage.entity.Clazz;
import org.manage.entity.Lecturer;
import org.manage.entity.Student;
import org.manage.exception.InvalidInputException;
import org.manage.utils.DBLecturer;
import org.manage.utils.DBStudent;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * 班级信息管理系统
 */
public class Menu {
    // 存储一级和二级菜单内容
    private final String[][] menus;

    // 创建 Scanner 对象
    private final Scanner scanner;

    // 创建班级对象
    private final Clazz clazz = new Clazz("软件工程", "2024", 50);

    public static void main(String[] args) throws ParseException, SQLException {
        new Menu().run();
    }

    /**
     * 构造方法
     */
    public Menu() {
        // 定义一级菜单和二级菜单
        menus = new String[][]{
                {"班级信息", "学生管理", "教师管理", "保存学生信息", "保存教师信息", "读取文件", "退出系统"},
                {"学生信息", "添加学生", "删除学生", "修改学生", "根据学号查询", "返回上级菜单", "退出系统"},
                {"教师信息", "添加教师", "删除教师", "修改教师", "根据工号查询", "返回上级菜单", "退出系统"}
        };
        scanner = new Scanner(System.in);
    }

    /**
     * 显示菜单
     */
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

    /**
     * 程序入口
     */
    public void run() throws ParseException, SQLException {
        while (true) {
            displayMenu(menus[0]); // 显示一级菜单
            System.out.print("请输入序号使用功能：");
            int choice = getUserChoice(menus[0].length);
            switch (choice) {
                case 1 -> displayClassInfo(); // 显示班级信息
                case 2 -> studentManagement(); // 学生管理
                case 3 -> teacherManagement(); // 教师管理
                case 4 -> saveStudentInfo(); // 保存学生信息到文件
                case 5 -> saveTeacherInfo(); // 保存教师信息到文件
                case 6 -> readFile(); // 读取文件
                case 7 -> {
                    System.out.println("系统退出成功，欢迎下次使用！");
                    // 关闭资源
                    scanner.close();
                    // 关闭数据库连接 避免资源浪费
                    DBStudent.closeConnection();
                    DBLecturer.closeConnection();
                    return;
                }
                default -> System.out.println("无效选择，请重新输入！");
            }
        }
    }

    /**
     * 显示班级信息
     */
    private void displayClassInfo() {
        // 查询数据库
        try {
            // 查询学生和教师信息
            List<Student> students = DBStudent.selectAllStudents();
            List<Lecturer> lecturers = DBLecturer.selectAllLecturers();
            // 显示班级信息
            System.out.println(
                    "2024级" + clazz.getGrade() +
                            "级-" + clazz.getMajor() +
                            "专业-学生人数：" + students.size() +
                            ",专业教师人数：" + lecturers.size());
            // 显示学生信息
            System.out.println("学生列表：");
            for (Student student : students) {
                System.out.println(student.showInfo());
            }
            // 显示教师信息
            System.out.println("教师列表：");
            for (Lecturer lecturer : lecturers) {
                System.out.println(lecturer.showInfo());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 教师管理
     */
    private void displayStudentInfo() {
        try {
            // 查询数据库(查询所有学生)
            List<Student> students = DBStudent.selectAllStudents();
            System.out.println("学生列表：");
            // 显示学生信息 遍历打印出
            for (Student student : students) {
                System.out.println(student.showInfo());
            }
            System.out.println("总计学生人数：" + students.size());
        } catch (SQLException e) {
            throw new RuntimeException("查询学生信息时发生错误: " + e.getMessage(), e);
        }
    }

    /**
     * 教师管理
     */
    private void displayLecturerInfo() {
        try {
            // 查询数据库(查询所有教师)
            List<Lecturer> lecturers = DBLecturer.selectAllLecturers();
            System.out.println("教师列表：");
            // 显示教师信息 遍历打印出
            for (Lecturer lecturer : lecturers) {
                System.out.println(lecturer.showInfo());
            }
            System.out.println("总计教师人数：" + lecturers.size());
        } catch (SQLException e) {
            throw new RuntimeException("查询教师信息时发生错误: " + e.getMessage(), e);
        }
    }

    /**
     * 学生管理
     */
    private void studentManagement() throws ParseException, SQLException {
        while (true) {
            displayMenu(menus[1]); // 显示学生管理二级菜单
            System.out.print("请输入序号使用功能：");
            int choice = getUserChoice(menus[1].length);
            switch (choice) {
                case 1 -> displayStudentInfo(); // 显示学生信息
                case 2 -> addStudent(); // 添加学生
                case 3 -> deleteStudent(); // 删除学生
                case 4 -> updateStudent(); // 修改学生
                case 5 -> searchStudent(); // 根据学号查询
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

    /**
     * 获取用户输入的选择
     */
    private int getUserChoice(int maxOption) {
        int choice;
        try {
            // 获取用户输入
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

    /**
     * 添加学生
     */
    private void addStudent() {
        System.out.print("请输入学生学号（S开头4位，例如：S001）：");
        String id = scanner.next();
        System.out.print("请输入学生姓名：");
        String name = scanner.next();
        System.out.print("请输入学生年龄（1-100）：");
        int age = scanner.nextInt();
        System.out.print("请输入学生性别（男/女）：");
        String gender = scanner.next();
        System.out.print("请输入学生出生日期(格式：2024.02.10)：");
        String birthday = scanner.next();
        try {
            // 格式化为 Date 类型
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            Date birthdayDate = dateFormat.parse(birthday);
            // 创建 Student 对象
            Student student = new Student(id, name, age, gender, birthdayDate);
            DBStudent.addStudent(student);
        } catch (ParseException e) {
            System.out.println("日期解析错误：" + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除学生
     */
    private void deleteStudent() throws SQLException {
        System.out.print("请输入要删除的学生学号：");
        String id = scanner.next();
        Student student = DBStudent.selectStudentById(id);
        if (student != null) {
            DBStudent.deleteStudent(id);
            System.out.println("学生删除成功！");
        } else {
            System.out.println("学号不存在，删除失败！");
        }
    }

    /**
     * 修改学生信息
     */
    private void updateStudent() throws ParseException, SQLException {
        System.out.print("请输入要修改的学生学号：");
        String id = scanner.next();
        Student selectStudentById = DBStudent.selectStudentById(id);
        if (selectStudentById != null) {
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
        try {
            // 格式化为 Date 类型
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            Date birthdayDate = dateFormat.parse(birthday);
            // 创建 Student 对象
            Student student = new Student(id, name, age, gender, birthdayDate);
            DBStudent.updateStudent(student);
        } catch (ParseException e) {
            System.out.println("日期解析错误：" + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据学号查询学生
     */
    private void searchStudent() throws SQLException {
        System.out.print("请输入要查询的学生学号：");
        String id = scanner.next();
        Student student = DBStudent.selectStudentById(id);
        if (student != null) {
            String s = student.showInfo();
            System.out.println(s);
        } else {
            System.out.println("未找到对应学号的学生！");
        }
    }

    /**
     * 教师管理
     */
    private void teacherManagement() throws ParseException, SQLException {
        while (true) {
            displayMenu(menus[2]); // 显示教师管理二级菜单
            System.out.print("请输入序号使用功能：");
            int choice = getUserChoice(menus[2].length);
            switch (choice) {
                case 1 -> displayLecturerInfo(); // 显示教师信息
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

    /**
     * 添加教师
     */
    private void addTeacher() {
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
            DBLecturer.addLecturer(addTeacher);
        } catch (ParseException e) {
            System.out.println("日期解析错误：" + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除教师
     */
    private void deleteTeacher() throws SQLException {
        System.out.print("请输入要删除的教师工号：");
        String id = scanner.next();
        // 查询教师信息
        Lecturer lecturer = DBLecturer.selectLecturerById(id);
        if (lecturer != null) {
            DBLecturer.deleteLecturer(id);
            System.out.println("教师删除成功！");
        } else {
            System.out.println("工号不存在，删除失败！");
        }
    }

    /**
     * 修改教师信息
     */
    private void updateTeacher() throws SQLException {
        System.out.print("请输入要修改的教师工号：");
        String id = scanner.next();
        // 查询教师信息
        Lecturer lecturer = DBLecturer.selectLecturerById(id);
        // 判断教师是否存在
        if (lecturer == null) {
            // 不存在则返回
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
            // 更新教师信息
            DBLecturer.updateLecturer(updatedTeacher);
        } catch (ParseException e) {
            System.out.println("日期解析错误：" + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("教师信息修改成功！");
    }

    /**
     * 根据工号查询教师
     */
    private void searchTeacher() throws SQLException {
        System.out.print("请输入要查询的教师工号：");
        String id = scanner.next();
        Lecturer teacher = DBLecturer.selectLecturerById(id);
        if (teacher != null) {
            System.out.println(teacher.showInfo());
        } else {
            System.out.println("未找到对应工号的教师！");
        }
    }

    /**
     * 保存学生信息到文件
     */
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

    /**
     * 保存教师信息到文件
     */
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

    /**
     * 读取文件
     */
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
