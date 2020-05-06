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

package data.task;

import action.AndroidLocalization;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import data.Log;
import data.SerializeUtil;
import data.StorageDataKey;
import language_engine.TranslationEngineType;
import language_engine.baidu.BaiduTranslationApi;

import language_engine.google.GoogleTranslationApi;
import module.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wesley Lin on 12/1/14.
 */
public class GetTranslationTask extends Task.Backgroundable {

    private List<SupportedLanguages> selectedLanguages;
    private final List<AndroidString> androidStrings;
    private double indicatorFractionFrame;
    private TranslationEngineType translationEngineType;
    private boolean override;
    private VirtualFile clickedFile;

    private static final String GoogleErrorUnknown = "Error, please check API key in the settings panel.";
    private static final String GoogleDailyLimitError = "Daily Limit Exceeded, please note that Google Translation API " +
            "is a <html><a href=\"https://cloud.google.com/translate/v2/pricing\">paid service.</a></html>";

    private String errorMsg = null;

    public GetTranslationTask(Project project, String title,
                              List<SupportedLanguages> selectedLanguages,
                              List<AndroidString> androidStrings,
                              TranslationEngineType translationEngineType,
                              boolean override,
                              VirtualFile clickedFile) {
        super(project, title);
        this.selectedLanguages = selectedLanguages;
        this.androidStrings = androidStrings;
        this.translationEngineType = translationEngineType;
        this.indicatorFractionFrame = 1.0d / (double) (this.selectedLanguages.size());
        this.override = override;
        this.clickedFile = clickedFile;
    }

    @Override
    public void run(ProgressIndicator indicator) {
        try {
            for (int i = 0; i < selectedLanguages.size(); i++) {

                SupportedLanguages language = selectedLanguages.get(i);

                if (language != null && !"".equals(language) /*&& !language.equals(SupportedLanguages.English)*/) {

                    List<AndroidString> androidStringList = filterAndroidString(androidStrings, language, override);

                    List<List<AndroidString>> filteredAndSplittedString
                            = splitAndroidString(androidStringList, translationEngineType);

                    List<AndroidString> translationResult = new ArrayList<AndroidString>();
                    for (int j = 0; j < filteredAndSplittedString.size(); j++) {

                        List<AndroidString> strings = getTranslationEngineResult(
                                filteredAndSplittedString.get(j),
                                language,
                                SupportedLanguages.AUTO_BAIDU,
                                translationEngineType
                        );

                        if (strings == null) {
                            Log.i("language===" + language);
                            continue;
                        }
                        translationResult.addAll(strings);
                        indicator.setFraction(indicatorFractionFrame * (double) (i)
                                + indicatorFractionFrame / filteredAndSplittedString.size() * (double) (j));
                        indicator.setText("Translating to " + language.getLanguageEnglishDisplayName()
                                + " (" + language.getLanguageDisplayName() + ")");
                    }
                    String fileName = getValueResourcePath(language);
                    Logger.info("output path:" + fileName);
                    List<AndroidString> fileContent = getTargetAndroidStrings(androidStrings, translationResult, fileName, override);
                    writeAndroidStringToLocal(myProject, fileName, fileContent);
                }
            }
        }catch (Exception e){
            Logger.error(e.getLocalizedMessage());
        }
    }


    @Override
    public void onSuccess() {

        if (errorMsg == null || errorMsg.isEmpty())
            return;
        AndroidLocalization.showSuccessDialog(getProject(), "translation Success");
    }

    private String getValueResourcePath(SupportedLanguages language) {
        String resPath = clickedFile.getParent().getParent().getPath();

       /* String resPath = clickedFile.getPath().substring(0,
                clickedFile.getPath().indexOf("/res/") + "/res/".length());*/

        return resPath + "/values-" + language.getAndroidStringFolderNameSuffix()
                + "/" + clickedFile.getName();
    }

