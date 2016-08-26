# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\AndroidSDK/tools/proguard/proguard-android.txt
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
-keepclassmembers class com.nhzw.bingdu.fragment.NewsDetailFragment$JavascriptInterface {
   public *;
}

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify

-dontwarn com.bugtags.library.**
-dontwarn com.handmark.**
-dontwarn com.mob.**
-dontwarn com.xiaomi.**
-dontwarn com.umeng.**
-dontwarn in.srain.cube.**
-dontwarn android.support.**
-dontwarn com.google.**
-dontwarn twitter4j.**
-dontwarn oauth.**
-dontwarn org.**
-dontwarn com.distimo.**
-dontwarn com.adobe.**
-dontwarn com.scmp.newspulse.fragment.qrcode.**
-dontwarn net.sourceforge.zbar.**
-dontwarn com.enrique.stackblur.**
-dontwarn com.sina.weibo.sdk.**
-dontwarn com.baidu.**
-dontwarn com.litesuits.http.**
-dontwarn com.github.moduth.**
-dontwarn com.igexin.**

## so文件
#-libraryjars libs/arm64-v8a/libBaiduMapSDK_v3_5_0_31.so
-libraryjars libs/arm64-v8a/liblocSDK6a.so
-libraryjars libs/arm64-v8a/libtvhelper.so

#-libraryjars libs/armeabi-v7a/libBaiduMapSDK_v3_5_0_31.so
-libraryjars libs/armeabi-v7a/liblocSDK6a.so
#-libraryjars libs/armeabi-v7a/libblackcar.so

#-libraryjars libs/mips/liblocSDK6a.so

#-libraryjars libs/mips64/liblocSDK6a.so

#-libraryjars libs/x86/libBaiduMapSDK_v3_5_0_31.so
-libraryjars libs/x86/liblocSDK6a.so
#-libraryjars libs/x86/libblackcar.so
-libraryjars libs/x86/libtvhelper.so

#-libraryjars libs/x86_64/libBaiduMapSDK_v3_5_0_31.so
-libraryjars libs/x86_64/liblocSDK6a.so

#-libraryjars libs/armeabi/libBaiduMapSDK_v3_5_0_31.so
-libraryjars libs/armeabi/liblocSDK6a.so
#-libraryjars libs/armeabi/libbspatch.so
-libraryjars libs/armeabi/libtvhelper.so

-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepattributes RuntimeVisibleAnnotations, RuntimeInvisibleAnnotations, RuntimeVisibleParameterAnnotations, RuntimeInvisibleParameterAnnotations,AnnotationDefault
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.**
-keep public class com.android.vending.licensing.ILicensingService

-keep class android.app.** { *; }
-keep class android.support.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class com.google.gdata.util.common.base.** { *; }
-keep class oauth.signpost.** { *; }
-keep class twitter4j.** { *; }
-keep class cn.sharesdk.onekeyshare.** { *; }
-keep class cn.bingoogolapple.refreshlayout.** { *; }
-keep class com.handmark.pulltorefresh.library.** { *; }
-keep class cn.sharesdk.** { *; }
-keep class com.business.** { *; }
-keep class com.litesuits.http.**{ *; }
-keep class com.litesuits.orm.**{ *; }
-keep class com.github.moduth.**{ *; }
-keep class com.igexin.**{ *; }
-keep class com.igexin.getuiext.**{ *; }

-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation { *; }
-keep class com.ym.jitv.Common.ioc.** { *; }
-keep class com.ym.jitv.Common.ioc.IocEventListener { *; }
-keep class com.ym.jitv.Common.ioc.IocView { *; }
-keepclassmembers class ** {
 	public int id();
 	public String click(**);
 	public String longClick(**);
    public String itemClick(**);
    public String itemLongClick(**);
 }
 -keepclassmembers class ** {
  	public IocEventListener click(**);
  	public IocEventListener longClick(**);
    public IocEventListener itemClick(**);
    public IocEventListener itemLongClick(**);
  }

-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

## 友盟
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
-keep class com.umeng.** { *; }


-keep public class com.yu.jitv.R$*{
public static final int *;
}

## ----------------------------------
##      AliPay
## ----------------------------------
-dontskipnonpubliclibraryclassmembers
-dontwarn com.alipay.**
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.mobilesecuritysdk.*
-keep class com.ut.*


## ----------------------------------
##   ########## Gson混淆    ##########
## ----------------------------------
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.ym.jitv.Model.** { *; }
-keep class com.ym.jitv.Model.ApiModel { *; }
-keep class com.ym.jitv.Model.BaseModel { *; }
-keep class com.ym.jitv.Model.BoxListServer { *; }
-keep class com.ym.jitv.Model.PlayListServer { *; }
-keep class com.ym.jitv.Model.ServerResponse { *; }
-keep class com.ym.jitv.Model.MITVAppList { *; }
-keep class com.ym.jitv.Model.CATTVAppList { *; }
-keep class com.ym.jitv.Model.ImageText { *; }
-keep class com.ym.jitv.Model.PublishTalkModel { *; }

-keep class com.ym.jitv.Model.IM.ChatInfo {*;}
-keep class com.ym.jitv.Model.IM.ChatInfoList {*;}
-keep class com.ym.jitv.Model.IM.ChatMessage {*;}
-keep class com.ym.jitv.Model.IM.ChatMessageList {*;}
-keep class com.ym.jitv.Model.IM.ChatSendStatus {*;}
-keep class com.ym.jitv.Model.IM.Friend {*;}
-keep class com.ym.jitv.Model.IM.FriendList {*;}
-keep class com.ym.jitv.Model.IM.PushResult {*;}
-keep class com.ym.jitv.Model.IM.VerifyMessage {*;}
-keep class com.ym.jitv.Model.IM.TimeInfo {*;}

-keep class com.ym.jitv.Model.User.UserLogin { *; }
-keep class com.ym.jitv.Model.User.VerifyCode { *; }
-keep class com.ym.jitv.Model.User.AppInfo { *; }
-keep class com.ym.jitv.Model.User.PlayRecord { *; }
-keep class com.ym.jitv.Model.User.UserDeviceInfo { *; }

-keep class com.ym.jitv.Model.HttpParam.PlayListParam { *; }
-keep class com.ym.jitv.Model.HttpParam.UserLoginParam { *; }
-keep class com.ym.jitv.Model.HttpParam.ChatParam { *; }

-keep class com.ym.jitv.Http.JS.JitvAppClass { *; }

-keep class **$Properties

## ----------------------------------
##      sharesdk
## ----------------------------------
-keep class android.net.http.SslError
-keep class android.webkit.**{*;}
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class com.alipay.share.sdk.**{*;}
-keep class **.R$* {
    *;
}

## ----------------------------------
##      Eventbus
## ----------------------------------

-keepclassmembers class ** {
    public void onEvent*(**);
}
-keepclassmembers class ** {
 	public void EventLocation(**);
 	public void EventPush(**);
    public void EventMessage(**);
    public void EventWaitDelivery(**);
    public void EventWaitEvaluate(**);
    public void EventWaitReceive(**);
    public void EventWithdraw(**);
 }


-keep class com.baidu.** { *; }
-keep class com.baidu.location.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}
-keep class com.litesuits.http.**{*;}


-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keep class **.R$* { *; }

-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}

-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
 public static **[] values();
 public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
 public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class * extends android.app.Activity {  # 保持自定义控件类不被混淆
    public void *(android.view.View);
}

-keepclassmembers class * extends android.support.v4.app.FragmentActivity {  # 保持自定义控件类不被混淆
    public void *(android.view.View);
}

