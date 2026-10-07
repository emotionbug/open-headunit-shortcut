# Open Headunit Shortcut

Open [Open Headunit](https://github.com/andreknieriem/open-headunit) with the **NAV** button on a compatible Android head unit. Inspired by [CarLinkShortcut](https://github.com/zalnaRs/CarLinkShortcut) by zalnaRs.

This small app launches Open Headunit and immediately closes itself. It has no background service, network access, audio output or audio-focus requests.

## Install and use

1. Install Open Headunit on the head unit.
2. Download and install **OpenHeadunitShortcut-1.0.0.apk** from [Releases](https://github.com/emotionbug/open-headunit-shortcut/releases/latest).
3. In the **head unit's own settings**, select **Open Headunit Shortcut** as the default navigation app. On some Joying/FYT/Xima units this is under **Settings → General → Default navigation app**; the name and location vary by firmware.
4. Replace any previous assignment of **Open Headunit** itself with **Open Headunit Shortcut**.
5. Press **NAV**. You can also tap the shortcut's launcher icon.

Open Headunit must already be installed and enabled in the same Android user/profile. If it cannot be opened, the shortcut shows a short message. No root or Shizuku is required.

## Compatibility

- **Android 4.1 (API 16) or newer**, matching Open Headunit's GitHub APK minimum. Its Play Store flavor has a higher minimum of API 21; this APK covers both.
- Compile/target SDK **36**, matching Open Headunit at the time of this release.
- One universal APK, with no native libraries or CPU-architecture restriction.
- Opens `com.andrerinas.headunitrevived`, the application ID used by the current Open Headunit GitHub and Play Store builds, including 3.2.6 and 3.5.0-beta3.
- The head unit must allow a third-party app to be selected for its navigation button. Hard-coded OEM allowlists cannot be bypassed by this app.

Selecting a separate shortcut package may help on firmware that classifies its selected navigation app's audio differently. **It is not a guaranteed audio-routing fix:** firmware can apply navigation mode more broadly, and this app does not change routing or volume. It does not handle map destinations or replace Open Headunit's Android Auto connection setup.

## Build

Install JDK 17 or newer and Android SDK 36. Set `ANDROID_HOME` or put `sdk.dir=/path/to/android-sdk` in an untracked `local.properties` file.

```sh
./gradlew assembleDebug lint
./gradlew assembleRelease
```

Debug APK: `app/build/outputs/apk/debug/app-debug.apk`.
Unsigned release APK: `app/build/outputs/apk/release/app-release-unsigned.apk`.

To align, sign and verify with an existing private release key:

```sh
export SHORTCUT_KEYSTORE=/secure/path/release.jks
export SHORTCUT_PASSWORD_FILE=/secure/path/password.txt
# Optional: SHORTCUT_KEY_ALIAS (default: release), SHORTCUT_KEY_PASSWORD_FILE
./tools/sign_release.sh
```

The signed APK is written to `app/build/outputs/signed/OpenHeadunitShortcut.apk`. Keep the same key for future updates. Never commit signing keys or passwords. Published APKs in this repository use a dedicated release key, not a debug key or the Open Headunit key.

## License and credits

GPL-3.0; see [LICENSE](LICENSE). The NAV-button shortcut concept comes from zalnaRs' GPL-3.0 [CarLinkShortcut](https://github.com/zalnaRs/CarLinkShortcut). This implementation resolves Open Headunit's launcher activity and uses its supported Android version range. The icon is original to this project.
