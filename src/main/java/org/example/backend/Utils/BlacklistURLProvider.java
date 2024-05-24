package org.example.backend.Utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.backend.Service.Impl.BlacklistService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Component
@AllArgsConstructor
public class BlacklistURLProvider {

    private URL url;

    public BlacklistURLProvider(){

        try {
            url = new URL("https://javabl.systementor.se/api/stefan/blacklist");
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
