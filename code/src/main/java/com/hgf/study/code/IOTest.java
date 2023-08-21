package com.hgf.study.code;

import java.io.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Hello world!
 */
public class IOTest {
    public static void main(String[] args) {
        in = new int[0];
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        File a = new File("a.txt");
        File b = new File("b.txt");
        File c = new File("c.txt");
        try {
            a.delete();
            b.delete();
            c.delete();
            a.createNewFile();
            b.createNewFile();
            c.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Writer writer = new FileWriter("a.txt")) {
            writer.write(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("a.txt"), StandardCharsets.UTF_8));
             Writer writer = new BufferedWriter(new FileWriter("b.txt"))) {
            String t;
            while ((t = reader.readLine()) != null){
                writer.write(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (InputStream inputStream = Files.newInputStream(Paths.get("b.txt"));
             OutputStream outputStream = Files.newOutputStream(Paths.get("c.txt"))){
            byte[] t = new byte[1024];
            int num;
            while ((num = inputStream.read(t)) > 0){
                outputStream.write(t, 0, num);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
