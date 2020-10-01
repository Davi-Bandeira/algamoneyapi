package com.example.algamoney.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperty {

    private String originPermited = "http://localhost:8000";

    private final Segurity segurity = new Segurity();

    public Segurity getSegurity() {
        return segurity;
    }

    public String getOriginPermited() {
        return originPermited;
    }

    public void setOriginPermited(String originPermited) {
        this.originPermited = originPermited;
    }

    public static class Segurity{

        private boolean enableHttps;

        public boolean isEnableHttps() {
            return enableHttps;
        }

        public void setEnableHttps(boolean enableHttps) {
            this.enableHttps = enableHttps;
        }
    }

}
