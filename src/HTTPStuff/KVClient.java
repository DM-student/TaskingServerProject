package HTTPStuff;

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

public class KVClient {
    private String apiToken;
    Gson gson = new Gson();
    private HttpClient client = HttpClient.newHttpClient();
    private String KVServerURL;
    public KVClient(String serverURL) throws IOException, InterruptedException {
        KVServerURL = serverURL;
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(KVServerURL + "/register"))
                .timeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        apiToken = response.body();
        System.out.println("Токен получен: " + apiToken);
    }
    public void uploadToServer(String key, String JSON) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(JSON))
                .uri(URI.create(KVServerURL + "/save/" + key +"/?API_TOKEN=" + apiToken))
                .timeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        System.out.println("На сервер отправлена строка:" + JSON);
        System.out.println("Код ответа от сервера:" + response.statusCode());
    }
    public String loadFromServer(String key) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(KVServerURL + "/load/" + key +"/?API_TOKEN=" + apiToken))
                .timeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        System.out.println("От сервера получен ответ:" + response.body());
        System.out.println("Код ответа от сервера:" + response.statusCode());
        return response.body();
    }
}
