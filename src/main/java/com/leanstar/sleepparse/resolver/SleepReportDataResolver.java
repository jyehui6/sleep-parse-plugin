package com.leanstar.sleepparse.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.leanstar.sleepparse.domain.MyBase64;
import com.leanstar.sleepparse.iterator.Iterator;
import com.leanstar.sleepparse.util.DataConverterUtil;

/**
 * 0x43数据类型的解析器
 * 睡眠报告
 */
public class SleepReportDataResolver extends AbstractSleepResolver {

    public SleepReportDataResolver(){
        attributeInfos = new String[10 + 15][2];

        attributeInfos[0][0] = "sleepScore";
        attributeInfos[0][1] = "1";

        attributeInfos[1][0] = "bodyMoveDeduction";
        attributeInfos[1][1] = "1";

        attributeInfos[2][0] = "lastSleepDeduction";
        attributeInfos[2][1] = "1";

        attributeInfos[3][0] = "easyAwakeDeduction";
        attributeInfos[3][1] = "1";

        attributeInfos[4][0] = "speedyDeduction";
        attributeInfos[4][1] = "1";

        attributeInfos[5][0] = "sleepShort";
        attributeInfos[5][1] = "1";

        attributeInfos[6][0] = "sleepLong";
        attributeInfos[6][1] = "1";

        attributeInfos[7][0] = "breathStop";
        attributeInfos[7][1] = "1";

        attributeInfos[8][0] = "breathException";
        attributeInfos[8][1] = "1";

        /**
         * 新增打鼾扣分 一下依次递增
         */
        attributeInfos[9][0] = "snore";
        attributeInfos[9][1] = "1";

        attributeInfos[10][0] = "beginStamp";
        attributeInfos[10][1] = "4";

        attributeInfos[11][0] = "reportDuration";
        attributeInfos[11][1] = "2";

        attributeInfos[12][0] = "inBedDuration";
        attributeInfos[12][1] = "2";

        attributeInfos[13][0] = "heartRateAverage";
        attributeInfos[13][1] = "1";

        attributeInfos[14][0] = "respiratoryRateAverage";
        attributeInfos[14][1] = "1";

        attributeInfos[15][0] = "bodyMoveTotal";
        attributeInfos[15][1] = "1";

        attributeInfos[16][0] = "sleepRatio";
        attributeInfos[16][1] = "1";

        attributeInfos[17][0] = "middleSleepRatio";
        attributeInfos[17][1] = "1";

        attributeInfos[18][0] = "deepSleepRatio";
        attributeInfos[18][1] = "1";

        attributeInfos[19][0] = "awakeTimes";
        attributeInfos[19][1] = "1";

        attributeInfos[20][0] = "breathStop";
        attributeInfos[20][1] = "1";

        attributeInfos[21][0] = "breathException";
        attributeInfos[21][1] = "1";

        attributeInfos[22][0] = "nightSleepFlag";
        attributeInfos[22][1] = "1";

        attributeInfos[23][0] = "snoreRatio";
        attributeInfos[23][1] = "1";

        attributeInfos[24][0] = "antiSnoreTotal";
        attributeInfos[24][1] = "1";

    }

    @Override
    public ObjectNode resolve(MyBase64 target){
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode data = objectMapper.createObjectNode();
//        List<ObjectNode> list = new ArrayList();
        ObjectNode content = objectMapper.createObjectNode();
        ObjectNode objectNode1 = objectMapper.createObjectNode();
        ObjectNode objectNode2 = objectMapper.createObjectNode();
        Iterator iterator = target.iterator();
        for(int i = 0; i < attributeInfos.length; i++){
            int currIndex = 0;
            String tempStr = "";
            while(iterator.hasNext()){
                tempStr = iterator.next() + tempStr;
                if(++currIndex >= Integer.valueOf(attributeInfos[i][1])){
                    if(isInObjectNode1(i)){
                        objectNode1.put(attributeInfos[i][0], DataConverterUtil.binaryToInt(tempStr));
                    }else{
                        objectNode2.put(attributeInfos[i][0], DataConverterUtil.binaryToInt(tempStr));
                    }
                    break;
                }
            }
        }
//        list.add(objectNode1);
//        list.add(addAttributeValueToObjectNode2(objectNode2));
        content.putPOJO("scoreDetail", objectNode1);
        content.putPOJO("reportStatistics", addAttributeValueToObjectNode2(objectNode2));

        data.put("id", target.getKey());
//        data.putPOJO("content", (Object)list);
        data.putPOJO("content", content);


        return data;
    }

    private boolean isInObjectNode1(int cursor){
        return cursor <= 9;
    }

    private ObjectNode addAttributeValueToObjectNode2(ObjectNode objectNode2){
        objectNode2.put("dormantSleepRatio", objectNode2.get("sleepRatio").asInt() - objectNode2.get("middleSleepRatio").asInt() - objectNode2.get("deepSleepRatio").asInt());
        objectNode2.put("awakeRatio", 100 - objectNode2.get("sleepRatio").asInt());
        return objectNode2;
    }

}
