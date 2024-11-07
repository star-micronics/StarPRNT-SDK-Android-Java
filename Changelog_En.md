# Ver.5.19.0 (2024/11/01)

## StarIO
- Support Android 15
- Added Features
    * Supported TSP100IV-UEWB, TSP100IV-UEWB SK.

## StarIOExtension
- Support Android15

## StarIODeviceSetting
- Support Android15

## SDK
- Support Android 15
- Added Sample Codes
    * Supported TSP100IV-UEWB, TSP100IV-UEWB SK.

stario (Ver. 2.13.0) <br>
starioextension (Ver. 1.16.1)<br>
StarIODeviceSetting (Ver. 1.0.3)


# V5.18.0 (2024/06/24)

## SDK
- Added Sample Codes
  * Supported BSC10II (Limited region model).

## StarIO
- Added features
  * Supported BSC10II (Limited region model).

## StarIOExtension
- Added Sample Codes
  * Supported BSC10II (Limited region model).


# V5.17.1 (2024/02/09)

## stario
- Support Android14
- Changed the library format from JAR to AAR. The library file name has be changed as follows.
  * stario.aar (old name : StarIOPort3.1.jar)
- Fixed an issue.
  * Fixed an issue where the obtained printer status may not be the latest.
    (When using Android 9 or lower devices and a printer with AOA connection)

## starioextension
- Support Android14
- Changed the library format from JAR to AAR. The library file name has be changed as follows.
  * starioextension.aar (old name : starioextension.jar)

## stariodevicesetting
- Support Android14

* Note on changing the library format to AAR<br>
  When linking libraries manually, the description of app/build.gradle is as follows.

        dependencies {
             implementation(name: 'stario', ext: 'aar')
             ....
        }

## SDK
- Support Android14


# V5.17.0 (2023/09/08)

## stario
- Minor fixes that do not affect operation.

## stariodevicesetting
- Support Android 12, 13.

## SDK
- Added Sample Codes
  * Supported TSP100IV SK.


stario (Ver. 2.11.1) <br>
starioextension (Ver. 1.15.1)<br>
StarIODeviceSetting (Ver. 1.0.0)


# V5.16.0 (2023/03/31)

## StarIO
- Added features
  * Added mC-Label3.
  * `StarBluetoothManager` : Added `SSP_NUMERIC_COMPARISON` to `StarBluetoothSecurity`.
- Added new Bluetooth module information for SM-S230i, SM-T300i, SM-T300 and SM-T400i.

stario (Ver. 2.11.0) <br>
starioextension (Ver. 1.15.1)<br>
StarIODeviceSetting (Ver. 1.0.0)


# V5.15.1 (2022/05/10)

## StarIO
- Added new Bluetooth module information for SM-L200.

## starioextension
- Fixed an issue where apps configured to use AndroidX could not be built if Jetifier was not used.

stario (Ver. 2.10.1) <br>
starioextension (Ver. 1.15.1)<br>
StarIODeviceSetting (Ver. 1.0.0)


# V5.15.0 (2021/11/09)

## StarIO
- Added features
  * Added TSP100IV.
- Support Android 12
  * Support for Android OS specification change when using USB communication.

## smcloudservices
- End of support

## SDK
- Added Sample Codes
  * Added TSP100IV.
  * Support for new Bluetooth permission request when targeting Android 12.

stario (Ver. 2.10.0) <br>
starioextension (Ver. 1.15.0)<br>
StarIODeviceSetting (Ver. 1.0.0)


# V5.14.0 (2020/12/09)

## StarIODeviceSetting example
- New

## StarIODeviceSetting
- New


# V5.14.0 (2020/06/17)

## StarIO
- Added features
  * Added MCP31C and TSP650IISK.
  * Added Auto Interface Select function.

## starioextension
- Added features
  * Added MCP31C and TSP650IISK.
  * Added Auto Interface Select function.
  * USB HID class (Keyboard mode) support for StarIoExtManager and IPeripheralCommandParser.

## SDK
- Added Sample Codes
  * Added sample codes for Auto Interface Select function.

stario (Ver. 2.9.0) <br>
starioextension (Ver. 1.15.0) <br>
smcloudservices (Ver. 1.4.1) <br>


# V5.13.0 (2019/12/25)

## StarIO
- Improved internal processing

## SDK
- Changed project structure
  * Maven support for each library.

## Manual
- Changed StarPRNT SDK User's Manual from PDF to online manual link

stario (Ver. 2.8.0) <br>
starioextension (Ver. 1.14.0) <br>
smcloudservices (Ver. 1.4.1)
