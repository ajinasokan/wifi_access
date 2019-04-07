package com.ajinasokan.wifi_access;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.HashMap;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import static android.content.ContentValues.TAG;
import static android.content.Context.WIFI_SERVICE;

/** WifiAccessPlugin */
public class WifiAccessPlugin implements MethodCallHandler {
  final String TAG= "WiFiAccessPlugin";
  final Context context;

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "wifi_access");
    channel.setMethodCallHandler(new WifiAccessPlugin(registrar.context()));
  }

  WifiAccessPlugin(Context ctx) {
    context = ctx;
  }


  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getDHCP")) {
      result.success(getDHCP());
    } else {
      result.notImplemented();
    }
  }

  public HashMap<String, String> getDHCP() {
    HashMap<String, String> result;
    result = new HashMap<>();

    result.put("ip", "0.0.0.0");
    result.put("gateway", "0.0.0.0");
    result.put("netmask", "0.0.0.0");
    result.put("broadcast", "0.0.0.0");

    try {
      WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
      DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
      result.put("ip", intToIp(dhcpInfo.ipAddress));
      result.put("gateway", intToIp(dhcpInfo.serverAddress));
      result.put("netmask", intToIp(dhcpInfo.netmask));
      result.put("broadcast", intToIp((dhcpInfo.ipAddress & dhcpInfo.netmask) | ~dhcpInfo.netmask));
      return result;
    } catch (Exception e) {
      Log.e(TAG, e.getMessage());
    }
    return result;
  }

  String intToIp(int i) {
    return (i & 0xFF) + "." +
            ((i >> 8) & 0xFF) + "." +
            ((i >> 16) & 0xFF) + "." +
            ((i >> 24) & 0xFF);
  }
}
