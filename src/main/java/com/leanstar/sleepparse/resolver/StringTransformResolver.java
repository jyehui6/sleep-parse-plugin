package com.leanstar.sleepparse.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.leanstar.sleepparse.domain.MyBase64;

/**
 * base64透传解析器，透传的数据类型是String
 */
public class StringTransformResolver extends AbstractSleepResolver {

    @Override
    public ObjectNode resolve(MyBase64 target) {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode data = objectMapper.createObjectNode();

        data.put("id", target.getKey());
        data.put("content", target.getSourceStr());

        return data;

    }

}
