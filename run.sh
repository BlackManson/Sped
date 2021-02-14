#!/bin/bash

#Start the emulator
#/Users/lukaszkolodziej/Library/Android/sdk/emulator/emulator -avd Nexus_S_API_29 &
#EMULATOR_PID=$!

# Wait for Android to finish booting
#WAIT_CMD="/Users/lukaszkolodziej/Library/Android/sdk/platform-tools/adb wait-for-device shell getprop init.svc.bootanim"
#until $WAIT_CMD | grep -m 1 stopped; do
#  echo "Waiting..."
#  sleep 1
#done

# Unlock the Lock Screen
#$ANDROID_SDK/platform-tools/adb shell input keyevent 82

# Clear and capture logcat
/Users/lukaszkolodziej/Library/Android/sdk/platform-tools/adb logcat -c
/Users/lukaszkolodziej/Library/Android/sdk/platform-tools/adb logcat > build/logcat.log &
LOGCAT_PID=$!

# Run the tests
./gradlew connectedAndroidTest -i

# Stop the background processes
kill $LOGCAT_PID
# kill $EMULATOR_PID
