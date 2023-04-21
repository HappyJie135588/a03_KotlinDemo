package com.example.a03_kotlindemo.network.okhttp;

public interface OkUrl {
    //获取笑话
    String getJoke = "https://autumnfish.cn/api/joke";
    //一女同事28岁了，跟她老公造娃三年终于怀孕3个月了，她老公那个怜惜劲啊，上下班接送，进出门搀扶。大伙都开玩笑笑话他们腻歪。她老公华丽丽的回了句，多年来老夫千军万马全部覆没，现独留一子，现在不好好珍惜，夫怕江山易主啊。

    //注册
    String postData = "https://autumnfish.cn/api/user/register";//username
    //{"msg":"注册成功","code":200}
    //{"msg":"该用户名已被注册，请重新提交","code":400}

    //打赏
    String postJson = "https://api.sunofbeaches.com/ast/prise/article";//body,priseArticleData:{articleId: '',sob: 16,}
    //{"success":true,"code":10000,"message":"感谢您的打赏！","data":null}

    //上传文件
    String postFile = "https://api.sunofbeaches.com/oss/image/mo_yu";//image
    //{"success":true,"code":10000,"message":"上传成功.","data":"https://images.sunofbeaches.com//images/2023_04_22/1649769369643966466.png"}
}
