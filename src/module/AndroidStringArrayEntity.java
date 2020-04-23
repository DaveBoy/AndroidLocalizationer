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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wesley Lin on 11/29/14.
 */
public class AndroidStringArrayEntity extends AndroidString{
    private List<StringArrayItem> children;

    public AndroidStringArrayEntity(String key) {
        this.key = key;
    }
    public void addChild(StringArrayItem item){
        ArrayList<StringArrayItem> list=new ArrayList();
        if(children!=null){
            list.addAll(children);
        }
        list.add(item);
        children=list;
    }
    public void setChild(List<StringArrayItem> list){
        children=list;
    }
    public List<StringArrayItem> getChild(){
        return children==null?new ArrayList<>():children;
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append("<string-array name=\"" +
                getKey() +
                "\">\n");
        for (StringArrayItem child : children) {
            builder.append(child.toString());
        }
        builder.append("</string-array>");
        return builder.toString();
    }

   
}
