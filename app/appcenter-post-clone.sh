#!/usr/bin/env bash

# Example: Get required frameworks.
curl -OL https://github.com/ytabuchi/KudanAR-assets/raw/master/android/KudanAR.aar
mv -f ./KudanAR.aar ../KudanAR/KudanAR.aar
ls ../KudanAR/

curl -OL https://github.com/ytabuchi/KudanAR-assets/raw/master/assets/cloud.mp4
mv -f ./cloud.mp4 ./src/main/assets/cloud.mp4
curl -OL https://github.com/ytabuchi/KudanAR-assets/raw/master/assets/waves.mp4
mv -f ./cloud.mp4 ./src/main/assets/waves.mp4
curl -OL https://github.com/ytabuchi/KudanAR-assets/raw/master/assets/kaboom.mp4
mv -f ./cloud.mp4 ./src/main/assets/kaboom.mp4
ls ./src/main/assets/
