package com.leanstar.sleepparse.factory;

import com.leanstar.sleepparse.constant.DataType;
import com.leanstar.sleepparse.resolver.*;

/**
 * 解析器简单工厂
 * 返回数据类型对应的正确解析器
 */
public class SimpleResolverFactory {

    public static Resolver createResolver(String dataTypeStr){
        if(DataType.T_0x41.value().equals(dataTypeStr)){
            return new RealTimeMonitorResolver();
        }else if(DataType.T_0x42.value().equals(dataTypeStr)){
            return new SleepDataUploadResolver();
        }else if (DataType.T_0x43.value().equals(dataTypeStr)){
            return new SleepReportDataResolver();
        }else if (DataType.T_0x44.value().equals(dataTypeStr) || DataType.T_0x45.value().equals(dataTypeStr)|| DataType.T_0x5C.value().equals(dataTypeStr)){
            return new OneByteNumberResolver();
        }else if (DataType.T_0x46.value().equals(dataTypeStr) || DataType.T_0x47.value().equals(dataTypeStr) || DataType.T_0x48.value().equals(dataTypeStr)
                  || DataType.T_0x49.value().equals(dataTypeStr) || DataType.T_0x4A.value().equals(dataTypeStr) || DataType.T_0x4B.value().equals(dataTypeStr)
                  || DataType.T_0x4C.value().equals(dataTypeStr)|| DataType.T_0x5D.value().equals(dataTypeStr)||DataType.T_0x66.value().equals(dataTypeStr)){
            return new StringTransformResolver();
        }
        return null;
    }

}
