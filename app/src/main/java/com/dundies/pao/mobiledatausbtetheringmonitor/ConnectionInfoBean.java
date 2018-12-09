package com.dundies.pao.mobiledatausbtetheringmonitor;

/**
 * Created by Pao on 12/8/2018.
 */

public class ConnectionInfoBean {

    private String provider;

    private String dataDownloaded;

    private String dataUploaded;

    public ConnectionInfoBean(String provider, String dataDownloaded, String dataUploaded) {
        this.provider = provider;
        this.dataDownloaded = dataDownloaded;
        this.dataUploaded = dataUploaded;
    }

    public ConnectionInfoBean() {
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getDataDownloaded() {
        return dataDownloaded;
    }

    public void setDataDownloaded(String dataDownloaded) {
        this.dataDownloaded = dataDownloaded;
    }

    public String getDataUploaded() {
        return dataUploaded;
    }

    public void setDataUploaded(String dataUploaded) {
        this.dataUploaded = dataUploaded;
    }

    public static ConnectionInfoBean createNotConnectedInfo() {
        return new ConnectionInfoBean("Nothing", "0 bytes", "0 bytes");
    }
}
