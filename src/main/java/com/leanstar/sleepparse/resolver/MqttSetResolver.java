package com.leanstar.sleepparse.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.leanstar.sleepparse.constant.DataType;
import com.leanstar.sleepparse.util.JsonUtil;

import java.util.Map;

/**
 * 0x5F数据类型的解析器
 * 上报 网络平台设置相关
 */
public class MqttSetResolver implements JsonDataResolver {
    @Override
    public ObjectNode resolve(String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode datas = objectMapper.createObjectNode();
        ObjectNode datass = objectMapper.createObjectNode();
        Map<String, Object> map = JsonUtil.json2map(data);
        map.remove("id");
        ObjectNode objectNode = JsonUtil.getObjectNode((Map<String, Object>) map.get("mqttSetting"));
        datass.put("mqttSetting",objectNode);
        datas.put("id", DataType.T_0x5F.value());
        datas.put("content",datass);
        return datas;
    }
}
