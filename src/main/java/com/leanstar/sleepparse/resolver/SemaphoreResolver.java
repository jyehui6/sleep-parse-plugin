package com.leanstar.sleepparse.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.leanstar.sleepparse.constant.DataType;
import com.leanstar.sleepparse.util.JsonUtil;

import java.util.List;
import java.util.Map;

/**
 * 0x5E数据类型的解析器
 */
public class SemaphoreResolver implements JsonDataResolver {

    @Override
    public ObjectNode resolve(String data) {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode datas = objectMapper.createObjectNode();
        Map<String, Object> map = JsonUtil.json2map(data);
        map.remove("id");
        datas.put("id", DataType.T_0x66.value());
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String result = "[";
            List value = (List) entry.getValue();
            for(int i=0;i<value.size();i++){
                if(i==value.size()-1){
                    result+=value.get(i);
                }else{
                    result+=value.get(i)+",";
                }
            }
            result+="]";
            System.out.println(result);
            datas.put(entry.getKey(), result);
        }

        return datas;
    }
}
