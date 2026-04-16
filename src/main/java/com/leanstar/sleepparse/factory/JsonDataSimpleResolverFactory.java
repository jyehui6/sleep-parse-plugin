package com.leanstar.sleepparse.factory;

import com.leanstar.sleepparse.constant.HexCodeEnum;
import com.leanstar.sleepparse.resolver.*;

public class JsonDataSimpleResolverFactory {
    public static JsonDataResolver createResolver(int id){
        if(HexCodeEnum.T_0x5E.getCode()==id){
            return new ReportCycleResolver();
        }else if(HexCodeEnum.T_0x5F.getCode()==id){
            return new MqttSetResolver();
        }else if(HexCodeEnum.T_0x61.getCode()==id){
            return new SensiResolver();
        }else if(HexCodeEnum.T_0x62.getCode()==id){
            return new OtaUpgradeResolver();
        }else if(HexCodeEnum.T_0x66.getCode()==id){
            return new SemaphoreResolver();
        }
        return null;
    }

}
