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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.Logger;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Wesley Lin on 11/29/14.
 */
public class AndroidString{
    protected String key;
    protected String value;
    private boolean link;

    public AndroidString(String key, String value,boolean localLink) {
        this.key = key;
        this.value = value;
        this.link = localLink;
    }
    public AndroidString(String key, String value) {
        this.key = key;
        this.value = value;
        this.link = false;
    }

    public AndroidString(AndroidString androidString) {
        this.key = androidString.getKey();
        this.value = androidString.getValue();
    }

    public AndroidString() {

    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "<string name=\"" +
                key +
                "\">" +
                value +
                "</string>";
    }



    public static List<AndroidString> getAndroidStrings(InputStream xml) throws IOException {
        List<AndroidString> androidStrings = new ArrayList<>();
        if (xml != null) {
            org.jsoup.nodes.Document parse = Jsoup.parse(xml, "utf-8", "");
            Element resources = parse.getElementsByTag("resources").first();


            Elements strings = resources.children();


            for (Element string : strings) {
                switch (string.tagName()){
                    case "string":
                        String trans=string.attr("translatable");
                        if(!"false".equals(trans)) {
                            String text=string.text();
                            androidStrings.add(new AndroidString(string.attr("name"), text,isLocalLink(text)));
                        }
                        break;
                    case "string-array":
                        AndroidStringArrayEntity array = new AndroidStringArrayEntity(string.attr("name"));
                        androidStrings.add(array);
                        getArrayItem(array,string);
                        break;
                }
            }

        }
        return androidStrings;
    }

    private static void getArrayItem(AndroidStringArrayEntity array, Element string) {
        Elements children = string.children();
        for (Element child : children) {
            String text=child.text();
            array.addChild(new StringArrayItem(text,isLocalLink(text)));
        }
    }
    private static boolean isLocalLink(String str){
        return str.startsWith("@string/");
    }



    public static List<String> getAndroidStringValues(List<AndroidString> list) {
        List<String> result = new ArrayList<String>();

        for (int i = 0; i < list.size(); i++) {
            AndroidString androidString = list.get(i);
            if(androidString instanceof AndroidStringArrayEntity){
                for (StringArrayItem stringArrayItem : ((AndroidStringArrayEntity) androidString).getChild()) {
                    if(!stringArrayItem.isLink())
                        result.add(stringArrayItem.getValue());
                }
            }else {
                result.add(list.get(i).getValue());
            }
        }
        return result;
    }

    public boolean isLink() {
        return link;
    }
}
