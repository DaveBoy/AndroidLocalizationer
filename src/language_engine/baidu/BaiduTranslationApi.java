package language_engine.baidu;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.ide.util.PropertiesComponent;
import data.Key;
import data.Log;
import data.StorageDataKey;
import language_engine.HttpUtils;
import module.SupportedLanguages;
import org.apache.http.message.BasicNameValuePair;
import org.jetbrains.annotations.NotNull;
import util.Logger;

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
            List<String> results = new ArrayList<>();
            for (String query : querys) {
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
                            String result = translations.get(0).getAsJsonObject().get("dst").getAsString();
                            results.add(result);
                        }
                    }
                } else {
                    return null;
                }
            }
            return results;
        }
//        baiduTranslate(propertiesComponent,querys, targetLanguageCode, sourceLanguageCode);
        return null;
    }

    private static List<String> baiduTranslate(PropertiesComponent propertiesComponent, List<String> querys,
                                               SupportedLanguages targetLanguageCode, SupportedLanguages sourceLanguageCode) {
        if (querys.isEmpty())
            return null;
        List<StringBuilder> queryBuilders = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        for (int i = 0; i < querys.size(); i++) {
            queryBuilder.append(querys.get(i)).append("\n");
            int nexLength = (i + 1) == querys.size() ? -1 : querys.get(i + 1).getBytes().length;
            if (queryBuilder.toString().getBytes().length + nexLength > MAX_BYTE) {
                queryBuilders.add(queryBuilder);
                queryBuilder = new StringBuilder();
            }
            if (i == querys.size() - 1) {
                queryBuilders.add(queryBuilder);
            }
        }

        if (queryBuilders.size() > 0) {
            List<String> results = new ArrayList<>();
            for (StringBuilder builder : queryBuilders) {
                String query = builder.toString();
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
                if (getResult != null) {
                    Logger.error(getResult);
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
                            List<String> result = new ArrayList<>();
                            for (int i = 0; i < translations.size(); i++) {
                                result.add(translations.get(i).getAsJsonObject().get("dst").getAsString());
                            }
                            results.addAll(result);

                        }
                    }
                } else {
                    return null;
                }
            }
            return results;
        }
        return null;
    }
}
