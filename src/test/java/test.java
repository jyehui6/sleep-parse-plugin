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

            // 调用解析方法
            System.out.println("0x41类型解析结果: " + SleepDataParserUtil.parse(new41).toString());
            System.out.println("0x42类型解析结果: " + SleepDataParserUtil.parse(new42).toString());
            System.out.println("0x43类型解析结果: " + SleepDataParserUtil.parse(new43).toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
