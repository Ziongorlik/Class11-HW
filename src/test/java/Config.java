public class Config {
    private String URL;

    public Config() {
    }

    public Config(String URL, String value) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    public String toString() {
        return "URL : " + getURL();
    }
}
