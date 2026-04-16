package com.leanstar.sleepparse.resolver;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.leanstar.sleepparse.domain.MyBase64;

/**
 * 自定义解析器的抽象接口
 */
public interface Resolver {

    ObjectNode resolve(MyBase64 target);


}
