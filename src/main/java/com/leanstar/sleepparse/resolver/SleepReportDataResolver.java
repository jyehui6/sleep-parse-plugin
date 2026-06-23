package com.leanstar.sleepparse.resolver;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.leanstar.sleepparse.domain.MyBase64;

/**
 * 0x43 睡眠报告解析器
 */
public class SleepReportDataResolver extends AbstractSleepResolver {

    private static final String[][] PILLOW_FULL_ATTRIBUTE_INFOS = {
            {"sleepScore", "1"},
            {"bodyMoveDeduction", "1"},
            {"lastSleepDeduction", "1"},
            {"easyAwakeDeduction", "1"},
            {"speedyDeduction", "1"},
            {"sleepShort", "1"},
            {"sleepLong", "1"},
            {"breathStop", "1"},
            {"breathException", "1"},
            {"snore", "1"},
            {"beginStamp", "4"},
            {"reportDuration", "2"},
            {"inBedDuration", "2"},
            {"heartRateAverage", "1"},
            {"respiratoryRateAverage", "1"},
            {"bodyMoveTotal", "1"},
            {"sleepRatio", "1"},
            {"middleSleepRatio", "1"},
            {"deepSleepRatio", "1"},
            {"awakeTimes", "1"},
            {"breathStop", "1"},
            {"breathException", "1"},
            {"nightSleepFlag", "1"},
            {"snoreRatio", "1"},
            {"antiSnoreTotal", "1"},
    };

    private static final String[][] PILLOW_LITE_ATTRIBUTE_INFOS = {
            {"sleepScore", "1"},
            {"bodyMoveDeduction", "1"},
            {"lastSleepDeduction", "1"},
            {"easyAwakeDeduction", "1"},
            {"speedyDeduction", "1"},
            {"sleepShort", "1"},
            {"sleepLong", "1"},
            {"breathStop", "1"},
            {"breathException", "1"},
            {"snore", "1"},
            {"beginStamp", "4"},
            {"reportDuration", "2"},
            {"inBedDuration", "2"},
            {"heartRateAverage", "1"},
            {"respiratoryRateAverage", "1"},
            {"bodyMoveTotal", "1"},
            {"sleepRatio", "1"},
            {"middleSleepRatio", "1"},
            {"deepSleepRatio", "1"},
            {"awakeTimes", "1"},
            {"breathStop", "1"},
            {"breathException", "1"},
            {"nightSleepFlag", "1"},
            {"snoreRatio", "1"},
    };

    private static final String[][] SMD_ATTRIBUTE_INFOS = {
            {"sleepScore", "1"},
            {"bodyMoveDeduction", "1"},
            {"lastSleepDeduction", "1"},
            {"easyAwakeDeduction", "1"},
            {"speedyDeduction", "1"},
            {"sleepShort", "1"},
            {"sleepLong", "1"},
            {"breathStop", "1"},
            {"breathException", "1"},
            {"beginStamp", "4"},
            {"reportDuration", "2"},
            {"inBedDuration", "2"},
            {"heartRateAverage", "1"},
            {"respiratoryRateAverage", "1"},
            {"bodyMoveTotal", "1"},
            {"sleepRatio", "1"},
            {"middleSleepRatio", "1"},
            {"deepSleepRatio", "1"},
            {"awakeTimes", "1"},
            {"breathStop", "1"},
            {"breathException", "1"},
            {"nightSleepFlag", "1"},
    };

    private final int scoreDetailLastIndex;

    public SleepReportDataResolver() {
        this(PILLOW_FULL_ATTRIBUTE_INFOS, 9);
    }

    private SleepReportDataResolver(String[][] attributeInfos, int scoreDetailLastIndex) {
        this.attributeInfos = attributeInfos;
        this.scoreDetailLastIndex = scoreDetailLastIndex;
    }

    public static SleepReportDataResolver pillowFull() {
        return new SleepReportDataResolver(PILLOW_FULL_ATTRIBUTE_INFOS, 9);
    }

    public static SleepReportDataResolver pillowLite() {
        return new SleepReportDataResolver(PILLOW_LITE_ATTRIBUTE_INFOS, 9);
    }

    public static SleepReportDataResolver smd() {
        return new SleepReportDataResolver(SMD_ATTRIBUTE_INFOS, 8);
    }

    @Override
    public ObjectNode resolve(MyBase64 target) {
        return resolveSleepReport(target, scoreDetailLastIndex);
    }

}
