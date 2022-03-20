package com.openfaas.function;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openfaas.model.IResponse;
import com.openfaas.model.IRequest;
import com.openfaas.model.Response;

import java.io.IOException;
import java.util.List;

public class Handler extends com.openfaas.model.AbstractHandler {

    private ObjectMapper objectMapper;

    public Handler() {
    }
    public Handler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public IResponse Handle(IRequest req) {
        Response res = new Response();
        try {
            List<Fare> faresList = PreProcess.createFares();

            res.setBody(objectMapper.writeValueAsString(faresList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
