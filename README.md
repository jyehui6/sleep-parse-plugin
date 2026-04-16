# Sleep Analysis Plugin

## 项目简介

Sleep Analysis Plugin 是一个专门用于解析能斯达智能睡眠监测产品设备睡眠数据的工具类库，提供了简洁的静态方法来解析不同格式的睡眠数据，方便客户直接调用。

## 功能特性

- 支持解析特定协议的睡眠数据
- 自动适配不同类型的睡眠数据格式

## 环境

- Java 1.8+
- Maven 3.8+

## 安装方法

### Maven 依赖

```xml
<dependency>
    <groupId>io.github.jyehui6</groupId>
    <artifactId>sleep-analysis-plugin</artifactId>
    <version>1.0.0.RELEASE</version>
</dependency>
```

### 本地构建

1. 克隆项目到本地
   ```bash
   git clone https://github.com/jyehui6/sleep-analysis-plugin.git
   cd sleep-analysis-plugin
   ```

2. 构建项目
   ```bash
   mvn clean install
   ```
   

## 项目结构

```
sleep-analysis-plugin/
├── src/
│   ├── main/java/com/leanstar/sleepparse/
│   │   ├── constant/         # 常量定义
│   │   ├── domain/           # 领域模型
│   │   ├── exception/        # 异常类
│   │   ├── factory/          # 解析器工厂
│   │   ├── iterator/         # 迭代器
│   │   ├── resolver/         # 数据解析器
│   │   └── util/             # 工具类
│   └── test/                 # 测试代码
├── .gitignore               # Git 忽略文件
├── pom.xml                  # Maven 配置文件
└── README.md                # 项目说明文档
```

## 核心工具类

### SleepDataParserUtil

提供静态方法 `parse` 用于解析睡眠数据：

- `parse(String data)`: 解析 JSON 格式的字符串数据
- `parse(byte[] bytes)`: 解析字节数组形式的数据

## 使用示例

### 解析0x41实时体征数据

```java
import com.leanstar.sleepparse.util.SleepDataParserUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Example {
    public static void main(String[] args) {
        // 示例 0x41 实时体征数据
        String sleepData = "{\"0x41\":\"QBEB\"}";
        
        // 解析数据
        ObjectNode result = SleepDataParserUtil.parse(sleepData);
        
        // 输出解析结果
        System.out.println("解析结果: " + result.toString());
        
        // 解析结果: {"id":"0x41","content":{"heartRate":64,"respiratoryRate":17,"sleepState":1}}
    }
}
```
### 解析0x42睡眠数据

```java
import com.leanstar.sleepparse.util.SleepDataParserUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Example {
    public static void main(String[] args) {
        // 示例 0x42 睡眠数据
        String sleepData = "{\"0x42\":\"DEC8YAoAQQ0AAUJCDgABQw==\"}";
        
        // 解析数据
        ObjectNode result = SleepDataParserUtil.parse(sleepData);
        
        // 输出解析结果
        System.out.println("解析结果: " + result.toString());
        
        // 解析结果: {"id":"0x42","content":[{"dateTime":1622949900,"heartRate":65,"respiratoryRate":13,"breathStop":0,"bodyMoveTotal":0,"sleepState":1,"antiSnoreTotal":1,"snoreTotal":2}, {"dateTime":1622949960,"heartRate":66,"respiratoryRate":14,"breathStop":0,"bodyMoveTotal":0,"sleepState":1,"antiSnoreTotal":1,"snoreTotal":3}]}
    }
}
```
### 解析0x43睡眠报告

```java
import com.leanstar.sleepparse.util.SleepDataParserUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Example {
    public static void main(String[] args) {
        // 示例 0x43 睡眠报告
        String sleepData = "{\"0x43\":\"QwoCCgYAAAAFAGSeHmaSAZIBNw7+Xj4FDQAFAQMN\"}";
        
        // 解析数据
        ObjectNode result = SleepDataParserUtil.parse(sleepData);
        
        // 输出解析结果
        System.out.println("解析结果: " + result.toString());
        
        // 解析结果: {"id":"0x43","content":{"scoreDetail":{"sleepScore":67,"bodyMoveDeduction":10,"lastSleepDeduction":2,"easyAwakeDeduction":10,"speedyDeduction":6,"sleepShort":0,"sleepLong":0,"breathStop":0,"breathException":5,"snore":0},"reportStatistics":{"beginStamp":1713282660,"reportDuration":402,"inBedDuration":402,"heartRateAverage":55,"respiratoryRateAverage":14,"bodyMoveTotal":254,"sleepRatio":94,"middleSleepRatio":62,"deepSleepRatio":5,"awakeTimes":13,"breathStop":0,"breathException":5,"nightSleepFlag":1,"snoreRatio":3,"antiSnoreTotal":13,"dormantSleepRatio":27,"awakeRatio":6}}}
    }
}
```

