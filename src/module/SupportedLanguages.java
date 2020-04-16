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

package module;

import language_engine.TranslationEngineType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wesley Lin on 11/29/14.
 */
public enum SupportedLanguages {
    //https://cloud.google.com/translate/docs/languages
    Afrikaans("af", "Afrikaans", "Afrikaans","南非荷兰语"),
    Albanian("sq", "Shqiptar", "Albanian","阿尔巴尼亚语"),
    Amharic("am", "አማርኛ", "Amharic","阿姆哈拉语"),
    Arabic("ar", "العربية", "Arabic","阿拉伯语"),
    Armenian("ar", "Հայերեն", "Armenian","亚美尼亚语"),
    Azerbaijani("az", "Azərbaycan", "Azerbaijani","阿塞拜疆语"),
    Basque("eu", "Euskal", "Basque","巴斯克语"),
    Belarusian("be", "Беларускі", "Belarusian","白俄罗斯语"),
    Bengali("bn", "বাঙালি", "Bengali","孟加拉语"),
    Bosnian("bs", "Bosanski", "Bosnian","波斯尼亚语"),
    Bulgarian("bg", "Български", "Bulgarian","保加利亚语"),
    Catalan("ca", "Català", "Catalan","加泰罗尼亚语"),
    Cebuano("ceb", "Cebuano", "Cebuano","宿务语"),
    Chinese_Simplified("zh-CN", "简体中文", "Chinese Simplified","中文（简体）"),
    Chinese_Simplified_BING("zh-CHS", "简体中文", "Chinese Simplified","中文（简体）"),
    Chinese_Traditional("zh-TW", "正體中文", "Chinese Traditional","中文（繁体）"),
    Chinese_Traditional_BING("zh-CHT", "正體中文", "Chinese Traditional","中文（繁体）"),
    Corsican("co", "Corsu", "Corsican","科西嘉语"),
    Croatian("hr", "Hrvatski", "Croatian","克罗地亚语"),
    Czech("cs", "Čeština", "Czech","捷克语"),
    Danish("da", "Dansk", "Danish","丹麦语"),
    Dutch("nl", "Nederlands", "Dutch","荷兰语"),
    English("en", "English", "English","英语"),
    Esperanto("eo", "Esperanta", "Esperanto","世界语"),
    Estonian("et", "Eesti", "Estonian","爱沙尼亚语"),
    Filipino("tl", "Pilipino", "Filipino","菲律宾语"),
    Finnish("fi", "Suomi", "Finnish","芬兰语"),
    French("fr", "Français", "French","法语"),
    //Frisian("fy", "Frysk", "Frisian","弗里斯兰语"),
    Galician("gl", "Galego", "Galician","加利西亚语"),
    Georgian("ka", "ქართული", "Georgian","格鲁吉亚语"),
    German("de", "Deutsch", "German","德语"),
    Greek("el", "Ελληνικά", "Greek","希腊语"),
    Gujarati("gu", "ગુજરાતી", "Gujarati","古吉拉特语"),
    Haitian_Creole("ht", "Haitiancreole", "Haitian Creole","海地克里奥尔语"),
    Hausa("ha", "Hausa", "Hausa","豪萨语"),
    Hawaiian("haw", "ʻ .lelo Hawaiʻi", "Hawaiian","夏威夷语"),
    Hebrew("iw", "עברית", "Hebrew","希伯来语"),
    Hebrew_BING("he", "עברית", "Hebrew","希伯来语"),
    Hindi("hi", "हिंदी", "Hindi","印地语"),
    Hungarian("hu", "Magyar", "Hungarian","匈牙利语"),
    Icelandic("is", "Icelandic", "Icelandic","冰岛语"),
    Igbo("ig", "Ndi Igbo", "Igbo","伊博语"),
    Indonesian("id", "Indonesia", "Indonesian","印度尼西亚语"),
    Irish("ga", "Irish", "Irish","爱尔兰语"),
    Italian("it", "Italiano", "Italian","意大利语"),
    Japanese("ja", "日本語", "Japanese","日语"),
    Javanese("jv", "Basa Jawa", "Javanese","爪哇语"),
    Kannada("kn", "ಕನ್ನಡ", "Kannada","卡纳达语"),
    Korean("ko", "한국의", "Korean","韩语"),
    Latin("la", "Latina", "Latin","拉丁文"),
    Latvian("lv", "Latvijas", "Latvian","拉脱维亚语"),
    Lithuanian("lt", "Lietuvos", "Lithuanian","立陶宛语"),
    Macedonian("mk", "Македонски", "Macedonian","马其顿语"),
    Malay("ms", "Melayu", "Malay","马来语"),
    Maltese("mt", "Malti", "Maltese","马耳他语"),
    Norwegian("no", "Norsk", "Norwegian","挪威语"),
    Persian("fa", "فارسی", "Persian","波斯语"),
    Polish("pl", "Polski", "Polish","波兰语"),
    Portuguese("pt", "Português", "Portuguese","葡萄牙语"),
    Romanian("ro", "Român", "Romanian","罗马尼亚语"),
    Russian("ru", "Русский", "Russian","俄语"),
    Serbian("sr", "Српски", "Serbian","塞尔维亚语"),
    Slovak("sk", "Slovenčina", "Slovak","斯洛伐克语"),
    Slovenian("sl", "Slovenščina", "Slovenian","斯洛文尼亚语"),
    Spanish("es", "Español", "Spanish","西班牙语"),
    Swahili("sw", "Kiswahili", "Swahili","斯瓦希里语"),
    Swedish("sv", "Svenska", "Swedish","瑞典语"),
    Tamil("ta", "தமிழ்", "Tamil","泰米尔语"),
    Telugu("te", "తెలుగు", "Telugu","泰卢固语"),
    Thai("th", "ไทย", "Thai","泰文"),
    Turkish("tr", "Türk", "Turkish","土耳其语"),
    Ukrainian("uk", "Український", "Ukrainian","乌克兰语"),
    Urdu("ur", "اردو", "Urdu","乌尔都语"),
    Vietnamese("vi", "Tiếng Việt", "Vietnamese","越南语"),
    Welsh("cy", "Cymraeg", "Welsh","威尔士语"),
    Yiddish("yi", "ייִדיש", "Yiddish","意第绪语"),

