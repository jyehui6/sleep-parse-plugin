package com.leanstar.sleepparse.util;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.leanstar.sleepparse.constant.HexCodeEnum;
import com.leanstar.sleepparse.domain.MyBase64;
import com.leanstar.sleepparse.exception.BusinessException;
import com.leanstar.sleepparse.factory.JsonDataSimpleResolverFactory;
import com.leanstar.sleepparse.factory.SimpleResolverFactory;
import com.leanstar.sleepparse.resolver.JsonDataResolver;
import com.leanstar.sleepparse.resolver.Resolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

/**
 * 睡眠数据解析工具类
 * 提供静态方法来解析睡眠数据，方便客户直接调用
 */
public class SleepDataParserUtil {

    private static final Logger logger = LoggerFactory.getLogger(SleepDataParserUtil.class);

    /**
     * 解析睡眠数据
     * @param data 待解析的数据，JSON格式的字符串
     * @return 解析结果，ObjectNode类型
     */
    public static ObjectNode parse(String data) {
        try {
            return decode(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("睡眠数据解析工具-编码异常：{}", e.getMessage());
            return null;
        }
    }

    /**
     * 解析睡眠数据
     */
    private static ObjectNode decode(byte[] bytes) {
        ObjectNode result = null;
        ObjectNode objectNode = null;
        String key = null;
        String value = null;
        try {
            String msg = new String(bytes, "UTF-8");
            Map<String, Object> map = JsonUtil.json2map(msg);
            // 0x5E数据类型的解析器之后，数据格式发生变化,传递数据为json
            if (map.containsKey("id")) {
                int id = ((Number) map.get("id")).intValue();
                JsonDataResolver resolver = JsonDataSimpleResolverFactory.createResolver(id);
                objectNode = resolver.resolve(msg);
                key = HexCodeEnum.getEnumByCode(id).getHexcode();
            } else {
                // 0x5D之前的数据类型解析
                Iterator<Map.Entry<String, Object>> iteratorMap = map.entrySet().iterator();
                while (iteratorMap.hasNext()) {
                    Map.Entry<String, Object> entry = iteratorMap.next();
                    key = entry.getKey();
                    value = String.valueOf(entry.getValue());
                }
                Resolver resolver = SimpleResolverFactory.createResolver(key);
                if (resolver == null) {
                    logger.error("睡眠数据解析工具-未找到合适类型解析器-{}，{}", msg, key);
                    return null;
                }
                MyBase64 myBase64 = null;
                if (key.equals("0x5D")) {
                    myBase64 = new MyBase64(value, key);
                } else {
                    myBase64 = new MyBase64(MyBase64Util.removeIllegalChar(value), key);
                }
                objectNode = resolver.resolve(myBase64);
            }
            result = objectNode;
        } catch (BusinessException e) {
            logger.info("睡眠数据解析工具-业务异常：{}，{}，{}", e.getCode(), e.getMsg(), e.getDesc());
            return null;
        } catch (Exception e) {
            logger.info("睡眠数据解析工具-接收到的异常：{}", e.getMessage());
            return null;
        }
        return result;
    }
}