## 数据结构

### 0x41实时体征数据

```json
{
  "id": "0x41",
  "content": {
    "heartRate": 64,          // 心率
    "respiratoryRate": 17,    // 呼吸率
    "sleepState": 1           // 睡眠状态：-1 断电（定时更新）、0 离床、1 在床、2 在床但有体动、3 在床但呼吸暂停、4 重物、5 打鼾（睡眠枕使用）
  }
}
```

### 0x42睡眠数据

```json
{
  "id": "0x42",
  "content": [
    {
      "dateTime": 1622949900,      // UTC 时间戳（秒）
      "heartRate": 65,             // 心率
      "respiratoryRate": 13,       // 呼吸率
      "breathStop": 0,             // 呼吸暂停
      "bodyMoveTotal": 0,          // 体动总数
      "sleepState": 1,             // 睡眠状态：0 离床、1 在床、2 浅睡、3 中睡、4 深睡
      "antiSnoreTotal": 1,         // 止鼾次数
      "snoreTotal": 2              // 打鼾次数
    },
    {
      "dateTime": 1622949960,      // UTC 时间戳（秒）
      "heartRate": 66,             // 心率
      "respiratoryRate": 14,       // 呼吸率
      "breathStop": 0,             // 呼吸暂停
      "bodyMoveTotal": 0,          // 体动总数
      "sleepState": 1,             // 睡眠状态：0 离床、1 在床、2 浅睡、3 中睡、4 深睡
      "antiSnoreTotal": 1,         // 止鼾次数
      "snoreTotal": 3              // 打鼾次数
    }
  ]
}
```

### 0x43睡眠报告

```json
{
  "id": "0x43",
  "content": {
    "scoreDetail": {                 // 评分明细
      "sleepScore": 67,              // 睡眠评分（值为 255 时代表睡眠时长过短，无评分）
      "bodyMoveDeduction": 10,       // 体动扣分项
      "lastSleepDeduction": 2,       // 晚睡扣分项
      "easyAwakeDeduction": 10,      // 易醒扣分项
      "speedyDeduction": 6,          // 效率扣分项
      "sleepShort": 0,               // 睡眠过短
      "sleepLong": 0,                // 睡眠过长
      "breathStop": 0,               // 呼吸暂停
      "breathException": 5,          // 呼吸异常
      "snore": 0                     // 打鼾
    },
    "reportStatistics": {             // 报告统计
      "beginStamp": 1713282660,       // 起始时间戳（格林时间，以秒为单位）
      "reportDuration": 402,          // 报告时长
      "inBedDuration": 402,           // 在床时长
      "heartRateAverage": 55,         // 平均心率
      "respiratoryRateAverage": 14,   // 平均呼吸率
      "bodyMoveTotal": 254,           // 体动总数
      "sleepRatio": 94,               // 睡眠比
      "middleSleepRatio": 62,         // 中睡比
      "deepSleepRatio": 5,            // 深睡比
      "awakeTimes": 13,               // 清醒次数
      "breathStop": 0,                // 呼吸暂停
      "breathException": 5,           // 呼吸异常
      "nightSleepFlag": 1,            // 夜间睡眠标记：0 日间（一日生成多次）、1 夜间（一日只会生成1次）
      "snoreRatio": 3,                // 打鼾比
      "antiSnoreTotal": 13,           // 止鼾次数
      "dormantSleepRatio": 27,        // 浅睡比
      "awakeRatio": 6                 // 清醒比
    }
  }
}
```

## 注意事项

- 项目支持 JDK 1.8 及以上版本
- 解析的数据格式需符合项目支持的格式规范

## 许可证

MIT License

## 联系方式

如有问题或建议，欢迎联系项目维护者。
