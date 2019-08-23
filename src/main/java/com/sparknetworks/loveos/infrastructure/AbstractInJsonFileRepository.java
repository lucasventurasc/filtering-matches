package com.sparknetworks.loveos.infrastructure;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

class AbstractInJsonFileRepository<T> {

    private File filesPath;

    AbstractInJsonFileRepository(File filesPath) {
        this.filesPath = filesPath;
    }

    T readJsonData(String fileName) throws FileNotFoundException {
        InputStream inputStream = readInputStreamFor(fileName);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")))) {
            String json = br.lines().collect(Collectors.joining(System.lineSeparator()));
            return createGson().fromJson(json, getType());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Type getType() {
        Type mySuperclass = this.getClass().getGenericSuperclass();
        return ((ParameterizedType)mySuperclass).getActualTypeArguments()[0];
    }

    private Gson createGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    private InputStream readInputStreamFor(String fileName) throws FileNotFoundException {
        return new DataInputStream(new FileInputStream(filesPath + File.separator + fileName));
    }

}
