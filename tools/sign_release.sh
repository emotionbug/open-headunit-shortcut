#!/usr/bin/env bash
# Password files and the keystore belong outside the source repository.
set -euo pipefail
: "${ANDROID_HOME:?Set ANDROID_HOME to your Android SDK}"
: "${SHORTCUT_KEYSTORE:?Set SHORTCUT_KEYSTORE to your release keystore}"
: "${SHORTCUT_PASSWORD_FILE:?Set SHORTCUT_PASSWORD_FILE to its password file}"
script_root="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)"
build_tools="$ANDROID_HOME/build-tools/${SHORTCUT_BUILD_TOOLS:-36.0.0}"
output_dir="$script_root/app/build/outputs/signed"
mkdir -p "$output_dir"
"$build_tools/zipalign" -f 4 \
    "$script_root/app/build/outputs/apk/release/app-release-unsigned.apk" \
    "$output_dir/aligned.apk"
# With no separate key password, apksigner tries the keystore password first.
# Reusing one password file for both flags would consume two lines from it.
key_password_args=()
if [[ -n "${SHORTCUT_KEY_PASSWORD_FILE:-}" ]]; then
    key_password_args=(--key-pass "file:$SHORTCUT_KEY_PASSWORD_FILE")
fi
"$build_tools/apksigner" sign \
    --ks "$SHORTCUT_KEYSTORE" \
    --ks-key-alias "${SHORTCUT_KEY_ALIAS:-release}" \
    --ks-pass "file:$SHORTCUT_PASSWORD_FILE" \
    "${key_password_args[@]}" \
    --out "$output_dir/OpenHeadunitShortcut.apk" \
    "$output_dir/aligned.apk"
"$build_tools/apksigner" verify --verbose --min-sdk-version 16 \
    "$output_dir/OpenHeadunitShortcut.apk"
"$build_tools/zipalign" -c 4 "$output_dir/OpenHeadunitShortcut.apk"
printf 'Signed APK: %s\n' "$output_dir/OpenHeadunitShortcut.apk"
