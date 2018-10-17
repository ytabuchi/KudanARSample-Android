#!/usr/bin/env bash

echo $KUDANAR_PATH
echo $TEST_PATH

# Example: Get required frameworks.
curl -OL $KUDANAR_PATH
mv -f ./KudanAR.aar ../KudanAR/KudanAR.aar
ls ../KudanAR/

curl -OL https://github.com/ytabuchi/KudanAR-assets/raw/master/assets/cloud.mp4
mv -f ./cloud.mp4 ./src/main/assets/cloud.mp4
curl -OL https://github.com/ytabuchi/KudanAR-assets/raw/master/assets/waves.mp4
mv -f ./waves.mp4 ./src/main/assets/waves.mp4
curl -OL https://github.com/ytabuchi/KudanAR-assets/raw/master/assets/kaboom.mp4
mv -f ./kaboom.mp4 ./src/main/assets/kaboom.mp4
ls ./src/main/assets/
