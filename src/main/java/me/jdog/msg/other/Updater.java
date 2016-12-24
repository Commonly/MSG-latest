package me.jdog.msg.other;

import me.jdog.msg.Main;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.net.URL;

/**
 * Created by Josh on 12/24/16.
 */
public class Updater {
    final static String VERSION_URL = "https://api.spiget.org/v2/resources/31708/versions?size=" + Integer.MAX_VALUE + "&spiget__ua=SpigetDocs";
    final static String DESCRIPTION_URL = "https://api.spiget.org/v2/resources/31708/updates?size=" + Integer.MAX_VALUE + "&spiget__ua=SpigetDocs";

    public static Object[] getLastUpdate() {
        try {
            JSONArray versionArray = (JSONArray) JSONValue.parseWithException(IOUtils.toString(new URL(String.valueOf(VERSION_URL))));
            Double lastVersion = Double.parseDouble(((JSONObject) versionArray.get(versionArray.size() - 1)).get("name").toString());
            if(lastVersion > Double.parseDouble(Main.getInstance().getDescription().getVersion())) {
                JSONArray updatesArray = (JSONArray) JSONValue.parseWithException(IOUtils.toString(new URL(String.valueOf(DESCRIPTION_URL))));
                String updateName = ((JSONObject) updatesArray.get(updatesArray.size() - 1)).get("title").toString();
                Object[] update = {lastVersion, updateName};
                return update;
            }
        } catch (Exception e) {
            return new String[0];
        }
        return new String[0];
    }
}
