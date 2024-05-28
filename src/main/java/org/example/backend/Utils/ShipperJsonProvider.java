package org.example.backend.Utils;

import org.example.backend.Configurations.IntegrationsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.server.ExportException;

import static org.example.backend.BackendApplication.*;

@Component
public class ShipperJsonProvider {

    private final IntegrationsProperties integrations;

    private URL url;

    //@Autowired
    public ShipperJsonProvider(IntegrationsProperties integrations)  {
        this.integrations = integrations;
        System.out.println(ANSI_GREEN + "HERE!" + ANSI_RESET);
        try {
            this.url = new URL(integrations.getShippersUrl());
        } catch (MalformedURLException e) {
            System.out.println(ANSI_RED + "ERROR!" + ANSI_RESET);
            e.printStackTrace();
        }
        // this.url = new URL("https://javaintegration.systementor.se/shippers");
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
