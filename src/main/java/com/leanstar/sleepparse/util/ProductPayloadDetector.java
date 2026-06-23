package com.leanstar.sleepparse.util;

import com.leanstar.sleepparse.domain.MyBase64;
import com.leanstar.sleepparse.iterator.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根据协议载荷特征自动识别数据格式。
 * 0x42 参见协议 4.3 及注 4.3；0x43 参见协议 4.2。
 */
public final class ProductPayloadDetector {

    private static final Logger logger = LoggerFactory.getLogger(ProductPayloadDetector.class);

    private static final int SLEEP_NODE_HEADER_BYTES = 6;
    private static final int SLEEP_REPORT_SMD_BYTES = 27;
    private static final int SLEEP_REPORT_PILLOW_LITE_BYTES = 29;
    private static final int SLEEP_REPORT_PILLOW_FULL_BYTES = 30;

    public enum SleepNodeVariant {
        PILLOW,
        SMD
    }

    public enum SleepReportVariant {
        SMD,
        PILLOW_LITE,
        PILLOW_FULL
    }

    private ProductPayloadDetector() {
    }

    /**
     * 自动识别 0x42 节点数据格式；歧义时默认睡眠枕。
     */
    public static SleepNodeVariant detectSleepNodeVariant(MyBase64 target) {
        int totalBytes = countBytes(target);
        if (totalBytes < SLEEP_NODE_HEADER_BYTES) {
            return SleepNodeVariant.PILLOW;
        }
        int dataLength = readUInt16LE(target, 4);
        int bodyLen = totalBytes - SLEEP_NODE_HEADER_BYTES;
        if (bodyLen != dataLength) {
            logger.warn("0x42 节点数据长度校验不一致，声明长度={}，实际长度={}", dataLength, bodyLen);
        }
        boolean divisibleBy4 = dataLength % 4 == 0;
        boolean divisibleBy5 = dataLength % 5 == 0;
        if (divisibleBy5 && !divisibleBy4) {
            return SleepNodeVariant.PILLOW;
        }
        if (divisibleBy4 && !divisibleBy5) {
            return SleepNodeVariant.SMD;
        }
        if (divisibleBy4) {
            logger.warn("0x42 dataLength={} 无法自动区分枕/带，默认按睡眠枕解析；可传入 ProductType 指定", dataLength);
        }
        return SleepNodeVariant.PILLOW;
    }

    public static SleepReportVariant detectSleepReportVariant(MyBase64 target) {
        int totalBytes = countBytes(target);
        if (totalBytes == SLEEP_REPORT_SMD_BYTES) {
            return SleepReportVariant.SMD;
        }
        if (totalBytes == SLEEP_REPORT_PILLOW_LITE_BYTES) {
            return SleepReportVariant.PILLOW_LITE;
        }
        if (totalBytes == SLEEP_REPORT_PILLOW_FULL_BYTES) {
            return SleepReportVariant.PILLOW_FULL;
        }
        if (totalBytes < SLEEP_REPORT_PILLOW_LITE_BYTES) {
            return SleepReportVariant.SMD;
        }
        if (totalBytes < SLEEP_REPORT_PILLOW_FULL_BYTES) {
            return SleepReportVariant.PILLOW_LITE;
        }
        return SleepReportVariant.PILLOW_FULL;
    }

    private static int countBytes(MyBase64 target) {
        Iterator iterator = target.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    private static int readUInt16LE(MyBase64 target, int offsetBytes) {
        Iterator iterator = target.iterator();
        StringBuilder bits = new StringBuilder();
        int needBytes = offsetBytes + 2;
        for (int i = 0; i < needBytes && iterator.hasNext(); i++) {
            bits.append(iterator.next());
        }
        String lowByte = bits.substring(offsetBytes * 8, offsetBytes * 8 + 8);
        String highByte = bits.substring((offsetBytes + 1) * 8, (offsetBytes + 1) * 8 + 8);
        return DataConverterUtil.binaryToInt(lowByte)
                | (DataConverterUtil.binaryToInt(highByte) << 8);
    }

}
