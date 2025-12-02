package backend;

import java.io.*;
import java.util.*;

public class FileUtil {
    private static final String FILE_PATH = "backend/Students.json";

    public static List<Student> readStudents() {
        List<Student> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) json.append(line);
            
            String content = json.toString().replace("[", "").replace("]", "").replace("},{", "}|{");
            if (content.trim().isEmpty()) return list;
            
            String[] items = content.split("\\|\\{");
            for (String item : items) {
                item = item.replace("{", "").replace("}", "").replace("\"", "");
                String[] props = item.split(",");
                int id = 0; String name = ""; int score = 0;
                for (String prop : props) {
                    String[] kv = prop.split(":");
                    if (kv[0].trim().equals("id")) id = Integer.parseInt(kv[1].trim());
                    if (kv[0].trim().equals("name")) name = kv[1].trim();
                    if (kv[0].trim().equals("score")) score = Integer.parseInt(kv[1].trim());
                }
                list.add(new Student(id, name, score));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public static void saveStudents(List<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);
                sb.append(String.format("{\"id\":%d,\"name\":\"%s\",\"score\":%d}", s.id, s.name, s.score));
                if (i < students.size() - 1) sb.append(",");
            }
            sb.append("]");
            bw.write(sb.toString());
        } catch (IOException e) { e.printStackTrace(); }
    }
}