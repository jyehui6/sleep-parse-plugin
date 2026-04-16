package com.leanstar.sleepparse.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.leanstar.sleepparse.constant.DataType;
import com.leanstar.sleepparse.util.JsonUtil;

import java.util.Map;

/**
 * 0x61数据类型的解析器
 */
public class SensiResolver implements JsonDataResolver {
    @Override
    public ObjectNode resolve(String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode datas = objectMapper.createObjectNode();
        ObjectNode datass = objectMapper.createObjectNode();
        Map<String, Object> map = JsonUtil.json2map(data);
        map.remove("id");
        ObjectNode objectNode = JsonUtil.getObjectNode((Map<String, Object>) map.get("sensiSetting"));
        datass.put("sensiSetting",objectNode);
        datas.put("id", DataType.T_0x61.value());
        datas.put("content",datass);
        return datas;
    }
}
