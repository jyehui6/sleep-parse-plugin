package com.leanstar.sleepparse.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.leanstar.sleepparse.constant.DataType;
import com.leanstar.sleepparse.util.JsonUtil;

import java.util.List;
import java.util.Map;

/**
 * 0x69数据类型的解析器
 * 实时节点数据
 */
public class RealTimeInstallmentResolver implements JsonDataResolver {

    @Override
    public ObjectNode resolve(String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode datas = objectMapper.createObjectNode();
        Map<String, Object> map = JsonUtil.json2map(data);
        map.remove("id");
        datas.put("id", DataType.T_0x69.value());
        ObjectNode objectNode = objectMapper.createObjectNode();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof List) {
                // 处理列表类型，保持为数组类型
                ArrayNode arrayNode = objectMapper.createArrayNode();
                List<?> listValue = (List<?>) value;
                for (Object item : listValue) {
                    if (item instanceof Number) {
                        arrayNode.add((Integer) item);
                    } else if (item instanceof String) {
                        arrayNode.add((String) item);
                    } else if (item instanceof Boolean) {
                        arrayNode.add((Boolean) item);
                    } else {
                        arrayNode.add(item.toString());
                    }
                }
                objectNode.set(key, arrayNode);
            } else if (value instanceof Number) {
                // 处理数值类型
                if (value instanceof Integer) {
                    objectNode.put(key, (Integer) value);
                } else if (value instanceof Long) {
                    objectNode.put(key, (Long) value);
                } else if (value instanceof Double) {
                    objectNode.put(key, (Double) value);
                } else {
                    objectNode.put(key, ((Number) value).doubleValue());
                }
            } else if (value instanceof String) {
                // 处理字符串类型
                objectNode.put(key, (String) value);
            } else if (value instanceof Boolean) {
                // 处理布尔类型
                objectNode.put(key, (Boolean) value);
            }
        }
        datas.put("content",objectNode);
        return datas;
    }

}
