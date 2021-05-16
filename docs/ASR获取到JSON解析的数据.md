

说话：”西安的天气怎么样？“

打印的Log

```log
2021-04-25 20:45:44.228 5021-5021/top.iqqcode.xiaoq I/VoiceManager: ASR准备就绪
2021-04-25 20:45:48.227 5021-5021/top.iqqcode.xiaoq I/VoiceManager: ASR动作的识别结果为{"appid":15363,"encoding":"UTF-8","results":[{"domain":"weather","intent":"USER_WEATHER","score":100.0,"slots":{"user_loc":[{"word":"西安","norm":"(NERL_PLUS_LOC_ROOT)>(陕西省)>西安市"}]}}],"err_no":0,"parsed_text":"西安 天气 怎么 样 ？","raw_text":"西安天气怎么样？"}
2021-04-25 20:45:48.228 5021-5021/top.iqqcode.xiaoq I/VoiceManager: ASR识别结束{"origin_result":{"sn":"cuid=4882544A6AD7BC445DAAD1602711A987|0&sn=532fa191-1c77-4c45-9afa-a6cffeda479a&nettype=4","error":"Speech Recognize success.","err_no":0},"error":0,"desc":"Speech Recognize success."}
```

转义为JSON

![image-20210425205522830](https://iqqcode-blog.oss-cn-beijing.aliyuncs.com/img-2021-befo/20210425205523.png)

```json
{
    "appid":15363,
    "encoding":"UTF-8",
    "results":[ // 识别结果
        {
            "domain":"weather", // 领域取值
            "intent":"USER_WEATHER", // 意图：用户查询天气
            "score":100, // 命中语义
            "slots":{ // 词槽
                "user_loc":[ // 用户寻找的地点
                    {
                        "word":"西安",
                        "norm":"(NERL_PLUS_LOC_ROOT)>(陕西省)>西安市" // 国际标码
                    }
                ]
            }
        }
    ],
    "err_no":0,
    "parsed_text":"西安 天气 怎么 样 ？",
    "raw_text":"西安天气怎么样？"
}
```

