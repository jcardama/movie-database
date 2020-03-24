####################################################################################################
####################################################################################################
####################################################################################################
######################################### PROGUARD #################################################
####################################################################################################
####################################################################################################
####################################################################################################

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-ignorewarnings
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception
-dontwarn org.jetbrains.annotations.**
-dontwarn javax.lang.**
-dontwarn javax.tools.**
-dontwarn javax.script.**
-dontwarn javax.annotation.**
-dontwarn autovalue.shaded.com.**
-dontwarn org.w3c.dom.**
-dontwarn org.joda.time.**
-dontwarn org.shaded.apache.**
-dontwarn org.ietf.jgss.**
-dontwarn com.firebase.**
-dontnote com.firebase.client.core.GaePlatform
-dontwarn com.google.auto.value.**
-dontwarn javax.servlet.**
-dontwarn sun.misc.**
-dontwarn autovalue.shaded.org.apache.commons.**
-dontwarn org.apache.log4j.**
-dontwarn org.apache.log.**
-dontwarn org.apache.velocity.**
-dontwarn org.apache.tools.**
-dontwarn org.jdom.**
-dontwarn org.java.lang.**
-dontwarn com.raizlabs.android.dbflow.**

# Optimization
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification

# Kotlin
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#Gson
-dontwarn sun.misc.**
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

#Stetho
-dontwarn com.facebook.stetho.**
