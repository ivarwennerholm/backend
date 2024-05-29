package org.example.backend.Utils;

import lombok.AllArgsConstructor;
import org.example.backend.Configurations.IntegrationsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Component
@AllArgsConstructor
public class BlacklistURLProvider {

    private final IntegrationsProperties integrations;

    private URL url;

    @Autowired
    public BlacklistURLProvider(IntegrationsProperties integrations){
        this.integrations = integrations;
        try {
            url = new URL(integrations.getBlacklistUrl());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }
    public URL getBlacklistURL(){
        return this.url;
    }

    public String getBlacklistUrl_String(){
        return this.url.toString();
    }

    public boolean isURLAvailable() throws Exception {
        try{
            HttpURLConnection con = (HttpURLConnection) getBlacklistURL().openConnection();
            return con.getResponseCode() == HttpURLConnection.HTTP_OK; //http_ok: 200, http_notfound: 404
        } catch (Exception e){
            throw new Exception("Connection to blacklist server failed");
        }
    }

    public void setUrl(URL newUrl){
        this.url = newUrl;
    }

}
