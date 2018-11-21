package com.dgtz.api.live.switcher.beans;

/**
 * BroCast.
 * Copyright: Sardor Navruzov
 * 2013-2016.
 */

/*
{
"action": "on_publish",
"client_id": 1985,
"ip": "192.168.1.10",
"vhost": "video.test.com",
"app": "live",
"stream": "livestream"
}
*/
public class Publish {
    private String action;
    private Long client_id;
    private String ip;
    private String vhost;
    private String app;
    private String stream;


    public Publish() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getVhost() {
        return vhost;
    }

    public void setVhost(String vhost) {
        this.vhost = vhost;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    @Override
    public String toString() {
        return "Publish{" +
                "action='" + action + '\'' +
                ", client_id=" + client_id +
                ", ip='" + ip + '\'' +
                ", vhost='" + vhost + '\'' +
                ", app='" + app + '\'' +
                ", stream='" + stream + '\'' +
                '}';
    }
}
