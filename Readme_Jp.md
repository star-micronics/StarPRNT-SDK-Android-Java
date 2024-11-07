
# StarPRNT SDK Android Java

本パッケージはStarプリンタを使用するアプリケーションの開発を容易にするためのSDKです。

## 適用

対応OS・開発環境・対応プリンターについては、[StarPRNT SDKの開発者向けドキュメント](https://www.star-m.jp/starprntsdk-oml-android.html)を参照ください。

## 制限事項

1. SM-L200では、BluetoothセキュリティをPinCode有効でお使いください。

2. Bluetooth I/Fでご利用の場合、メモリスイッチの"ASB機能"は、デフォルト設定(無効)のままご利用ください。(バンク7/ビットC)

3. 弊社での動作確認の中で、Android Nを搭載したNexus9にUSBデバイス（弊社USBプリンタを含む）を接続した時、まれにAndroid OSがハングアップする現象が確認されています。この場合、OS再起動により復帰することが出来ます。

4. Android端末によってはBluetoothインターフェイスで大量のデータ(※)を送信する場合、印刷が遅くなったり、間欠印字が発生したりすることがあります。その際にはデータを分割し、送信間隔をあけることで改善される場合があります。※例えば、appendBitmapメソッドで生成されたデータ

5. プリンターの電源をONした後にプリンターにmC-Soundを接続した場合、メロディスピーカーのAPIは正常に動作しません。プリンターにmC-Soundを接続した後にプリンターの電源をONしてください。

6. releasePortメソッドの挙動の変更
StarIOPort3.1.jar V2.6.0 (StarPRNT SDK V5.11.0)より、releasePortメソッドの挙動を変更しました。

   - V2.5.0以前:
      releasePortメソッドを呼ぶ前にgetPortメソッドを複数回呼んだ場合は、
      getPortメソッドを呼んだ回数分releasePortメソッドを呼ぶことでポートがクローズされる。
   - V2.6.0以降:
      releasePortメソッドを呼んだ場合は、getPortメソッドを呼んだ回数に関わらず必ずポートがクローズされる。

## 著作権

スター精密（株）Copyright 2016-2024
