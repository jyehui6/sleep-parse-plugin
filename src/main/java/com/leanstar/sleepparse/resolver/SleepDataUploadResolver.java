package com.leanstar.sleepparse.resolver;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.leanstar.sleepparse.domain.MyBase64;

/**
 * 0x42 睡眠节点数据解析器
 */
public class SleepDataUploadResolver extends AbstractSleepResolver {

    private static final String[][] PILLOW_ATTRIBUTE_INFOS = {
            {"heartRate", "1"},
            {"respiratoryRate", "1"},
            {"breathStop", "1"},
            {"bodyMoveTotal|sleepState", "5|3"},
            {"antiSnoreTotal|snoreTotal", "2|6"},
    };

    private static final String[][] SMD_ATTRIBUTE_INFOS = {
            {"heartRate", "1"},
            {"respiratoryRate", "1"},
            {"breathStop", "1"},
            {"bodyMoveTotal|sleepState", "5|3"},
    };

    public SleepDataUploadResolver() {
        this(PILLOW_ATTRIBUTE_INFOS);
    }

    private SleepDataUploadResolver(String[][] attributeInfos) {
        this.attributeInfos = attributeInfos;
    }

    public static SleepDataUploadResolver pillow() {
        return new SleepDataUploadResolver(PILLOW_ATTRIBUTE_INFOS);
    }

    public static SleepDataUploadResolver smd() {
        return new SleepDataUploadResolver(SMD_ATTRIBUTE_INFOS);
    }

    @Override
    public ObjectNode resolve(MyBase64 target) {
        return resolveSleepNodeUpload(target);
    }

}
