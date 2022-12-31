package test;

import HTTPStuff.KVClient;
import HTTPStuff.KVServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class KVClientTest {
    @Test
    void testServer() throws IOException, InterruptedException {
        KVServer KVServer1 = new KVServer();
        KVServer1.start();
        KVClient KVClient1 = new KVClient("http://localhost:8078");
        KVClient1.uploadToServer("1/history","test");
        KVClient1.loadFromServer("1");
    }
}