package com.mycompany.inviter.customerInviter.jsonUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonToBeanTransformerComponent<T> {

    public T transform(String jsonObject, Class clazz) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        T javaObject = (T) mapper.readValue(jsonObject,clazz);
        return javaObject;
    }

}
