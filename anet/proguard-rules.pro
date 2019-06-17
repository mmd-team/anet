-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-dontoptimize
-dontpreverify
-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

#google推荐算法
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
# 包含了native()方法保留类名和该类成员
-keepclasseswithmembernames class * {
    native <methods>;
}
# keep View子类的get和set方法
# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}
# 保留Activity子类中参数为View的公开方法(主要用于xml文件中的onClick标签能找到对应的方法)
# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
# 保留枚举的下面两个方法,这两个方法和反射相关
# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
# 保留Parcelable中的静态内部变量`CREATOR`
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}
# 保留所有R类的内部类中的静态变量
-keepclassmembers class **.R$* {
    public static <fields>;
}
# 因为support包里面包含了一些新平台的api, 如果当前平台比较老就会报错; 所以这里设置dontwarn.
# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**
# 如果用@Keep注解的就保留
# Understand the @Keep support annotation.
-keep class android.support.annotation.Keep
-keep @android.support.annotation.Keep class * {*;}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}

-keep class com.mmdteam.anet.lib.StringConverter {
    <methods>;
}

-keep class com.mmdteam.anet.lib.SubscribeUtils {
    <methods>;
}
