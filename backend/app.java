package backend;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class app {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        server.createContext("/", exchange -> {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) path = "/index.html";
            File file = new File("public" + path);
            if (file.exists()) {
                byte[] bytes = Files.readAllBytes(file.toPath());
                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            } else {
                exchange.sendResponseHeaders(404, -1);
            }
        });

        server.createContext("/api/students", exchange -> {
            List<Student> students = FileUtil.readStudents();
            String query = exchange.getRequestURI().getQuery();
            Map<String, String> params = parseQuery(query);

            if (params.containsKey("q") && !params.get("q").isEmpty()) {
                String keyword = params.get("q").toLowerCase();
                students = students.stream()
                    .filter(s -> s.name.toLowerCase().contains(keyword) || String.valueOf(s.id).contains(keyword))
                    .collect(Collectors.toList());
            }

            if (params.containsKey("sort")) {
                if (params.get("sort").equals("score")) {
                    students.sort((a, b) -> b.score - a.score);
                } else if (params.get("sort").equals("id")) {
                    students.sort(Comparator.comparingInt(a -> a.id));
                }
            }

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);
                json.append(String.format("{\"id\":%d,\"name\":\"%s\",\"score\":%d}", s.id, s.name, s.score));
                if (i < students.size() - 1) json.append(",");
            }
            json.append("]");

            byte[] response = json.toString().getBytes("UTF-8");
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
            exchange.sendResponseHeaders(200, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        });

        server.createContext("/api/add", exchange -> {
            Map<String, String> params = parseQuery(exchange.getRequestURI().getQuery());
            List<Student> students = FileUtil.readStudents();
            
            int newId = Integer.parseInt(params.get("id"));
            boolean exists = students.stream().anyMatch(s -> s.id == newId);
            
            if (!exists) {
                students.add(new Student(newId, params.get("name"), Integer.parseInt(params.get("score"))));
                FileUtil.saveStudents(students);
                sendResponse(exchange, "{\"status\":\"ok\"}");
            } else {
                sendResponse(exchange, "{\"status\":\"error\", \"message\":\"ID exists\"}");
            }
        });

        server.createContext("/api/update", exchange -> {
            Map<String, String> params = parseQuery(exchange.getRequestURI().getQuery());
            int targetId = Integer.parseInt(params.get("id"));
            
            List<Student> students = FileUtil.readStudents();
            for (Student s : students) {
                if (s.id == targetId) {
                    s.name = params.get("name");
                    s.score = Integer.parseInt(params.get("score"));
                    break;
                }
            }
            FileUtil.saveStudents(students);
            sendResponse(exchange, "{\"status\":\"updated\"}");
        });

        server.createContext("/api/delete", exchange -> {
            int id = Integer.parseInt(parseQuery(exchange.getRequestURI().getQuery()).get("id"));
            List<Student> students = FileUtil.readStudents();
            students.removeIf(s -> s.id == id);
            FileUtil.saveStudents(students);
            sendResponse(exchange, "{\"status\":\"deleted\"}");
        });

        System.out.println("Server started on port 8000...");
        server.start();
    }

    private static void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static Map<String, String> parseQuery(String query) {
        Map<String, String> result = new HashMap<>();
        if (query == null) return result;
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) result.put(entry[0], entry[1]);
        }
        return result;
    }
}