    //http://api.fanyi.baidu.com/doc/21
    AUTO_BAIDU("auto","自动检测","auto check","自动检测"),
    Chinese_Simplified_BAIDU("zh", "简体中文", "Chinese Simplified","中文简体","zh-rCN"),
    English_BAIDU("en", "English", "English","英语","en"),
    Japanese_BAIDU("jp","日本語","Japanese","日语","ja"),
    Korean_BAIDU("kor","한국어","Korean","韩语","ko"),
    French_BAIDU("fra", "Français", "French","法语","fr"),
    Spanish_BAIDU("spa", "Español", "Spanish","西班牙语","es"),
    Thai_BAIDU("th", "ไทย", "Thai","泰语","th"),
    Arabic_BAIDU("ara", "العربية", "Arabic","阿拉伯语","ar"),
    Russian_BAIDU("ru", "Русский", "Russian","俄语","ru"),
    Portuguese_BAIDU("pt", "Português", "Portuguese","葡萄牙语","pt"),
    German_BAIDU("de", "Deutsch", "German","德语","de"),
    Italian_BAIDU("it", "Italiano", "Italian","意大利语","it"),
    Greek_BAIDU("el", "Ελληνικά", "Greek","希腊语","el"),
    Dutch_BAIDU("nl", "Nederlands", "Dutch","荷兰语","nl"),
    Polish_BAIDU("pl", "Polski", "Polish","波兰语","pl"),
    Bulgarian_BAIDU("bul", "Български", "Bulgarian","保加利亚语","bg"),
    Estonian_BAIDU("est", "Eesti", "Estonian","爱沙尼亚语","et"),
    Danish_BAIDU("dan", "Dansk", "Danish","丹麦语","da"),
    Finnish_BAIDU("fin", "Suomi", "Finnish","芬兰语","fi"),
    Czech_BAIDU("cs", "Čeština", "Czech","捷克语","cs"),
    Romanian_BAIDU("rom", "Român", "Romanian","罗马尼亚语","ro"),
    Slovenian_BAIDU("slo", "Slovenščina", "Slovenian","斯洛文尼亚语","sl"),
    Swedish_BAIDU("swe", "Svenska", "Swedish","瑞典语","sv"),
    Hungarian_BAIDU("hu", "Magyar", "Hungarian","匈牙利语","hu"),
    Chinese_Traditional_BAIDU("cht", "正體中文", "Chinese Traditional","中文繁体","zh-rTW"),
    Vietnamese_BAIDU("vie", "Tiếng Việt", "Vietnamese","越南语","vi");

