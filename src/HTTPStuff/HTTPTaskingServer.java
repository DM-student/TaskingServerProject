package HTTPStuff;

import tasking.managers.Managers;
import tasking.managers.TaskManager;
import tasking.Tasks.*;

import com.google.gson.Gson;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.concurrent.Flow;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HTTPTaskingServer {
    public static final int PORT = 64432;
    public TaskManager taskManager = Managers.loadFromKVServer("http://localhost:8078", "Tasks");
    private final HttpServer server;

    private Gson gson;

    public HTTPTaskingServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/addTask", this::addTask);
        server.createContext("/removeTask", this::removeTask);
        server.createContext("/removeAllTasks", this::removeAllTasks);
        server.createContext("/replaceTask", this::replaceTask);
        server.createContext("/getTask", this::getTask);
        server.createContext("/getEpicTask", this::getEpicTask);
        server.createContext("/getSubTask", this::getSubTask);
        server.createContext("/getTasks", this::getTasks);
        server.createContext("/getHistory", this::getHistory);
        server.createContext("/getPrioritizedTasks", this::getPrioritizedTasks);
        gson = new Gson().newBuilder()
                .setPrettyPrinting()
                .create();
        server.start();
    }
    void addTask(HttpExchange h) throws IOException {
        try
        {
            JsonTaskElement element = gson.fromJson(new String(h.getRequestBody().readAllBytes(), UTF_8), JsonTaskElement.class);
            taskManager.addTask(JsonTaskElement.elementToTask(element));
            h.sendResponseHeaders(200, 0);
        }
        catch (Throwable err)
        {
            err.printStackTrace();
            h.sendResponseHeaders(500, 0);
        }
        finally {
            h.close();
        }
    }
    void removeTask(HttpExchange h) throws IOException {
        try
        {
            int id = Integer.parseInt(h.getRequestURI().getQuery().split("id=")[1]);
            taskManager.removeTask(id);
            h.sendResponseHeaders(200, 0);
        }
        catch (Throwable err)
        {
            err.printStackTrace();
            h.sendResponseHeaders(500, 0);
        }
        finally {
            h.close();
        }
    }
    void removeAllTasks(HttpExchange h) throws IOException {
        try
        {
            taskManager.removeAllTasks();
            h.sendResponseHeaders(200, 0);
        }
        catch (Throwable err)
        {
            err.printStackTrace();
            h.sendResponseHeaders(500, 0);
        }
        finally {
            h.close();
        }
    }
    void replaceTask(HttpExchange h) throws IOException {
        try
        {
            int id = Integer.parseInt(h.getRequestURI().getQuery().split("id=")[1]);
            JsonTaskElement element = gson.fromJson(new String(h.getRequestBody().readAllBytes(), UTF_8), JsonTaskElement.class);
            taskManager.replaceTask(id, JsonTaskElement.elementToTask(element));
            h.sendResponseHeaders(200, 0);
        }
        catch (Throwable err)
        {
            err.printStackTrace();
            h.sendResponseHeaders(500, 0);
        }
        finally {
            h.close();
        }
    }
    void getTask(HttpExchange h) throws IOException {
        try
        {
            int id = Integer.parseInt(h.getRequestURI().getQuery().split("id=")[1]);
            JsonTaskElement element = JsonTaskElement.taskToElement(taskManager.getTask(id));
            String json = gson.toJson(element);
            byte[] resp = json.getBytes(UTF_8);
            h.getResponseHeaders().add("Content-Type", "application/json");
            h.sendResponseHeaders(200, resp.length);
            h.getResponseBody().write(resp);
        }
        catch (Throwable err)
        {
            err.printStackTrace();
            h.sendResponseHeaders(500, 0);
        }
        finally {
            h.close();
        }
    }
    void getEpicTask(HttpExchange h) throws IOException {
        try
        {
            int id = Integer.parseInt(h.getRequestURI().getQuery().split("id=")[1]);
            JsonTaskElement element = JsonTaskElement.taskToElement(taskManager.getEpicTask(id));
            String json = gson.toJson(element);
            byte[] resp = json.getBytes(UTF_8);
            h.getResponseHeaders().add("Content-Type", "application/json");
            h.sendResponseHeaders(200, resp.length);
            h.getResponseBody().write(resp);
        }
        catch (Throwable err)
        {
            err.printStackTrace();
            h.sendResponseHeaders(500, 0);
        }
        finally {
            h.close();
        }
    }
    void getSubTask(HttpExchange h) throws IOException {
        try
        {
            int id = Integer.parseInt(h.getRequestURI().getQuery().split("id=")[1]);
            JsonTaskElement element = JsonTaskElement.taskToElement(taskManager.getSubTask(id));
            String json = gson.toJson(element);
            byte[] resp = json.getBytes(UTF_8);
            h.getResponseHeaders().add("Content-Type", "application/json");
            h.sendResponseHeaders(200, resp.length);
            h.getResponseBody().write(resp);
        }
        catch (Throwable err)
        {
            err.printStackTrace();
            h.sendResponseHeaders(500, 0);
        }
        finally {
            h.close();
        }
    }
    void getTasks(HttpExchange h) throws IOException {
        try
        {
            Object[] elements = taskManager.getTasks().stream().map(JsonTaskElement::taskToElement).toArray();
            String json = gson.toJson(elements);
            byte[] resp = json.getBytes(UTF_8);
            h.getResponseHeaders().add("Content-Type", "application/json");
            h.sendResponseHeaders(200, resp.length);
            h.getResponseBody().write(resp);
        }
        catch (Throwable err)
        {
            err.printStackTrace();
            h.sendResponseHeaders(500, 0);
        }
        finally {
            h.close();
        }
    }
    void getHistory(HttpExchange h) throws IOException {
        try
        {
            Object[] elements = taskManager.getHistory().stream().map(JsonTaskElement::taskToElement).toArray();
            String json = gson.toJson(elements);
            byte[] resp = json.getBytes(UTF_8);
            h.getResponseHeaders().add("Content-Type", "application/json");
            h.sendResponseHeaders(200, resp.length);
            h.getResponseBody().write(resp);
        }
        catch (Throwable err)
        {
            err.printStackTrace();
            h.sendResponseHeaders(500, 0);
        }
        finally {
            h.close();
        }
    }
    void getPrioritizedTasks(HttpExchange h) throws IOException {
        try
        {
            Object[] elements = taskManager.getPrioritizedTasks().stream().map(JsonTaskElement::taskToElement).toArray();
            String json = gson.toJson(elements);
            byte[] resp = json.getBytes(UTF_8);
            h.getResponseHeaders().add("Content-Type", "application/json");
            h.sendResponseHeaders(200, resp.length);
            h.getResponseBody().write(resp);
        }
        catch (Throwable err)
        {
            err.printStackTrace();
            h.sendResponseHeaders(500, 0);
        }
        finally {
            h.close();
        }
    }
}