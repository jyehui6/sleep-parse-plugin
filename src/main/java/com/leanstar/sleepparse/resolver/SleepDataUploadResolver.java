package com.leanstar.sleepparse.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.leanstar.sleepparse.domain.MyBase64;
import com.leanstar.sleepparse.iterator.Iterator;
import com.leanstar.sleepparse.util.DataConverterUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 0x42数据类型的解析器
 * 睡眠数据
 */
public class SleepDataUploadResolver extends AbstractSleepResolver {

    public SleepDataUploadResolver(){
        // 创建一个二维数组,用来存储属性信息
        attributeInfos = new String[5][2];

        attributeInfos[0][0] = "heartRate";
        attributeInfos[0][1] = "1";

        attributeInfos[1][0] = "respiratoryRate";
        attributeInfos[1][1] = "1";

        attributeInfos[2][0] = "breathStop";
        attributeInfos[2][1] = "1";

        attributeInfos[3][0] = "bodyMoveTotal|sleepState";
        attributeInfos[3][1] = "5|3";

        attributeInfos[4][0] = "antiSnoreTotal|snoreTotal";
        attributeInfos[4][1] = "2|6";


    }

    @Override
    public ObjectNode resolve(MyBase64 target){
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode data = objectMapper.createObjectNode();
        ObjectNode objectNode = null;
        List<ObjectNode> list = new ArrayList();
        int beginStamp = 0;
        int dataLength = 0;
        String tempStr = "";
        int cursor = 0;
        Iterator iterator = target.iterator();

        while(iterator.hasNext() && cursor++ < 4){
            tempStr = iterator.next() + tempStr;
        }
        beginStamp = Integer.valueOf(DataConverterUtil.binaryToStr(tempStr));

        tempStr = "";
        cursor = 0;
        while(iterator.hasNext() && cursor++ < 2){
            tempStr = iterator.next() + tempStr;
        }
        dataLength = Integer.valueOf(DataConverterUtil.binaryToStr(tempStr));
        for(int i = 1; i <= dataLength; i += attributeInfos.length){
            objectNode = objectMapper.createObjectNode();
            objectNode.put("dateTime", beginStamp + (i / attributeInfos.length) * 60);
            for(int j = 0; j < attributeInfos.length; j++){
                tempStr = "";
                cursor = 0;
                int byteLength = 0;
                if(!attributeInfos[j][1].contains("|")){
                    byteLength = Integer.valueOf(attributeInfos[j][1]);
                }else{
                    String[] values = attributeInfos[j][1].split("\\|");
                    for(int p = 0; p < values.length; p++){
                        byteLength += Integer.valueOf(values[p]);
                    }
                    byteLength = byteLength / 8;
                }
                while(iterator.hasNext() && cursor++ < byteLength){
                    tempStr = iterator.next() + tempStr;
                }
                if(!attributeInfos[j][0].contains("|")){
                    objectNode.put(attributeInfos[j][0], DataConverterUtil.binaryToInt(tempStr));
                }else{
                    String[] keys = attributeInfos[j][0].split("\\|");
                    String[] values = attributeInfos[j][1].split("\\|");
                    int tempTotal = 0;
                    for(int p = 0; p < keys.length; p++){
                        objectNode.put(keys[p], DataConverterUtil.binaryToInt(tempStr.substring(tempTotal, Integer.valueOf(values[p]) + tempTotal)));
                        tempTotal = Integer.valueOf(values[p]) + tempTotal;
                    }
                }
            }
            list.add(objectNode);
        }

        data.put("id", target.getKey());
        data.putPOJO("content", (Object)list);

        return data;
    }

}