    private String languageCode;
    private String languageDisplayName;
    private String languageEnglishDisplayName;
    private String languageChineseDisplayName;
    private String realLanguageCode;

    SupportedLanguages(String languageCode, String languageDisplayName, String languageEnglishDisplayName,String languageChineseDisplayName) {
        this.languageCode = languageCode;
        this.languageDisplayName = languageDisplayName;
        this.languageEnglishDisplayName = languageEnglishDisplayName;
        this.languageChineseDisplayName = languageChineseDisplayName;
    }
    SupportedLanguages(String languageCode, String languageDisplayName, String languageEnglishDisplayName,String languageChineseDisplayName,String realLanguageCode) {
        this.languageCode = languageCode;
        this.languageDisplayName = languageDisplayName;
        this.languageEnglishDisplayName = languageEnglishDisplayName;
        this.languageChineseDisplayName = languageChineseDisplayName;
        this.realLanguageCode = realLanguageCode;
    }
    public String getLanguageCode() {
        return languageCode;
    }

    public String getLanguageDisplayName() {
        return languageDisplayName;
    }

    public String getLanguageEnglishDisplayName() {
        return languageEnglishDisplayName;
    }
    public String getLanguageChineseDisplayName() {
        return languageChineseDisplayName;
    }
    public String getRealLanguageCode() {
        return realLanguageCode;
    }

    public static List<SupportedLanguages> getAllSupportedLanguages(TranslationEngineType type) {
        switch (type) {
            case Baidu:
                return getBaiduLanguages();
            case Bing:
                return getBingLanguages();
            case Google:
                return getGoogleLanguages();
        }
        return null;
    }

    public String toString() {
        return getLanguageEnglishDisplayName() + "(\"" + getLanguageCode() + "\", \"" + getLanguageDisplayName() + "\")";
    }

