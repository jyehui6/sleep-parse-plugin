package com.leanstar.sleepparse.factory;

import com.leanstar.sleepparse.constant.DataType;
import com.leanstar.sleepparse.constant.ProductType;
import com.leanstar.sleepparse.domain.MyBase64;
import com.leanstar.sleepparse.resolver.*;
import com.leanstar.sleepparse.util.ProductPayloadDetector;
import com.leanstar.sleepparse.util.ProductPayloadDetector.SleepNodeVariant;

/**
 * 解析器简单工厂
 * 返回数据类型对应的正确解析器
 */
public class SimpleResolverFactory {

    public static Resolver createResolver(String dataTypeStr, MyBase64 payload) {
        return createResolver(dataTypeStr, payload, null);
    }

    public static Resolver createResolver(String dataTypeStr, MyBase64 payload, ProductType productType) {
        if (DataType.T_0x41.value().equals(dataTypeStr)) {
            return new RealTimeMonitorResolver();
        } else if (DataType.T_0x42.value().equals(dataTypeStr)) {
            return createSleepNodeResolver(payload, productType);
        } else if (DataType.T_0x43.value().equals(dataTypeStr)) {
            switch (ProductPayloadDetector.detectSleepReportVariant(payload)) {
                case SMD:
                    return new SleepReportDataResolver2();
                case PILLOW_LITE:
                    return new SleepReportDataPillowLiteResolver();
                case PILLOW_FULL:
                default:
                    return new SleepReportDataResolver();
            }
        } else if (DataType.T_0x44.value().equals(dataTypeStr) || DataType.T_0x45.value().equals(dataTypeStr) || DataType.T_0x5C.value().equals(dataTypeStr)) {
            return new OneByteNumberResolver();
        } else if (DataType.T_0x46.value().equals(dataTypeStr) || DataType.T_0x47.value().equals(dataTypeStr) || DataType.T_0x48.value().equals(dataTypeStr)
                || DataType.T_0x49.value().equals(dataTypeStr) || DataType.T_0x4A.value().equals(dataTypeStr) || DataType.T_0x4B.value().equals(dataTypeStr)
                || DataType.T_0x4C.value().equals(dataTypeStr) || DataType.T_0x5D.value().equals(dataTypeStr)) {
            return new StringTransformResolver();
        }
        return null;
    }

    private static Resolver createSleepNodeResolver(MyBase64 payload, ProductType productType) {
        if (productType != null) {
            return toSleepNodeResolver(ProductType.T_PILLOW.equals(productType) ? SleepNodeVariant.PILLOW : SleepNodeVariant.SMD);
        }
        return toSleepNodeResolver(ProductPayloadDetector.detectSleepNodeVariant(payload));
    }

    private static Resolver toSleepNodeResolver(SleepNodeVariant variant) {
        return variant == SleepNodeVariant.PILLOW
                ? new SleepDataUploadResolver()
                : new SleepDataUploadResolver2();
    }

}
