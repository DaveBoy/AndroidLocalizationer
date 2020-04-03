/*
 * Copyright 2014-2015 Wesley Lin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package language_engine.google;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.ide.util.PropertiesComponent;
import data.StorageDataKey;
import language_engine.HttpUtils;
import module.SimpleNameValuePair;
import module.SupportedLanguages;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import util.Logger;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;


/**
 * Created by Wesley Lin on 12/1/14.
 */
public class GoogleTranslationApi {
    private static final String BASE_TRANSLATION_URL = "https://translation.googleapis.com/language/translate/v2";

    /**
     * @param querys
     * @param targetLanguageCode
     * @param sourceLanguageCode
     * @return
     */
    public static List<String> getTranslationJSON(@NotNull List<String> querys,
                                                  @NotNull SupportedLanguages targetLanguageCode,
                                                  @NotNull SupportedLanguages sourceLanguageCode) {
        if (querys.isEmpty())
            return null;




        List<NameValuePair> para=new ArrayList<>();
        para.add(new SimpleNameValuePair("key",PropertiesComponent.getInstance().getValue(StorageDataKey.GoogleApiKeyStored)));
        para.add(new SimpleNameValuePair("target",targetLanguageCode.getLanguageCode()));
        for (int i = querys.size() - 1; i >= 0; i--) {
            para.add(new SimpleNameValuePair("q",querys.get(i)));
        }



        String getResult = HttpUtils.doHttpPost(BASE_TRANSLATION_URL,para);
        Logger.info("do get result: " + getResult );

        JsonObject jsonObject = new JsonParser().parse(getResult).getAsJsonObject();
        if (jsonObject.get("error") != null) {
            JsonObject error = jsonObject.get("error").getAsJsonObject().get("errors").getAsJsonArray().get(0).getAsJsonObject();
            if (error == null)
                return null;

            if (error.get("reason").getAsString().equals("dailyLimitExceeded"))
                return new ArrayList<String>();
            return null;
        } else {
            JsonObject data = jsonObject.get("data").getAsJsonObject();
            JsonArray translations = data.get("translations").getAsJsonArray();
            if (translations != null) {
                List<String> result = new ArrayList<String>();
                for (int i = 0; i < translations.size(); i++) {
                    result.add(URLDecoder.decode(translations.get(i).getAsJsonObject().get("translatedText").getAsString()));
                }
                return result;
            }
        }
        return null;
    }



}
