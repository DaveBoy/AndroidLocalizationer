package language_engine.google;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GoogleTranslationer {
    private ExecutorService fixedThreadPool;
    private CountDownLatch countDownLatch;
    private List<String> result;
    private static int COUNT = 0;

    public GoogleTranslationer(List<String> urls) {
        this.fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.countDownLatch = new CountDownLatch(urls.size());
        COUNT = urls.size();
        this.result = new ArrayList<>();
    }

    public List<String> googleTranslate(List<String> urls) {
        if (urls != null && urls.size() > 0) {
            for (String url : urls) {
                fixedThreadPool.execute(new HttpGetRunnable(url));
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        }
        return result;
    }


    private class HttpGetRunnable implements Runnable {

        String url;

        public HttpGetRunnable(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            String resp = httpGet(url);
            if (resp != null){
            }
        }
    }

    private static String httpGet(String url) {
        HttpClient httpClient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = httpResponse.getEntity();
                return EntityUtils.toString(entity, "utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;

    }

}
