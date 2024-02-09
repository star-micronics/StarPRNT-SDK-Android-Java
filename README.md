# StarPRNT SDK Android Java

This package contains StarPRNT SDK for supporting to develop applications for Star printers.

## Scope

Please refer to the [StarPRNT SDK document](https://www.star-m.jp/starprntsdk-oml-android.html) for supported OS, development environment, and supported printers.

## Limitation
1. Only the last connected USB printer can communicate with Android V5.0.

2. Please use "PIN code enable" in Bluetooth security with SM-L200.

3. When using printer with Bluetooth Interface, please do not change the memory switch setting of "ASB Status" from default value(invalid). (Bank 7/Bit C)

4. During our test, we have confirmed using Nexus9 with Android 7.0 may rarely cause a hang-up. To recover from this phenomenon, restart the OS.

5. With some Android devices, the transmission of large amounts of data(*) via Bluetooth may cause slow printing or intermittent printing. The printing can be improved by splitting data and extending the transmission interval.  
e.g., data created by appendBitmap method

6. If mC-Sound was connected after the printer power was turned ON, melody speaker API does not work properly. Please turn on the printer after connecting mC-Sound to it.

7. Change the behavior of releasePort method  
Beginning from StarIOPort3.1.jar V2.6.0 (StarPRNT SDK V5.11.0), the releasePort method behavior has been changed as shown below.
    - V2.5.0 and before:  
    In the case of calling getPort method multiple times before calling releasePort method, the port is released by calling releasePort method the same number of times as the getPort method was called.
    - V2.6.0 and later:  
    The port is always released when releasePort method is called.

## Copyright

Copyright 2016-2024 Star Micronics Co., Ltd. All rights reserved.
