************************************************************
      StarPRNT SDK Ver 5.16.0 20230331
         Readme_En.txt             Star Micronics Co., Ltd.
************************************************************

    1. Overview
    2. Changes
    3. Contents
    4. Scope
    5. Limitation
    6. Copyright

=============
 1. Overview
=============

    This package contains StarPRNT SDK for supporting to develop applications for Star printers.
    Please refer to the document(documents/UsersManual.url) for details.

======================
 2. Changes
======================

    [StarIO]
        - Added features
            * Added mC-Label3.
            * StarBluetoothManager : Added SSP_NUMERIC_COMPARISON to StarBluetoothSecurity.
        - Added new Bluetooth module information for SM-S230i, SM-T300i, SM-T300 and SM-T400i.

=============
 3. Contents
=============

    StarPRNT_Android_SDK_V5.16.0_20230331
    |- Readme_En.txt                          // Release Notes (English)
    |- Readme_Jp.txt                          // Release Notes (Japanese)
    |- Changelog_En.txt                       // Changelog (English)
    |- Changelog_Jp.txt                       // Changelog (Japanese)
    |- SoftwareLicenseAgreement.pdf           // Software License Agreement (English)
    |- SoftwareLicenseAgreement_Jp.pdf        // Software License Agreement (Japanese)
    |- SoftwareLicenseAgreementAppendix.pdf   // Software License Agreement Appendix (English)
    |
    |- documents
    |  +- UsersManual.url                     // Link to StarPRNT SDK document
    |
    +- software
    |  |- examples
    |  |   |- 0_StarPRNT                      // Sample program for printing and searching function (Ver 5.16.0)
    |  |   |- 1_StarIODeviceSetting           // Sample program for SteadyLAN settings (Ver 1.0.0)
    |  |   |
    |  +- libs
    |      |- StarIOPort3.1.jar                // StarIOPort3.1.jar (Ver 2.11.0)
    |      |- starioextension.jar              // starioextension.jar (Ver 1.15.1)
    |      +- StarIODeviceSetting.aar          // StarIODeviceSetting.aar (Ver 1.0.0)
    |
    +- Others
       +- StarSoundConverter                  // Tools for converting sound format for melody speaker (Ver 1.0.0)

==========
 4. Scope
==========

    Please refer to UsersManual.url for supported OS, development environment, and supported printers.

===============
 5. Limitation
===============

    1. Only the last connected USB printer can communicate with Android V5.0.

    2. Please use "PIN code enable" in Bluetooth security with SM-L200.

    3. When using printer with Bluetooth Interface, please do not change the memory switch setting of "ASB Status"
        from default value(invalid). (Bank 7/Bit C)

    4. During our test, we have confirmed using Nexus9 with Android 7.0 may rarely cause a hang-up.
        To recover from this phenomenon, restart the OS.

    5. With some Android devices, the transmission of large amounts of data(*) via Bluetooth may cause slow
        printing or intermittent printing.
        The printing can be improved by splitting data and extending the transmission interval.
        (*)e.g., data created by appendBitmap method

    6. If mC-Sound was connected after the printer power was turned ON, melody speaker API does not work properly.
        Please turn on the printer after connecting mC-Sound to it.

    7. Change the behavior of releasePort method
        - Beginning from StarIOPort3.1.jar V2.6.0 (StarPRNT SDK V5.11.0), the releasePort method behavior has been changed as shown below.
            V2.5.0 and before:
                In the case of calling getPort method multiple times before calling releasePort method,
                the port is released by calling releasePort method the same number of times as the getPort method was called.
            V2.6.0 and later:
                The port is always released when releasePort method is called.

==============
 6. Copyright
==============

    Copyright 2016-2023 Star Micronics Co., Ltd. All rights reserved.

