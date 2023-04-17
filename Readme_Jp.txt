************************************************************
      StarPRNT SDK Ver 5.16.0 20230331
         Readme_Jp.txt                  スター精密（株）
************************************************************

    1. 概要
    2. 変更点
    3. 内容
    4. 適用
    5. 制限事項
    6. 著作権

==========
 1. 概要
==========

    本パッケージはStarプリンタを使用するアプリケーションの開発を容易にするためのSDKです。
    詳細な説明は、ドキュメント(documents/UsersManual.url)を参照ください。

=============================
 2. 変更点
=============================

    [StarIO]
        - 機能追加
            * mC-Label3に対応
            * StarBluetoothManager : StarBluetoothSecurityにSSP_NUMERIC_COMPARISONを追加
        - SM-S210i, SM-T300i, SM-T300, SM-T400iのBluetoothモジュール情報を新規追加

==========
 3. 内容
==========

    StarPRNT_Android_SDK_V5.16.0_20230331
    |- Readme_En.txt                          // リリースノート (英語)
    |- Readme_Jp.txt                          // リリースノート (日本語)
    |- Changelog_En.txt                       // 変更履歴 (英語)
    |- Changelog_Jp.txt                       // 変更履歴 (日本語)
    |- SoftwareLicenseAgreement.pdf           // ソフトウエア使用許諾書 (英語)
    |- SoftwareLicenseAgreement_Jp.pdf        // ソフトウエア使用許諾書 (日本語)
    |- SoftwareLicenseAgreementAppendix.pdf   // ソフトウエア使用許諾書付録 (英語)
    |
    |- documents
    |  +- UsersManual.url                     // StarPRNT SDK ドキュメントへのリンク
    |
    +- software
    |  |- examples
    |  |   |- 0_StarPRNT                      // 印刷やプリンターの検索などのサンプルプログラム (Ver 5.16.0)
    |  |   |- 1_StarIODeviceSetting           // SteadyLAN設定サンプルプログラム (Ver 1.0.0)
    |  |   |
    |  +- libs
    |      |- StarIOPort3.1.jar                // StarIOPort3.1.jar (Ver 2.11.0)
    |      |- starioextension.jar              // starioextension.jar (Ver 1.15.1)
    |      +- StarIODeviceSetting.aar          // StarIODeviceSetting.aar (Ver 1.0.0)
    |
    +- tools
       +- StarSoundConverter                   // メロディスピーカー向け音声変換ツール (Ver 1.0.0)

==========
 4. 適用
==========

    対応OS・開発環境・対応プリンターについては、
    ドキュメント(documents/UsersManual.url)を参照ください。

===============
 5. 制限事項
===============

    1. Android5.0でUSBプリンタを接続した場合、最後に接続した1台としか通信できません。

    2. SM-L200では、BluetoothセキュリティをPinCode有効でお使いください。

    3. Bluetooth I/Fでご利用の場合、メモリスイッチの"ASB機能"は、デフォルト設定(無効)のままご利用ください。
        (バンク7/ビットC)

    4. 弊社での動作確認の中で、Android Nを搭載したNexus9にUSBデバイス（弊社USBプリンタを含む）を接続した時、
        まれにAndroid OSがハングアップする現象が確認されています。
        この場合、OS再起動により復帰することが出来ます。

    5. Android端末によってはBluetoothインターフェイスで大量のデータ(※)を送信する場合、印刷が遅くなったり、
        間欠印字が発生したりすることがあります。
        その際にはデータを分割し、送信間隔をあけることで改善される場合があります。
        ※例えば、appendBitmapメソッドで生成されたデータ

    6. プリンターの電源をONした後にプリンターにmC-Soundを接続した場合、メロディスピーカーのAPIは正常に動作しません。
        プリンターにmC-Soundを接続した後にプリンターの電源をONしてください。

    7. releasePortメソッドの挙動の変更
        - StarIOPort3.1.jar V2.6.0 (StarPRNT SDK V5.11.0)より、releasePortメソッドの挙動を変更しました。
            V2.5.0以前:
                releasePortメソッドを呼ぶ前にgetPortメソッドを複数回呼んだ場合は、
                getPortメソッドを呼んだ回数分releasePortメソッドを呼ぶことでポートがクローズされる。
            V2.6.0以降:
                releasePortメソッドを呼んだ場合は、getPortメソッドを呼んだ回数に関わらず必ずポートがクローズされる。

===========
 6. 著作権
===========

    スター精密（株）Copyright 2016-2023
