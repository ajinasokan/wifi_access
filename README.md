# wifi_access

A Flutter plugin to get WiFi related functions. This project uses snippets from [wifi_ip](https://github.com/luanvotrongdev/wifi_ip) project but targeting only android and adds gateway IP to the result.

## Getting Started

You will need the following permission in android:

```xml
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```

You can access WiFi information in your Flutter project like this:

```dart
import 'package:wifi_access/wifi_access.dart';

DHCP dhcp = await WifiAccess.dhcp;
print(dhcp.ip);
print(dhcp.gateway);
```