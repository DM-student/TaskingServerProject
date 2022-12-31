package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

class GsonTest {
    @Test
    void testCreation() {
        String out;

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try (StringWriter stringWriter = new StringWriter()){
            JsonWriter writer = gson.newJsonWriter(stringWriter);
            writer.beginObject().name("test 1");
            writer.beginObject().name("intest 1");
            writer.value(6);
            writer.endObject();
            writer.endObject();
            writer.close();
            out = stringWriter.toString();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        System.out.println(out);
    }

    @Test
    void testJsonObj()
    {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        testObject[] objs = {new testObject(1, 2), new testObject(3, 4)};
        String json = gson.toJson(objs);
        System.out.println(json);

        testObject[] testArr = gson.fromJson(json, testObject[].class);
        System.out.println(testArr[1].getClass().getSimpleName());
    }
}

class testObject
{
    public int test1;
    public int test2;
    public testObject(int a, int b)
    {
        test1 = a;
        test2 = b;
    }
}