    // todo: if got error message, should break the background task
    private List<AndroidString> getTranslationEngineResult(@NotNull List<AndroidString> needToTranslatedString,
                                                           @NotNull SupportedLanguages targetLanguageCode,
                                                           @NotNull SupportedLanguages sourceLanguageCode,
                                                           TranslationEngineType translationEngineType) {

        List<String> querys = AndroidString.getAndroidStringValues(needToTranslatedString);
        Log.i(querys.toString());

        List<String> result = null;

        switch (translationEngineType) {
            case Baidu:
                result = BaiduTranslationApi.getTranslationJSON(querys,targetLanguageCode,sourceLanguageCode);
                break;
            case Bing:
                break;
            case Google:
                result = GoogleTranslationApi.getTranslationJSON(querys, targetLanguageCode, sourceLanguageCode);
                if (result == null) {
                    errorMsg = GoogleErrorUnknown;
                    return null;
                } else if (result.isEmpty() && !querys.isEmpty()) {
                    errorMsg = GoogleDailyLimitError;
                    return null;
                }
                break;
        }
        if (result == null || result.size() <= 0){
            return null;
        }

        List<AndroidString> translatedAndroidStrings = new ArrayList<>();
//        Logger.error(needToTranslatedString.size());
//        Logger.info("needToTranslatedString.size(): " + needToTranslatedString.size()+
//                "result.size(): " + result.size());
        for (int i = 0,j=0; i < needToTranslatedString.size()&&j<needToTranslatedString.size(); j++) {
            AndroidString oldAndroidString = needToTranslatedString.get(j);

            //link是处理引用
            if(oldAndroidString instanceof AndroidStringArrayEntity){
                AndroidStringArrayEntity androidStringArrayEntity = new AndroidStringArrayEntity(oldAndroidString.getKey());
                List<StringArrayItem> child = ((AndroidStringArrayEntity) oldAndroidString).getChild();
                for(StringArrayItem item:child ){
                    if(item.isLink()){
                        androidStringArrayEntity.addChild(item);
                    }else {
                        androidStringArrayEntity.addChild(new StringArrayItem(result.get(i)));
                        i++;
                    }
                }
                translatedAndroidStrings.add(androidStringArrayEntity);
            }else{
                if(oldAndroidString.isLink()){
                    translatedAndroidStrings.add(oldAndroidString);
                }else {
                    translatedAndroidStrings.add(new AndroidString(
                            oldAndroidString.getKey(), result.get(i)));
                    i++;
                }
            }

        }
        return translatedAndroidStrings;
    }

    private List<List<AndroidString>> splitAndroidString(List<AndroidString> origin, TranslationEngineType engineType) {

        List<List<AndroidString>> splited = new ArrayList<List<AndroidString>>();
        int splitFragment = 50;
        switch (engineType) {
            case Baidu:
                splitFragment = 50;
                break;
            case Bing:
                splitFragment = 50;
                break;
            case Google:
                splitFragment = 50;
                break;
        }

        if (origin != null && origin.size() > 0) {
            if (origin.size() <= splitFragment) {
                splited.add(origin);
            } else {
                int count = (origin.size() % splitFragment == 0) ? (origin.size() / splitFragment) : (origin.size() / splitFragment + 1);
                for (int i = 1; i <= count; i++) {
                    int end = i * splitFragment;
                    if (end > origin.size()) {
                        end = origin.size();
                    }

                    splited.add(origin.subList((i - 1) * splitFragment, end));
                }
            }
        }

        return splited;
    }

    private List<AndroidString> filterAndroidString(List<AndroidString> origin,
                                                    SupportedLanguages language,
                                                    boolean override) {
        List<AndroidString> result = new ArrayList<AndroidString>();




        String rulesString = PropertiesComponent.getInstance().getValue(StorageDataKey.SettingFilterRules);
        List<FilterRule> filterRules = new ArrayList<FilterRule>();
        if (rulesString == null) {
            filterRules.add(FilterRule.DefaultFilterRule);
        } else {
            filterRules = SerializeUtil.deserializeFilterRuleList(rulesString);
        }
//        Log.i("targetAndroidString: " + targetAndroidStrings.toString());
        for (AndroidString androidString : origin) {
            // filter rules
            if (FilterRule.inFilterRule(androidString.getKey(), filterRules))
                continue;

            // override
            /*if (!override && !targetAndroidStrings.isEmpty()) {
                // check if there is the androidString in this file
                // if there is, filter it
                if (isAndroidStringListContainsKey(targetAndroidStrings, androidString.getKey())) {
                    continue;
                }
            }*/

            result.add(androidString);
        }

        return result;
    }

