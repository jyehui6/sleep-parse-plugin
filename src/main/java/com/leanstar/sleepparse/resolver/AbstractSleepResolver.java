package com.leanstar.sleepparse.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.leanstar.sleepparse.domain.MyBase64;
import com.leanstar.sleepparse.iterator.Iterator;
import com.leanstar.sleepparse.util.DataConverterUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析器的抽象类
 */
public abstract class AbstractSleepResolver implements Resolver {

    String[][] attributeInfos;

    protected ObjectNode resolveSleepNodeUpload(MyBase64 target) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode data = objectMapper.createObjectNode();
        ObjectNode objectNode = null;
        List<ObjectNode> list = new ArrayList();
        int beginStamp = 0;
        int dataLength = 0;
        String tempStr = "";
        int cursor = 0;
        Iterator iterator = target.iterator();

        while (iterator.hasNext() && cursor++ < 4) {
            tempStr = iterator.next() + tempStr;
        }
        beginStamp = Integer.valueOf(DataConverterUtil.binaryToStr(tempStr));

        tempStr = "";
        cursor = 0;
        while (iterator.hasNext() && cursor++ < 2) {
            tempStr = iterator.next() + tempStr;
        }
        dataLength = Integer.valueOf(DataConverterUtil.binaryToStr(tempStr));
        for (int i = 1; i <= dataLength; i += attributeInfos.length) {
            objectNode = objectMapper.createObjectNode();
            objectNode.put("dateTime", beginStamp + (i / attributeInfos.length) * 60);
            for (int j = 0; j < attributeInfos.length; j++) {
                tempStr = "";
                cursor = 0;
                int byteLength = 0;
                if (!attributeInfos[j][1].contains("|")) {
                    byteLength = Integer.valueOf(attributeInfos[j][1]);
                } else {
                    String[] values = attributeInfos[j][1].split("\\|");
                    for (int p = 0; p < values.length; p++) {
                        byteLength += Integer.valueOf(values[p]);
                    }
                    byteLength = byteLength / 8;
                }
                while (iterator.hasNext() && cursor++ < byteLength) {
                    tempStr = iterator.next() + tempStr;
                }
                if (!attributeInfos[j][0].contains("|")) {
                    objectNode.put(attributeInfos[j][0], DataConverterUtil.binaryToInt(tempStr));
                } else {
                    String[] keys = attributeInfos[j][0].split("\\|");
                    String[] values = attributeInfos[j][1].split("\\|");
                    int tempTotal = 0;
                    for (int p = 0; p < keys.length; p++) {
                        objectNode.put(keys[p], DataConverterUtil.binaryToInt(tempStr.substring(tempTotal, Integer.valueOf(values[p]) + tempTotal)));
                        tempTotal = Integer.valueOf(values[p]) + tempTotal;
                    }
                }
            }
            list.add(objectNode);
        }

        data.put("id", target.getKey());
        data.putPOJO("content", (Object) list);

        return data;
    }

    protected ObjectNode resolveSleepReport(MyBase64 target, int scoreDetailLastIndex) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode data = objectMapper.createObjectNode();
        ObjectNode content = objectMapper.createObjectNode();
        ObjectNode objectNode1 = objectMapper.createObjectNode();
        ObjectNode objectNode2 = objectMapper.createObjectNode();
        Iterator iterator = target.iterator();
        for (int i = 0; i < attributeInfos.length; i++) {
            int currIndex = 0;
            String tempStr = "";
            while (iterator.hasNext()) {
                tempStr = iterator.next() + tempStr;
                if (++currIndex >= Integer.valueOf(attributeInfos[i][1])) {
                    if (i <= scoreDetailLastIndex) {
                        objectNode1.put(attributeInfos[i][0], DataConverterUtil.binaryToInt(tempStr));
                    } else {
                        objectNode2.put(attributeInfos[i][0], DataConverterUtil.binaryToInt(tempStr));
                    }
                    break;
                }
            }
        }
        content.putPOJO("scoreDetail", objectNode1);
        content.putPOJO("reportStatistics", enrichReportStatistics(objectNode2));

        data.put("id", target.getKey());
        data.putPOJO("content", content);

        return data;
    }

    private ObjectNode enrichReportStatistics(ObjectNode objectNode2) {
        objectNode2.put("dormantSleepRatio", objectNode2.get("sleepRatio").asInt() - objectNode2.get("middleSleepRatio").asInt() - objectNode2.get("deepSleepRatio").asInt());
        objectNode2.put("awakeRatio", 100 - objectNode2.get("sleepRatio").asInt());
        return objectNode2;
    }

    public abstract ObjectNode resolve(MyBase64 target);

}
