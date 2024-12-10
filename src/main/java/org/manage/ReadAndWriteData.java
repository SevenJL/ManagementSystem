package org.manage;

import org.manage.pojo.Person;

import java.io.*;
import java.util.List;

public class ReadAndWriteData {
    public void saveData(String filePath, List<Person> list) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Person person : list) {
                writer.println(person.toString());
            }
            System.out.println("数据保存到位置：" + filePath + ",保存成功！");
        } catch (IOException e) {
            System.out.println("保存数据时发生错误: " + e.getMessage());
        }
    }

    public void readData(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("读取路径：" + filePath + ",数据如下：");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("读取数据时发生错误: " + e.getMessage());
        }
    }
}