    private static List<AndroidString> getTargetAndroidStrings(List<AndroidString> sourceAndroidStrings,
                                                               List<AndroidString> translatedAndroidStrings,
                                                               String fileName,
                                                               boolean override) {

        if (translatedAndroidStrings == null) {
            translatedAndroidStrings = new ArrayList<AndroidString>();
        }

        VirtualFile existenceFile = LocalFileSystem.getInstance().findFileByPath(fileName);
        List<AndroidString> existenceAndroidStrings = null;
        if (existenceFile != null && !override) {
            try {
//                existenceAndroidStrings = AndroidString.getAndroidStringsList(existenceFile.contentsToByteArray());
                existenceAndroidStrings = AndroidString.getAndroidStrings(existenceFile.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            existenceAndroidStrings = new ArrayList<AndroidString>();
        }

        Log.i("sourceAndroidStrings: " + sourceAndroidStrings,
                "translatedAndroidStrings: " + translatedAndroidStrings,
                "existenceAndroidStrings: " + existenceAndroidStrings);

        List<AndroidString> targetAndroidStrings = new ArrayList<AndroidString>();

        for (int i = 0; i < sourceAndroidStrings.size(); i++) {
            AndroidString string = sourceAndroidStrings.get(i);
            AndroidString resultString ;
            if(string instanceof  AndroidStringArrayEntity) resultString= new AndroidStringArrayEntity(string.getKey()); else resultString=new AndroidString(string);
            replaceValueOrChildren(translatedAndroidStrings, resultString);
            // if override is checked, skip setting the existence value, for performance issue
            if (!override) {//不覆盖原有的
                replaceValueOrChildren(existenceAndroidStrings, resultString);
            }
            targetAndroidStrings.add(resultString);
        }
        Log.i("targetAndroidStrings: " + targetAndroidStrings);
        return targetAndroidStrings;
    }



    private static void writeAndroidStringToLocal(final Project myProject, String filePath, List<AndroidString> fileContent) {
        File file = new File(filePath);
        final VirtualFile virtualFile;
        boolean fileExits = true;
        try {
            file.getParentFile().mkdirs();
            if (!file.exists()) {
                fileExits = false;
                file.createNewFile();
            }
            //Change by GodLikeThomas FIX: Appeared Messy code under windows --start; 
            //FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            //BufferedWriter writer = new BufferedWriter(fileWriter);
            //writer.write(getFileContent(fileContent));
            //writer.close();
            FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile());
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            osw.write(getFileContent(fileContent));
            osw.close();
            //Change by GodLikeThomas FIX: Appeared Messy code under windows --end;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fileExits) {
            virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
            if (virtualFile == null)
                return;
            virtualFile.refresh(true, false, new Runnable() {
                @Override
                public void run() {
                    openFileInEditor(myProject, virtualFile);
                }
            });
        } else {
            virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(file);
            openFileInEditor(myProject, virtualFile);
        }
    }

    private static void openFileInEditor(final Project myProject, @Nullable final VirtualFile file) {
        if (file == null)
            return;

        // run in UI thread:
        //    https://theantlrguy.atlassian.net/wiki/display/~admin/Intellij+plugin+development+notes#Intellijplugindevelopmentnotes-GUIandthreads,backgroundtasks
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                final FileEditorManager editorManager = FileEditorManager.getInstance(myProject);
                editorManager.openFile(file, true);
            }
        });
    }

    private static String getFileContent(List<AndroidString> fileContent) {
        String xmlHeader = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
        String stringResourceHeader = "<resources>\n\n";
        String stringResourceTail = "</resources>\n";

        StringBuilder sb = new StringBuilder();
        sb.append(xmlHeader).append(stringResourceHeader);
        for (AndroidString androidString : fileContent) {
            sb.append("\t").append(androidString.toString()).append("\n");
        }
        sb.append("\n").append(stringResourceTail);
        return sb.toString();
    }



    public static AndroidString getAndroidStringInList(List<AndroidString> androidStrings, String key) {
        for (AndroidString androidString : androidStrings) {
            if (androidString.getKey().equals(key)) {
                return androidString;
            }
        }
        return null;
    }
    private static void replaceValueOrChildren(List<AndroidString> translatedAndroidStrings, AndroidString resultString) {
        AndroidString translatedValue = getAndroidStringInList(translatedAndroidStrings, resultString.getKey());
        if (translatedValue != null) {
            resultString.setValue(translatedValue.getValue());
            if(translatedValue instanceof AndroidStringArrayEntity){
                ((AndroidStringArrayEntity) resultString).setChild( ((AndroidStringArrayEntity) translatedValue).getChild());
            }
        }
    }
}
