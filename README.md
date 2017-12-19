# end-user-programming-for-internet-of-things
A mobile app that follows End User Programming for Internet of Things (EUP4IOT) paradigm to support creating digital sticky notes

Import all 3 projects

actionbarsherlock
AndroidHorizontalListView
RemindMe

All projects should share the same systems 'android-support-v4.jar'

Download actionbarsherlock from the website Replace both actionbarsherlock and TinyGSN's 'android-support-v4' with the systems jar located in:

1. Make sure you have downloaded the Android Support Library using the SDK Manager.
1. Create a libs/ directory in the root of your application project.
1. Copy the JAR file from your Android SDK installation directory (e.g., <sdk>/extras/android/support/v4/android-support-v4.jar) into your application's project libs/ directory.
1. Right click the JAR file and select Build Path > Add to Build Path.

RemindMe project all should make reference to other 2 projects in project.properties file:

* android.library.reference.1=..\\actionbarsherlock
* android.library.reference.2=..\\AndroidHorizontalListView


NEW Instructions due to incompatability of new Gradle

1. Pls uninstall you current android studio as well the android SDK
1. Install Andorid studio Android Studio 2.1.0.9 https://android-studio.en.uptodown.com/windows/download/527645
1. Download the zip files from: https://github.com/ngcharithperera/end-user-programming-for-internet-of-things/tree/56e714d91379971af1908c0ff0e86f35b607b9d5
1. Extract the zip and delete 'RemindMe4AndroidStudio' (so you should endup with 3 folders)
1. Import with Gradle
1. Install as gradel and SDK as prompted
1. Import actionbarsherlock as module (File -> Import Module)


