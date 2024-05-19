package org.example.backend.Utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class ShipperJsonProvider {
    private URL url;

    public ShipperJsonProvider() throws MalformedURLException {
         this.url = new URL("https://javabl.systementor.se/api/stefan/blacklist");
    }

    public URL getShipperUrl(){
        return this.url;
    }

    public boolean isURLAvailable() throws IOException {
        HttpURLConnection con = (HttpURLConnection) getShipperUrl().openConnection();
        return con.getResponseCode() == HttpURLConnection.HTTP_OK; //http_ok: 200, http_notfound: 404
    }

    public void setUrl(URL newUrl){
        this.url = newUrl;
    }
}
