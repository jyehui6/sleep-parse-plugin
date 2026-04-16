package com.leanstar.sleepparse.resolver;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.leanstar.sleepparse.domain.MyBase64;

/**
 * 解析器的抽象类
 */
public abstract class AbstractSleepResolver implements Resolver {

    String[][] attributeInfos;

    public abstract ObjectNode resolve(MyBase64 target);

}
