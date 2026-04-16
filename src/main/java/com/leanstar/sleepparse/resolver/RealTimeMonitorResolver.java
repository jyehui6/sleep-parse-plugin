package com.leanstar.sleepparse.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.leanstar.sleepparse.domain.MyBase64;
import com.leanstar.sleepparse.iterator.Iterator;
import com.leanstar.sleepparse.util.DataConverterUtil;

/**
 * 0x41数据类型的解析器
 */
public class RealTimeMonitorResolver extends AbstractSleepResolver {

    public RealTimeMonitorResolver(){
        attributeInfos = new String[3][2];
        attributeInfos[0][0] = "heartRate";
        attributeInfos[0][1] = "1";

        attributeInfos[1][0] = "respiratoryRate";
        attributeInfos[1][1] = "1";

        attributeInfos[2][0] = "sleepState";
        attributeInfos[2][1] = "1";
    }

    @Override
    public ObjectNode resolve(MyBase64 target) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode data = objectMapper.createObjectNode();
        ObjectNode objectNode = objectMapper.createObjectNode();
        Iterator iterator = target.iterator();
        for(int i = 0; i < attributeInfos.length; i++){
            int currIndex = 0;
            String tempStr = "";
            while(iterator.hasNext()){
                // 从迭代器中取出数据,并拼接成字符串
                tempStr = iterator.next() + tempStr;
                if(++currIndex >= Integer.valueOf(attributeInfos[i][1])){

                    objectNode.put(attributeInfos[i][0], DataConverterUtil.binaryToInt(tempStr));
                    break;
                }
            }
        }

        data.put("id", target.getKey());
        data.putPOJO("content", objectNode);

        return data;
    }

}
