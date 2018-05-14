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

public class PDFplugin extends CordovaPlugin {
  boolean DEBUG = false;
  public void showToast(String message){
    if (DEBUG) {
      Toast toast = Toast.makeText(cordova.getActivity(), message, Toast.LENGTH_SHORT);
        // Display toast
      toast.show();
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
      FileOutputStream fos = null;
      try {
        JSONObject payload = args.getJSONObject(0);
        try {
          showToast(payload.getString("url"));
          url = new URL(payload.getString("url"));
          in = url.openStream();
          root = android.os.Environment.getExternalStorageDirectory();
          dir = new File (root.getAbsolutePath() + "/download");
          dir.mkdirs();
          file = new File(dir, payload.getString("fileName"));
          fos = new FileOutputStream(file);
        } catch(Exception e) {
          showToast("file stream error" + e.getMessage());
        }
        int length = -1;
        byte[] buffer = new byte[1024];// buffer for portion of data from connection
        showToast("will start wrtiting file");
        while ((length = in.read(buffer)) > -1) {
            fos.write(buffer, 0, length);
        }
        showToast("Writing done. Closing Stream");
        fos.close();
        in.close();
        System.out.println("CAtch Error in Success");
      } catch (Exception e) {
        // TODO Auto-generated catch block
        showToast("Exception in writing file" + e.getMessage());
        System.out.println("CAtch Error in exception");
      }
      showToast("End of function calling");
      PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
      callbackContext.sendPluginResult(pluginResult);
      return true;
  }
}
