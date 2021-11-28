package ru.sillyseal;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    static final String pathToFile = "C:/Users/micke/Desktop/nbki_testtask/toSort.csv";
    static final String pathToOutput1 = "C:/Users/micke/Desktop/nbki_testtask/output1.csv";
    static final String pathToOutput2 = "C:/Users/micke/Desktop/nbki_testtask/output2.csv";

    public static void main(String[] args) {
        //Задание 1
        System.out.println("\nЗадание 1:");
        System.out.println(replace("test-of-replace_method",'-',';'));
        System.out.println(replace("test-of-replace_method",'e','a'));
        //Задание 2
        System.out.println("\nЗадание 2.1:");
        System.out.println(stringToInt("0"));
        System.out.println(stringToInt("2838293"));
        System.out.println(stringToInt("-72566347"));
        System.out.println("\nЗадание 2.2:");
        System.out.println(stringToDouble("-0.15"));
        System.out.println(stringToDouble("3.14159265359"));
        System.out.println(stringToDouble("353"));
        //Задание 3
        System.out.println("\nЗадание 3:");
        fizzBuzz();
        //Задание 4
        System.out.println("\nЗадание 4:");
        sortCsvFull(pathToFile,pathToOutput1);
        sortCsvByLine(pathToFile,pathToOutput2);
        //System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    }

    //Задание 1
    public static String replace(String data, char oldChar, char newChar){
        char[] data_arr = data.toCharArray();
        for (int i = 0; i<data_arr.length; i++) {
            if (data_arr[i] == oldChar){
                data_arr[i] = newChar;
            }
        }
        return String.valueOf(data_arr);
    }

    //Задание 2
    //Перевод String в int. Аналог Integer.parseInt()
    public static int stringToInt(String data){
        int result = 0;
        boolean is_negative = false;
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        for (byte elem:bytes) {
            if (elem == 45){
                is_negative = true;
                continue;
            }
            result += (elem & 0xF);
            result *= 10;
        }
        return is_negative? result/-10 : result/10;
    }

    //Перевод String в double. Аналог Double.parseDouble()
    public static double stringToDouble(String data){
        double result = 0;
        int count = 0;
        boolean is_negative = false;
        boolean dot_flag = false;
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        for (byte elem:bytes) {
            if (elem == 45){
                is_negative = true;
                continue;
            }
            if (elem == 46){
                dot_flag = true;
                continue;
            }
            if (dot_flag){
                count++;
            }
            result += (elem & 0xF);
            result *= 10;
        }
        return is_negative? (result/-10)/Math.pow(10,count): (result/10)/Math.pow(10,count);
    }

    //Задание 3
    public static void fizzBuzz(){
        for (int i=1; i<=100; i++){
            if (i%3 == 0 && i%5 != 0){
                System.out.println("Fizz");
            }
            else if(i%3 != 0 && i%5 == 0){
                System.out.println("Buzz");
            }
            else if(i%3 == 0){
                System.out.println("FizzBuzz");
            }
            else{
                System.out.println(i);
            }
        }
    }

    //Задание 4
    public static void sortCsvFull(String src, String dst) {
        ArrayList<String> data = new ArrayList<>();
        String columns = null;
        try {
            File file = new File(src);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            columns = line;
            while (line != null) {
                data.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        data.remove(0);
        data.sort(Comparator.comparingInt(o -> Integer.parseInt(o.split(";")[0])));
        try{
        FileWriter writer = new FileWriter(dst);
        writer.write(columns);
        for(String str: data) {
            writer.write(str + System.lineSeparator());
        }
        writer.close();
        System.out.println("Файл отсортирован и сохранен в " + dst);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sortCsvByLine(String src, String dst){
        FileInputStream inputStream = null;
        Scanner sc = null;
        AVLTree tree = new AVLTree();
        try {
            try {
                inputStream = new FileInputStream(src);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            sc = new Scanner(inputStream, StandardCharsets.UTF_8);
            String columns = sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                //System.out.println(line);
                tree.root = tree.insert(tree.root,Integer.parseInt(line.split(";")[0]));
            }
            if (sc.ioException() != null) {
                try {
                    throw sc.ioException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sc != null) {
                sc.close();
            }
        }
        System.out.println("Preorder traversal" +
                " of constructed tree is : ");
        tree.preOrder(tree.root);
        System.out.println(tree.root.key);
    }

}
