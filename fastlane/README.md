fastlane documentation
================
# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```
xcode-select --install
```

Install _fastlane_ using
```
[sudo] gem install fastlane -NV
```
or alternatively using `brew cask install fastlane`

# Available Actions
## Android
### android unit_tests
```
fastlane android unit_tests
```
Run unit tests
### android debug_build
```
fastlane android debug_build
```
Build a debug APK
### android slack_apk_build
```
fastlane android slack_apk_build
```
Build a signed release APK & deploy to slack
### android beta
```
fastlane android beta
```
Deploy latest Beta build to Crashlytics Beta
### android distribute
```
fastlane android distribute
```
Deploy latest Beta build to Firebase App Distribution
### android deploy
```
fastlane android deploy
```
Deploy latest version to Google Play

----

This README.md is auto-generated and will be re-generated every time [fastlane](https://fastlane.tools) is run.
More information about fastlane can be found on [fastlane.tools](https://fastlane.tools).
The documentation of fastlane can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
