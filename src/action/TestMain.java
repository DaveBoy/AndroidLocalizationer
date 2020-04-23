package action;

import module.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static module.AndroidString.getAndroidStrings;


public class TestMain {
    public static void main(String[]args) throws IOException {
        File file=new File("/Users/pc/IdeaProjects/AndroidLocalizationer/values/strings.xml");
        InputStream input=new FileInputStream(file);
        List<AndroidString> androidStrings = getAndroidStrings(input);
        if(androidStrings==null) return;
        for (int i = 0; i < androidStrings.size(); i++) {
            System.out.println(androidStrings.get(i).toString());
        }

    }

}
