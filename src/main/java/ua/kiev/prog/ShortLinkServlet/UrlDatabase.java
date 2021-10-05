package ua.kiev.prog.ShortLinkServlet;

import org.apache.commons.lang3.RandomStringUtils;

import java.net.URLDecoder;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UrlDatabase {

    public final static UrlDatabase INSTANCE = new UrlDatabase();

    private final Map<String, UrlRecord> db = new HashMap<>();
    private long n;

    private UrlDatabase() {}

    public synchronized String save(String url) {
        String key =RandomStringUtils.randomAlphanumeric(10);
        UrlRecord value = new UrlRecord();
        value.setUrl(url);
        Collection<String> collection= db.keySet();
        if(db.containsKey(value)==true){
            for (String k : collection) {
                UrlRecord obj = db.get(key);
                if (k != null) {
                    if (value.equals(obj)) {
                        return k;
                    }
                }
            }
        }
        while(db.get(key)!=null && db.containsKey(value)==false){
            key =RandomStringUtils.randomAlphanumeric(10);
        }
        db.put(key, value);
        return key;
    }

    public synchronized String get(String shortUrl) {
        UrlRecord value = db.get(shortUrl);
        value.inc();

        return value.getUrl();
    }

    public synchronized Collection<UrlRecord> getStats() {
        return db.values();
    }

    static public class UrlRecord {
        String url;
        long counter;

        public void inc() {
            counter++;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getCounter() {
            return counter;
        }

        public void setCounter(long counter) {
            this.counter = counter;
        }
    }
}
