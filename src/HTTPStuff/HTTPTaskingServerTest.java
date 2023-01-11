package HTTPStuff;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class HTTPTaskingServerTest {

    @Test
    void testGetQuery() throws URISyntaxException {
        URI s = new URI("http://localhost:8078/test?s=1");
        System.out.println(s.getQuery().split("s=")[1]);
    }
}