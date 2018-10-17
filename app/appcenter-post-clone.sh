#!/usr/bin/env bash

echo $KUDANAR_PATH
echo $CLOUD_PATH
echo $WAVE_PATH
echo $KABOOM_PATH

# Get Kudan Android frameworks.
curl -OL $KUDANAR_PATH
mv -f ./KudanAR.aar ../KudanAR/KudanAR.aar
ls ../KudanAR/

# Get required mp4 files.
curl -OL $CLOUD_PATH
mv -f ./cloud.mp4 ./src/main/assets/cloud.mp4
curl -OL $WAVE_PATH
mv -f ./waves.mp4 ./src/main/assets/waves.mp4
curl -OL $KABOOM_PATH
mv -f ./kaboom.mp4 ./src/main/assets/kaboom.mp4
ls ./src/main/assets/
