package com.example.antonyalkmim.androidsocketexample.network;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.IOException;

public class PackUtils {

    public static <T> T decode(byte[] data, Class<T> type) {
        try {
            return new ObjectMapper(new MessagePackFactory())
                    .readValue(data, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static <T> byte[] encode(T data) {
        try {
            return new ObjectMapper(new MessagePackFactory())
                    .writeValueAsBytes(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

}
