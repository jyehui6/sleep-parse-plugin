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
        }else if(HexCodeEnum.T_0x63.getCode()==id){
            return new PillowHeadPositionResolver();
        }else if(HexCodeEnum.T_0x64.getCode()==id){
            return new PillowSettingAirBagResolver();
        }else if(HexCodeEnum.T_0x65.getCode()==id){
            return new PillowSettingAnitSnoringResolver();
        }else if(HexCodeEnum.T_0x66.getCode()==id){
            return new SemaphoreResolver();
        }else if(HexCodeEnum.T_0x67.getCode()==id){
            return new AlarmMessageResolver();
        }else if(HexCodeEnum.T_0x68.getCode()==id){
            return new AlarmSettingResolver();
        }else if(HexCodeEnum.T_0x69.getCode()==id){
            return new RealTimeInstallmentResolver();
        }
        return null;
    }

}
