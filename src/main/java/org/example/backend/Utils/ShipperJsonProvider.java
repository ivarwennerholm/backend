package org.example.backend.Utils;

import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class ShipperJsonProvider {
    public URL getShipperUrl() throws MalformedURLException {
        return new URL("https://javaintegration.systementor.se/shippers");
    }
}
