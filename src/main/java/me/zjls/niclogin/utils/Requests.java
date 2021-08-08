package me.zjls.niclogin.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import me.zjls.niclogin.Main;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.json.JSONObject;

public class Requests {

    private Main plugin;
    public CookieStore cs;
    public String site;

    public Requests(Main plugin) {
        this.plugin= plugin;
        this.cs = new BasicCookieStore();
        this.site = plugin.getConfig().getString("site");
    }

    private CloseableHttpAsyncClient get() {
        return HttpAsyncClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
                .setDefaultCookieStore(this.cs).build();
    }

    public void login(String username, String password, FutureCallback<HttpResponse> callback) {
        CloseableHttpAsyncClient client = get();
        client.start();

        JSONObject data = new JSONObject();

        data.put("identification", username);
        data.put("password", password);
//        data.put("lifetime", 604800);
        try {
            HttpPost post = new HttpPost(this.site + "/api/token");
            StringEntity params = new StringEntity(data.toString());

            post.setHeader("content-type", "application/json");
            post.setEntity(params);
            client.execute(post, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
