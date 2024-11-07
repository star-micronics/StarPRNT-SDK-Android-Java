# Ver.5.19.0 (2024/11/01)

## StarIO
- Android15をサポート
- 機能追加
    * TSP100IV-UEWB, TSP100IV-UEWB SKに対応

## StarIOExtension
- Android15をサポート

## StarIODeviceSetting
- Android15をサポート

## SDK
- Android15をサポート
- サンプルコードの追加
    * TSP100IV-UEWB, TSP100IV-UEWB SKに対応

stario (Ver. 2.13.0) <br>
starioextension (Ver. 1.16.1)<br>
StarIODeviceSetting (Ver. 1.0.3)


# Ver.5.18.0 (2024/06/24)

## StarIO
- 機能追加
    * BSC10IIに対応（日本国内では販売しておりません）

## StarIOExtension
- 機能追加
    * ICommandBuilderクラス : CutPaperAction.TearOff
    * ICommandBuilderクラス : InternationalType.India

## SDK
- サンプルコードの追加
    * BSC10IIに対応（日本国内では販売しておりません）


# Ver.5.17.1 (2024/02/09)

## stario
- Android14をサポート
- ライブラリの形式をJARからAARに変更。ライブラリのファイル名は以下のように変更されます。    
    * stario.aar (旧版 : StarIOPort3.1.jar)
- バグ修正
    * Android9以下の端末とプリンターとをAOA接続で利用するとき、読み取ったプリンターステータスが最新の状態でないことがある問題を修正。

## starioextension
- Android14をサポート
- ライブラリの形式をJARからAARに変更。ライブラリのファイル名は以下のように変更されます。    
    * starioextension.aar (旧版 : starioextension.jar)

## stariodevicesetting
- Android14をサポート
    * ライブラリのAAR化に伴う注釈
      ライブラリを手動リンクしている場合、app/build.gradleの記述は以下のようになります。
      
          dependencies {
              implementation(name: 'stario', ext: 'aar')
              ....
          }

## SDK
- Android14をサポート


# Ver.5.17.0 (2023/09/08)

## StarIO
- 動作に影響しない軽微な修正

## StarIODeviceSetting
- Android 12, 13をサポート

## SDK
- サンプルコードの追加
    * TSP100IV SKに対応
- StarIODeviceSetting サンプル
    * Android 12, 13をサポート

stario (Ver. 2.11.1)<br>
starioextension (Ver. 1.15.1)<br>
StarIODeviceSetting (Ver. 1.0.0)


# Ver.5.16.0 (2023/03/31)

## StarIO
- 機能追加
    * mC-Label3に対応
    * StarBluetoothManager : StarBluetoothSecurityにSSP_NUMERIC_COMPARISONを追加
        - SM-S210i, SM-T300i, SM-T300, SM-T400iのBluetoothモジュール情報を新規追加

stario (Ver. 2.11.0) <br>
starioextension (Ver. 1.15.1)<br>
StarIODeviceSetting (Ver. 1.0.0)


# Ver.5.15.1　 (2022/05/10)
## StarIO
- SM-L200のBluetoothモジュール情報を新規追加

## starioextension
- AndroidXを使用する設定のアプリで、Jetifierを利用しない場合にビルド出来ない問題を修正

stario (Ver. 2.10.1) <br>
starioextension (Ver. 1.15.1)<br>
StarIODeviceSetting (Ver. 1.0.0)


# Ver.5.15.0 (2021/11/09)

## StarIO
- 機能追加
    * TSP100IVに対応
- Android 12対応
    * Android 12にてUSB通信をする際のAndroid OS仕様変更に対応

## smcloudservices
- サポート終了

## SDK
- サンプルコードの追加
    * TSP100IVに対応
    * Android 12をターゲットとする場合の新しいBluetooth権限に対応

stario (Ver. 2.10.0)<br>
starioextension (Ver. 1.15.0)<br>
StarIODeviceSetting (Ver. 1.0.0)


# Ver.5.14.0 (2020/12/09)

## StarIODeviceSetting example
- 新規作成

## StarIODeviceSetting
- 新規作成


## Ver.5.14.0 (2020/06/17)

## StarIO
- 機能追加
    * MCP31C, TSP650IISKに対応
    * インターフェイス自動切り替え機能の追加

## starioextension
- 機能追加
    * MCP31C, TSP650IISKに対応
    * インターフェイス自動切り替え機能の追加
    * StarIoExtManager, IPeripheralCommandParserのUSB HIDクラス（キーボードモード）対応
 
 ## SDK
- サンプルコードの追加
    * インターフェイス自動切り替え機能の実装例

stario (Ver. 2.9.0) <br>
starioextension (Ver. 1.15.0) <br>
smcloudservices (Ver. 1.4.1)<br>
StarIODeviceSetting (Ver. 1.0.0)


# Ver 5.13.0 (2019/12/25)

## StarIO
- 内部処理改善

## SDK
- プロジェクト構成の変更
    * 各ライブラリのMaven対応

## マニュアル
- StarPRNT SDKユーザーズマニュアルをPDFからオンラインマニュアルへのリンクに変更

stario (Ver. 2.8.0)<br>
starioextension (Ver. 1.14.0)<br>
smcloudservices (Ver. 1.4.1)