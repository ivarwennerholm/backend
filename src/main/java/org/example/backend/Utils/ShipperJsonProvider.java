package org.example.backend.Utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.server.ExportException;

@Component
public class ShipperJsonProvider {
    private URL url;

    public ShipperJsonProvider() throws MalformedURLException {
         this.url = new URL("https://javaintegration.systementor.se/shippers");
    }

    public URL getShipperUrl(){
        return this.url;
    }

    public boolean isURLAvailable() throws Exception {
        try{
            HttpURLConnection con = (HttpURLConnection) getShipperUrl().openConnection();
            return con.getResponseCode() == HttpURLConnection.HTTP_OK; //http_ok: 200, http_notfound: 404
        } catch (Exception e){
            throw new Exception("Connection to shipping contractor server failed.");
        }

    }

    public void setUrl(URL newUrl){
        this.url = newUrl;
    }
}
