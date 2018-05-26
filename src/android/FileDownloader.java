package com.greek.cordova.plugin;
// The native Toast API
import android.widget.Toast;
import android.os.*;
// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.net.URL;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileDownloader extends CordovaPlugin {
  boolean DEBUG = false;
  public void DEBUGGER(String message){
    if (DEBUG) {
      // Toast toast = Toast.makeText(cordova.getActivity(), message, Toast.LENGTH_SHORT);
        // Display toast
      // toast.show();
      System.out.println("DOWNLOADER LOGGS::" + message);
    }
  }
  @Override
  public boolean execute(String action, JSONArray args,
    final CallbackContext callbackContext) {
      // Verify that the user sent a 'show' action
      if (!action.equals("download")) {
        callbackContext.error("\"" + action + "\" is not a recognized action.");
        return false;
      }
      URL url;
      File root;
      File dir;
      File file;
      InputStream in = null;
      String folder = "download";
      String fileURL = null;
      String filename = "download";
      FileOutputStream fos = null;
      try {
        JSONObject payload = args.getJSONObject(0);
        if (payload.has("folder")) {
            folder = payload.getString("folder");
        }
        if (payload.has("filename")) {
            filename = payload.getString("filename");
        }
        if(payload.has("fileurl")) {
            fileURL = payload.getString("fileurl");
        }
        if(filename == null || fileURL == null || filename == null) {
           callbackContext.error("Error in getting one of options: (filename, fileurl, filename)");
            // Error CB
        }
        try {
          DEBUGGER(payload.getString("url"));
          url = new URL(fileURL);
          HttpURLConnection connection = (HttpURLConnection)url.openConnection();
          connection.connect();
          int responseCode = connection.getResponseCode();
          if (responseCode != HttpURLConnection.HTTP_OK) {
              callbackContext.error("Error in fetching the url" + fileURL + "with response code" + responseCode);
              return false;
          }

          in = url.openStream();
          root = android.os.Environment.getExternalStorageDirectory();
          dir = new File (root.getAbsolutePath() + "/"+folder);
          dir.mkdirs();
          file = new File(dir, filename);
          fos = new FileOutputStream(file);
          int length = -1;
          byte[] buffer = new byte[1024];
          while ((length = in.read(buffer)) > -1) {
              fos.write(buffer, 0, length);
          }
          fos.close();
          in.close();
        } catch(Exception e) {
            callbackContext.error("Error in plugin as " + e.getMessage());
        }
      } catch (Exception e) {
        // TODO Auto-generated catch block
          callbackContext.error("Error in plugin as " + e.getMessage());
      }
      PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
      callbackContext.sendPluginResult(pluginResult);
      return true;
  }
}