    // get the right value-XX suffix
    public String getAndroidStringFolderNameSuffix() {
        if (this.name().contains("BAIDU")){
            System.out.println(this.toString());
            return this.getRealLanguageCode();
        }
        if (this == Chinese_Simplified_BING || this == Chinese_Simplified)
            return "zh-rCN";
        if (this == Chinese_Traditional_BING || this == Chinese_Traditional)
            return "zh-rTW";
        if (this == Hebrew_BING)
            return Hebrew.getLanguageCode();

        return this.getLanguageCode();
    }
    // google supported language code: https://cloud.google.com/translate/v2/using_rest, language reference section
    private static List<SupportedLanguages> getBaiduLanguages() {
        List<SupportedLanguages> result = new ArrayList<SupportedLanguages>();
        result.add(Chinese_Simplified_BAIDU);
        result.add(Chinese_Traditional_BAIDU);
        result.add(English_BAIDU);
        result.add(Japanese_BAIDU);
        result.add(Korean_BAIDU);
        result.add(French_BAIDU);
        result.add(Spanish_BAIDU);
        result.add(Thai_BAIDU);
        result.add(Arabic_BAIDU);
        result.add(Russian_BAIDU);
        result.add(Portuguese_BAIDU);
        result.add(German_BAIDU);
        result.add(Italian_BAIDU);
        result.add(Greek_BAIDU);
        result.add(Dutch_BAIDU);
        result.add(Polish_BAIDU);
        result.add(Bulgarian_BAIDU);
        result.add(Estonian_BAIDU);
        result.add(Danish_BAIDU);
        result.add(Finnish_BAIDU);
        result.add(Czech_BAIDU);
        result.add(Romanian_BAIDU);
        result.add(Slovenian_BAIDU);
        result.add(Swedish_BAIDU);
        result.add(Hungarian_BAIDU);
        result.add(Vietnamese_BAIDU);
        return result;
    }
    // google supported language code: https://cloud.google.com/translate/docs/languages, language reference section
    private static List<SupportedLanguages> getGoogleLanguages() {
        List<SupportedLanguages> result = new ArrayList<SupportedLanguages>();
        result.add(Afrikaans);
        result.add(Albanian);
        result.add(Arabic);
        result.add(Azerbaijani);
        result.add(Basque);
        result.add(Bengali);
        result.add(Belarusian);
        result.add(Bulgarian);
        result.add(Catalan);
        result.add(Chinese_Simplified);
        result.add(Chinese_Traditional);
        result.add(Croatian);
        result.add(Czech);
        result.add(Danish);
        result.add(Dutch);
//        result.add(English);
        result.add(Esperanto);
        result.add(Estonian);
        result.add(Filipino);
        result.add(Finnish);
        result.add(French);
        result.add(Galician);
        result.add(Georgian);
        result.add(German);
        result.add(Greek);
        result.add(Gujarati);
        result.add(Haitian_Creole);
        result.add(Hebrew);
        result.add(Hindi);
        result.add(Hungarian);
        result.add(Icelandic);
        result.add(Indonesian);
        result.add(Irish);
        result.add(Italian);
        result.add(Japanese);
        result.add(Kannada);
        result.add(Korean);
        result.add(Latin);
        result.add(Latvian);
        result.add(Macedonian);
        result.add(Malay);
        result.add(Maltese);
        result.add(Norwegian);
        result.add(Persian);
        result.add(Polish);
        result.add(Portuguese);
        result.add(Romanian);
        result.add(Russian);
        result.add(Serbian);
        result.add(Slovak);
        result.add(Slovenian);
        result.add(Spanish);
        result.add(Swahili);
        result.add(Swedish);
        result.add(Tamil);
        result.add(Telugu);
        result.add(Thai);
        result.add(Turkish);
        result.add(Ukrainian);
        result.add(Urdu);
        result.add(Vietnamese);
        result.add(Welsh);
        result.add(Yiddish);
        return result;
    }

    // bing supported language code: http://msdn.microsoft.com/en-us/library/hh456380.aspx
    private static List<SupportedLanguages> getBingLanguages() {
        List<SupportedLanguages> result = new ArrayList<SupportedLanguages>();
        result.add(Arabic);
        result.add(Bulgarian);
        result.add(Catalan);
        result.add(Chinese_Simplified_BING);
        result.add(Chinese_Traditional_BING);
        result.add(Czech);
        result.add(Danish);
        result.add(Dutch);
        result.add(English);
        result.add(Estonian);
        result.add(Finnish);
        result.add(French);
        result.add(German);
        result.add(Greek);
        result.add(Haitian_Creole);
        result.add(Hebrew_BING);
        result.add(Hindi);
        result.add(Hungarian);
        result.add(Indonesian);
        result.add(Italian);
        result.add(Japanese);
        result.add(Korean);
        result.add(Latvian);
        result.add(Lithuanian);
        result.add(Malay);
        result.add(Maltese);
        result.add(Norwegian);
        result.add(Persian);
        result.add(Polish);
        result.add(Portuguese);
        result.add(Romanian);
        result.add(Russian);
        result.add(Slovak);
        result.add(Slovenian);
        result.add(Spanish);
        result.add(Swedish);
        result.add(Thai);
        result.add(Turkish);
        result.add(Ukrainian);
        result.add(Urdu);
        result.add(Vietnamese);
        result.add(Welsh);
        return result;
    }
}
