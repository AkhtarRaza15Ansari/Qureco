# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/akhtarraza/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#-keep class org.apache.http.** { *; }
#-keep class android.net.http.AndroidHttpClient.** { *; }
#-keep class com.google.android.gms.** { *; }
#-keep class com.squareup.picasso.** { *; }
#-keep class com.facebook.android.** { *; }

#-dontwarn org.apache.http.**
#-dontwarn android.net.http.AndroidHttpClient
#-dontwarn com.google.android.gms.**
#-dontwarn com.squareup.picasso.**
#-dontwarn com.facebook.android.**