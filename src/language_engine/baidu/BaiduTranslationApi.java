package language_engine.baidu;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.ui.ListUtil;
import data.Key;
import data.Log;
import data.StorageDataKey;
import language_engine.HttpUtils;
import module.SupportedLanguages;
import org.apache.commons.collections.ListUtils;
import org.apache.http.message.BasicNameValuePair;
import org.jetbrains.annotations.NotNull;
import util.Logger;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

public class BaiduTranslationApi {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";
    private static final int MAX_BYTE = 6000;

    private String appid;
    private String securityKey;

    public BaiduTranslationApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }

    /**
     * @param querys
     * @param targetLanguageCode
     * @param sourceLanguageCode
     * @return
     */
    public static List<String> getTranslationJSON(@NotNull List<String> querys,
                                                  @NotNull SupportedLanguages targetLanguageCode,
                                                  @NotNull SupportedLanguages sourceLanguageCode) {
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();

        if (querys.size() > 0) {
            try {
                Thread.sleep(1000);//http://api.fanyi.baidu.com/doc/21 对于标准版服务，您的QPS（每秒请求量）=1，如需更大频率，请先进行身份认证，认证通过后可切换为高级版
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            StringBuilder qu = new StringBuilder();
            for (int i = 0; i < querys.size(); i++) {
                if (i != 0)
                    qu.append("\n");
                qu.append(querys.get(i));
            }
            String query = qu.toString();
            List<String> results = new ArrayList<>();

            Map<String, String> params = new HashMap<>();
            params.put("q", query);
            params.put("from", sourceLanguageCode.getLanguageCode());
            params.put("to", targetLanguageCode.getLanguageCode());
            String appid = new BasicNameValuePair("client_id",
                    propertiesComponent.getValue(StorageDataKey.BaiduClientIdStored, Key.BAIDU_CLIENT_ID)).getValue();
            params.put("appid", appid);
            if (appid.isEmpty()) {
                Logger.error("Please input your Baidu  APPID");
            }
            // 随机数
            String salt = String.valueOf(System.currentTimeMillis());
            params.put("salt", salt);

            String securityKey = new BasicNameValuePair("client_secret",
                    propertiesComponent.getValue(StorageDataKey.BaiduClientSecretStored, Key.BAIDU_CLIENT_SECRET)).getValue();
            if (securityKey.isEmpty()) {
                Logger.error("Please input your Baidu SecretKey");
            }
            // 签名
            String src = appid + query + salt + securityKey; // 加密前的原文
            params.put("sign", MD5.md5(src));
            String getResult = HttpGet.get(TRANS_API_HOST, params);
            Logger.info("Baidu Translation:" + getResult);
            if (getResult != null) {
                JsonObject resultObj = new JsonParser().parse(getResult).getAsJsonObject();
                JsonElement errorElement = resultObj.get("error_code");
                if (errorElement != null) {
                    String errorCode = errorElement.getAsString();
                    String errorMsg = resultObj.get("error_msg").getAsString();
                    Logger.error(errorCode + " :" + errorMsg);
                    return null;
                } else {
                    JsonArray translations = resultObj.getAsJsonArray("trans_result");
                    if (translations != null) {
                        for (int i = 0; i < translations.size(); i++) {
                            String result = translations.get(i).getAsJsonObject().get("dst").getAsString();
                            results.add(URLDecoder.decode(result));
                        }

                    }
                }
            } else {
                return null;
            }

            return results;
        }
//        baiduTranslate(propertiesComponent,querys, targetLanguageCode, sourceLanguageCode);
        return null;
    }


}
