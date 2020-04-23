package module;

public class StringArrayItem {
    private String value;
    private boolean link;

    public StringArrayItem(String value, boolean link) {
        this.value = value;
        this.link = link;
    }

    public StringArrayItem(String value) {
        this.value = value;
        this.link = false;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isLink() {
        return link;
    }

    public void setLink(boolean link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "\t<item>" +
                value +
                "</item>\n";
    }
}
