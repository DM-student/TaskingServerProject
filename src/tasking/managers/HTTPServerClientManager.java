package tasking.managers;
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
import java.time.Duration;

public class HTTPServerClientManager{
    public static final int PORT = 8080;
    public static final Gson gson = new Gson();
    public static final String KVServerUri = "http://localhost:8078";

    private final HttpClient client;
    private String KVServerToken;
    private final HttpServer server;
    private TaskManager taskManager;

    // Прошу прощения, что отправляю недоделанный код, но даже после беседы с наставником и другими студентами
    // я не понял, удовлетворяет ли мой концепт задачам ТЗ, было бы полезно услышать профессиональный ответ.
    // Концепт заключается в следующем: HTTPServerClientManager является API, который взаимодействует
    // с условным клиентом, а для хранения задач использует KVServer, обращаясь как клиент к серверу.

    public HTTPServerClientManager() throws IOException, InterruptedException {
        client = HttpClient.newHttpClient();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.start();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(KVServerUri + "/register"))
                .timeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        KVServerToken = response.body();
        System.out.println("Токен получен: " + KVServerToken);

        server.createContext("/getTasks", this::getTasks);
    }
    // TODO
    // Сделать реализацию методов TestManager'а если ревьювер одобрит концепт.
    private void getTasks(HttpExchange e) throws IOException {

    }
}
