# 国际化插件
改进自[wujushan/AndroidLocalizationer](https://github.com/wujushan/AndroidLocalizationer)使用方式不变：

## 代码的事，哪能没有bug是吧。
我也就简单的测试了一下，如果遇到什么问题，欢迎大家提issue。

## 使用说明
1. 本地安装插件 
   
    [AndroidLocalizationer](https://github.com/DaveBoy/AndroidLocalizationer/blob/master/AndroidLocalizationer.zip)
    下载后解压出 **AndroidLocalizationer.jar**文件，再如下图进行安装
    ![安装](https://github.com/DaveBoy/AndroidLocalizationer/blob/master/img/install.png)
2. 然后申请对应翻译的key填入设置

    2.1 申请
    包含google和百度两种翻译，目前百度翻译是基本版免费，google也有免费的额度，但是google的翻译弄起来比较麻烦。
    
    百度:[申请百度翻译api](http://api.fanyi.baidu.com/doc/12) 
    
    google:[google翻译（基本版）快速入门](https://cloud.google.com/translate/docs/basic/setup-basic)
    
    2.2 设置key
   
    ![设置key](https://github.com/DaveBoy/AndroidLocalizationer/blob/master/img/setkey.png)
3. 选中string.xml右键选中"Convert to other languages"，选择对应的语言,点击就可以生成了

## 更新日志

#### V0.0.3
1. 根据百度翻译最新文档接入百度翻译 

    [通用翻译API接入文档](http://api.fanyi.baidu.com/doc/21)
    
2. 为什么我的请求会返回54003？
    
    54003表示请求频率超限，请降低您的请求频率。
    
    对于标准版服务，您的QPS（每秒请求量）=1，如需更大频率，请先进行身份认证，认证通过后可切换为高级版（适用于个人，QPS=10）或尊享版（适用于企业，QPS=100）
    
#### V0.0.2
1. 根据google翻译最新文档接入google翻译 

    [翻译文本（基本版）](https://cloud.google.com/translate/docs/basic/translating-text#translate_translate_text-drest)
    
    [创建API 密钥](https://cloud.google.com/docs/authentication/api-keys)
    
    [启用api](https://console.developers.google.com/apis/api/translate.googleapis.com/overview)
    
