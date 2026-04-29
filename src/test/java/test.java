import com.leanstar.sleepparse.util.SleepDataParserUtil;

/**
 * 测试
 */
public class test {
    public static void main(String[] args) {
        try{
            // 数据类型示例
            String new41 = "{\"0x41\":\"QBEB\"}";
            String new42 = "{\"0x42\": \"DEC8YAoAQQ0AAUJCDgABQw==\"}";
            String new43 = "{\"0x43\":\"QwoCCgYAAAAFAGSeHmaSAZIBNw7+Xj4FDQAFAQMN\"}";
            String new99 = "{\n" +
                    "\"id\":99,\n" +
                    "\"position\":[1,0,0,1,0,0]\n" +
                    "}";
            String new100 = "{\n" +
                    "\"id\":100,\n" +
                    "\"ChargTime\":[1,2,3,4,5],\n" +
                    "\"RelsTime\":[1,2,3,4,5]\n" +
                    "}";
            String new101 = "{\n" +
                    "\"id\":101,\n" +
                    "\"AnitSnorSw\": [1, 1, 1, 1,1],\n" +
                    "\"AnitSnorSta\": [0, 1, 2, 3,3]\n" +
                    "}";
            String new102 = "{\n" +
                    "  \"id\": 102,\n" +
                    "  \"timestamp\": 1777282796,\n" +
                    "  \"vpp\": [\n" +
                    "    9,\n" +
                    "    8,\n" +
                    "    2014,\n" +
                    "    2053,\n" +
                    "    2,\n" +
                    "    2,\n" +
                    "    2033,\n" +
                    "    2039\n" +
                    "  ],\n" +
                    "  \"hr\": 669,\n" +
                    "  \"br\": 2,\n" +
                    "  \"gain\": 3,\n" +
                    "  \"rr_o\": 0,\n" +
                    "  \"slp_stag\": 1\n" +
                    "}";
            String new103 = "{\"id\":103,\"HrAlarm\":0,\"RrAlarm\":0}";
            String new104 = "{\n" +
                    "\"id\":104,\n" +
                    "\"HrDelayTime\":0,\n" +
                    "\"RrDelayTime\":0,\n" +
                    "\"HrWnSw\":0,\n" +
                    "\"RrWnSw\":0,\n" +
                    "\"HrOkRang\":[50,80],\n" +
                    "\"RrOkRang\":[8,20]\n" +
                    "}";
            String new105 = "{\n" +
                    "\"id\":105,\n" +
                    "\"Node\":[60,12,0,2,0]\n" +
                    "}";
            // 调用解析方法
            System.out.println("0x41类型解析结果: " + SleepDataParserUtil.parse(new41).toString());
            System.out.println("0x42类型解析结果: " + SleepDataParserUtil.parse(new42).toString());
            System.out.println("0x43类型解析结果: " + SleepDataParserUtil.parse(new43).toString());
            System.out.println("99类型解析结果: " + SleepDataParserUtil.parse(new99).toString());
            System.out.println("100类型解析结果: " + SleepDataParserUtil.parse(new100).toString());
            System.out.println("101类型解析结果: " + SleepDataParserUtil.parse(new101).toString());
            System.out.println("102类型解析结果: " + SleepDataParserUtil.parse(new102).toString());
            System.out.println("103类型解析结果: " + SleepDataParserUtil.parse(new103).toString());
            System.out.println("104类型解析结果: " + SleepDataParserUtil.parse(new104).toString());
            System.out.println("105类型解析结果: " + SleepDataParserUtil.parse(new105).toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
