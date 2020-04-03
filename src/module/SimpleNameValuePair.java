package module;

import org.apache.http.NameValuePair;

public class SimpleNameValuePair implements NameValuePair {
    private String name;
    private String value;
    public SimpleNameValuePair(String name,String value) {
        this.name=name;
        this.value=value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }
}
