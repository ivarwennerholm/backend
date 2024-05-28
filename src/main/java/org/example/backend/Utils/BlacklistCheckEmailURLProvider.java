package org.example.backend.Utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;

@Component
@RequiredArgsConstructor
public class BlacklistCheckEmailURLProvider {

    private BlacklistURLProvider blacklistURLProvider;
    private URL url;
    private String email;

    @Autowired
    public BlacklistCheckEmailURLProvider(BlacklistURLProvider blacklistURLProvider){
        this.blacklistURLProvider = blacklistURLProvider;
    }

    public URL getBlacklistCheckEmailURL(){
        return this.url;
    }

    public String getBlacklistCheckEmailURL_String(){
        return this.url.toString();
    }

    public boolean isCheckEmailURLAvailable() throws Exception {
        try {
            HttpURLConnection con = (HttpURLConnection) getBlacklistCheckEmailURL().openConnection();
            return con.getResponseCode() == HttpURLConnection.HTTP_OK; //http_ok: 200, http_notfound: 404
        } catch (Exception e){
            throw new Exception("Connection to blacklist check email api failed");
        }
    }

    public void setUrl(URL newUrl){
        this.url = newUrl;
    }

    public void setEmail(String email){
        this.email = email;
        try {
            url = new URL(blacklistURLProvider.getBlacklistUrl_String()+"check/"+email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
