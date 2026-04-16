package com.leanstar.sleepparse.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.leanstar.sleepparse.domain.MyBase64;
import com.leanstar.sleepparse.iterator.Iterator;
import com.leanstar.sleepparse.util.DataConverterUtil;

/**
 * Base64单字节解析器
 */
public class OneByteNumberResolver extends AbstractSleepResolver {

    @Override
    public ObjectNode resolve(MyBase64 target) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode data = objectMapper.createObjectNode();

        Iterator iterator = target.iterator();
        if(iterator.hasNext()){
            data.put("id", target.getKey());
            data.put("content", DataConverterUtil.binaryToInt(String.valueOf(iterator.next())));
        }

        return data;
    }

}
