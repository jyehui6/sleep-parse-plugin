package com.leanstar.sleepparse.resolver;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 自定义son解析器的抽象接口
 */
public interface JsonDataResolver {

    ObjectNode resolve(String data);

}
