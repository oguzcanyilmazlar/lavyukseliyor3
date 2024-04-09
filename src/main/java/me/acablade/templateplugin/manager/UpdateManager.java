package me.acablade.templateplugin.manager;

import me.acablade.templateplugin.TemplatePlugin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class UpdateManager {

    private final String CURR_VERSION = TemplatePlugin.getPlugin(TemplatePlugin.class).getPluginMeta().getVersion();
    private final LogManager logManager;
    private final String url;


    public UpdateManager(String url, LogManager logManager){
        this.url = url;
        this.logManager = logManager;
    }

    public UpdateManager(String url){
        this(url, null);
    }

    public boolean check(){
        String ver = read();

        if(ver.isEmpty()) return false;
        if(ver.equals(CURR_VERSION)) return false;

        return true;
    }

    private String read(){
        try {
            URL url = new URL(this.url);
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            String encoding = "UTF-8";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            int len;
            while ((len = in.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            String body = new String(baos.toByteArray(), encoding);
            return body;
        } catch (IOException e) {
            if(logManager != null) logManager.err(String.format("Error when checking update (%s)", e.getMessage()));
            return "";
        }
    }

}
