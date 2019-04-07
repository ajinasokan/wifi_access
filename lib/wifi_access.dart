import 'dart:async';

import 'package:flutter/services.dart';

class DHCP {
  final String ip;
  final String gateway;
  final String netmask;
  final String broadcast;

  DHCP({
    this.ip = "0.0.0.0",
    this.gateway = "0.0.0.0",
    this.netmask = "0.0.0.0",
    this.broadcast = "0.0.0.0",
  });

  DHCP.fromMap(Map<String, String> map)
      : ip = map["ip"],
        gateway = map["gateway"],
        netmask = map["netmask"],
        broadcast = map["broadcast"];
}

class WifiAccess {
  static const MethodChannel _channel = const MethodChannel('wifi_access');

  static Future<DHCP> get dhcp async {
    return DHCP.fromMap(
        Map<String, String>.from(await _channel.invokeMethod('getDHCP')));
  }
}
