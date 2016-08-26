package com.jewelbao.library.utils;
/**
 * Copyright 2014 Zhenguo Jin
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.regex.Pattern;

import javax.security.auth.x500.X500Principal;

/**
 * APP工具类
 * APP相关信息工具类。获取版本信息
 *
 * @author jingle1267@163.com
 * @modify kevin
 */
@SuppressWarnings("unused")
public final class AppUtil {

	private static final boolean DEBUG = true;
	private static final String TAG = "AppUtil";


	/**
	 Don't let anyone instantiate this class.
	 */
	private AppUtil() {
		throw new Error("Do not need instantiate!");
	}

	/**
	 获取IMEI号，IESI号，手机型号
	 */
	@SuppressWarnings("deprecation")
	public static String getAllInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("主板:").append(Build.BOARD);
		sb.append(",系统启动程序版本号:").append(Build.BOOTLOADER);
		sb.append("\n系统定制商:").append(Build.BRAND);
		sb.append(",cpu指令集:").append(Build.CPU_ABI);
		sb.append(",cpu指令集2:").append(Build.CPU_ABI2);
		sb.append("\n设置参数:").append(Build.DEVICE);
		sb.append("\n显示屏参数:").append(Build.DISPLAY);
		sb.append("\n硬件识别码(指纹):").append(Build.FINGERPRINT);
		sb.append("\n硬件名称:").append(Build.HARDWARE);
		sb.append(",HOST:").append(Build.HOST);
		sb.append(",修订版本列表:").append(Build.ID);
		sb.append("\n硬件制造商:").append(Build.MANUFACTURER);
		sb.append("\n机型:").append(Build.MODEL);
		sb.append(",硬件序列号:").append(Build.SERIAL);
		sb.append(",手机制造商:").append(Build.PRODUCT);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			sb.append(",无线电固件版本:").append(Build.getRadioVersion());
		} else {
			sb.append(",无线电固件版本:").append(Build.RADIO);
		}
		sb.append(",描述Build的标签:").append(Build.TAGS);
		sb.append(",TIME:").append(Build.TIME);
		sb.append(",builder类型:").append(Build.TYPE);
		sb.append(",USER:").append(Build.USER);
		sb.append("\n基带版本:").append(Build.VERSION.INCREMENTAL);
		sb.append("\nSDK:").append(Build.VERSION.SDK);
		sb.append(",SDK_INT:").append(Build.VERSION.SDK_INT);
		sb.append("\nRELEASE:").append(Build.VERSION.RELEASE);
		sb.append(",CODENAME:").append(Build.VERSION.CODENAME);

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			sb.append("\nBASE_OS:").append(Build.VERSION.BASE_OS);
			sb.append("\nSECURITY_PATCH:").append(Build.VERSION.SECURITY_PATCH);
			sb.append("\nPREVIEW_SDK_INT:").append(Build.VERSION.PREVIEW_SDK_INT);
		}
		return sb.toString();
	}

	/**
	 * 获取应用权限
	 *
	 * @param context 上下文
	 * @param packageName 应用包名
	 * @return 权限集合
	 */
	public static String[] getAppPermission(Context context, String packageName) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
			return packageInfo.requestedPermissions;
		} catch(NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取应用名称
	 *
	 * @param context 上下文
	 * @param packageName 应用包名
	 * @return 应用名称
	 */
	public static String getAppName(Context context, String packageName) {
		try {
			PackageManager pm = context.getPackageManager();
			ApplicationInfo info = pm.getApplicationInfo(packageName, 0);
			return info.loadLabel(pm).toString();
		} catch(NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取包名
	 *
	 * @param context 上下文
	 * @return 包名
	 */
	public static String getPackageName(Context context) {
		return context.getPackageName();
	}

	/**
	 * 获取当前应用图标
	 *
	 * @param context 上下文
	 * @return 当前应用图标
	 */
	public static Drawable getIcon(Context context) {
		return getAppIcon(context, getPackageName(context));
	}

	/**
	 *获取应用图标
	 *
	 * @param context 上下文
	 * @param packageName 包名
	 * @return 应用图标
	 */
	public static Drawable getAppIcon(Context context, String packageName) {
		try {
			PackageManager pm = context.getPackageManager();
			ApplicationInfo info = pm.getApplicationInfo(packageName, 0);
			return info.loadIcon(pm);
		} catch(NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 获取设备名称
	 @return 设备名称
	 */
	public static String getDeviceName() {
		return Build.MODEL;
	}

	/**
	 获取android系统版本号
	 @return android系统版本号
	 */
	public static String getVersionRelease() {
		return Build.VERSION.RELEASE;
	}

	@SuppressWarnings("deprecation")
	public static String getCPU() {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			return Build.SUPPORTED_ABIS[0] + Build.SUPPORTED_ABIS[1];
		} else {
			return Build.CPU_ABI;
		}
	}

	/**
	 获取设备IMEI
	 @param context 上下文
	 @return 设备IMEI
	 */
	public static String getDeviceID(Context context) {
		TelephonyManager mTm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return  mTm.getDeviceId();
	}

	/**
	 得到软件版本号

	 @param context 上下文

	 @return 当前版本Code
	 */
	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			String packageName = context.getPackageName();
			verCode = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
		} catch(PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return verCode;
	}


	/**
	 获取应用运行的最大内存

	 @return 最大内存
	 */
	public static long getMaxMemory() {
		long memory = Runtime.getRuntime().maxMemory() / (1024 * 1024 * 1024);
		if(memory < 1) {
			memory = Runtime.getRuntime().maxMemory() / (1024 * 1024);
		}
		return memory;
	}

	/**
	 得到软件显示版本信息

	 @param context 上下文

	 @return 当前版本信息
	 */
	public static String getVerName(Context context) {
		String verName = "";
		try {
			String packageName = context.getPackageName();
			verName = context.getPackageManager().getPackageInfo(packageName, 0).versionName;
		} catch(PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return verName;
	}


	/**
	 安装apk

	 @param context 上下文
	 @param file    APK文件
	 */
	public static void installApk(Context context, File file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}


	/**
	 安装apk
	 @param context 上下文
	 @param file    APK文件uri
	 */
	public static void installApk(Context context, Uri file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(file, "application/vnd.android.package-archive");
		context.startActivity(intent);
	}


	/**
	 卸载apk

	 @param context     上下文
	 @param packageName 包名
	 */
	public static void uninstallApk(Context context, String packageName) {
		Intent intent = new Intent(Intent.ACTION_DELETE);
		Uri packageURI = Uri.parse("package:" + packageName);
		intent.setData(packageURI);
		context.startActivity(intent);
	}


	/**
	 检测服务是否运行

	 @param context   上下文
	 @param className 类名

	 @return 是否运行的状态
	 */
	public static boolean isServiceRunning(Context context, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> servicesList = activityManager
				.getRunningServices(Integer.MAX_VALUE);
		for(RunningServiceInfo si : servicesList) {
			if(className.equals(si.service.getClassName())) {
				isRunning = true;
			}
		}
		return isRunning;
	}


	/**
	 停止运行服务

	 @param context   上下文
	 @param className 类名

	 @return 是否执行成功
	 */
	public static boolean stopRunningService(Context context, String className) {
		Intent intent_service = null;
		boolean ret = false;
		try {
			intent_service = new Intent(context, Class.forName(className));
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(intent_service != null) {
			ret = context.stopService(intent_service);
		}
		return ret;
	}


	/**
	 得到CPU核心数

	 @return CPU核心数
	 */
	public static int getNumCores() {
		try {
			File dir = new File("/sys/devices/system/cpu/");
			File[] files = dir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return Pattern.matches("cpu[0-9]", pathname.getName());
				}
			});
			return files.length;
		} catch(Exception e) {
			return 1;
		}
	}

	/**
	 * 获取CPU最大频率, Unit KHZ.
	 *
	 * @return CPU最大频率
	 */
	public static String getCpuMaxFreq() {
		String result = "N/A";
		ProcessBuilder cmd;
		try {
			String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
			cmd = new ProcessBuilder(args);
			Process process = cmd.start();
			InputStream in = process.getInputStream();
			byte[] re = new byte[24];
			while (in.read(re) != -1) {
				result = result + new String(re);
			}
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result.trim();
	}

	/**
	 * 获取CPU最小频率, Unit KHZ.
	 *
	 * @return CPU最小频率
	 */
	public static String getCpuMinFreq() {
		String result = "N/A";
		ProcessBuilder cmd;
		try {
			String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
			cmd = new ProcessBuilder(args);
			Process process = cmd.start();
			InputStream in = process.getInputStream();
			byte[] re = new byte[24];
			while (in.read(re) != -1) {
				result = result + new String(re);
			}
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result.trim();
	}

	/**
	 *	获取CPU当前频率, Unit KHZ.
	 *
	 * @return CPU当前频率
	 */
	@SuppressWarnings("resource")
	public static String getCpuCurFreq() {
		String result = "N/A";
		try {
			FileReader fr = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			result = text.trim();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取CPU名称
	 *
	 * @return cpu名
	 */
	@SuppressWarnings("resource")
	public static String getCpuName() {
		try {
			FileReader fr = new FileReader("/proc/cpuinfo");
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			String[] array = text.split(":\\s+", 2);
			for (int i = 0; i < array.length; i++) {
			}
			return array[1];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 whether this process is named with processName

	 @param context     上下文
	 @param processName 进程名

	 @return 是否含有当前的进程
	 */
	public static boolean isNamedProcess(Context context, String processName) {
		if(context == null || TextUtils.isEmpty(processName)) {
			return false;
		}

		int pid = android.os.Process.myPid();
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> processInfoList = manager.getRunningAppProcesses();
		if(processInfoList == null) {
			return true;
		}

		for(RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
			if(processInfo.pid == pid && processName.equalsIgnoreCase(processInfo.processName)) {
				return true;
			}
		}
		return false;
	}


	/**
	 whether application is in background <ul> <li>need use permission android.permission.GET_TASKS
	 in Manifest.xml</li> </ul>

	 @param context 上下文

	 @return if application is in background return true, otherwise return false
	 */
	@SuppressWarnings("deprecation")
	public static boolean isApplicationInBackground(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> taskList = am.getRunningTasks(1);
		if(taskList != null && !taskList.isEmpty()) {
			ComponentName topActivity = taskList.get(0).topActivity;
			if(topActivity != null && !topActivity.getPackageName()
					.equals(context.getPackageName())) {
				return true;
			}
		}
		return false;
	}


	/**
	 获取应用签名

	 @param context 上下文
	 @param pkgName 包名

	 @return 返回应用的签名
	 */
	public static String getSign(Context context, String pkgName) {
		try {
			PackageInfo pis = context.getPackageManager()
					.getPackageInfo(pkgName, PackageManager.GET_SIGNATURES);
			return hexdigest(pis.signatures[0].toByteArray());
		} catch(NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}


	/**
	 将签名字符串转换成需要的32位签名

	 @param paramArrayOfByte 签名byte数组

	 @return 32位签名字符串
	 */
	private static String hexdigest(byte[] paramArrayOfByte) {
		final char[] hexDigits = {
				48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(paramArrayOfByte);
			byte[] arrayOfByte = localMessageDigest.digest();
			char[] arrayOfChar = new char[32];
			for(int i = 0, j = 0; ; i++, j++) {
				if(i >= 16) {
					return new String(arrayOfChar);
				}
				int k = arrayOfByte[i];
				arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
				arrayOfChar[++j] = hexDigits[(k & 0xF)];
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}


	/**
	 清理后台进程与服务

	 @param context 应用上下文对象context

	 @return 被清理的数量
	 */
	public static int gc(Context context) {
		long i = getDeviceUsableMemory(context);
		int count = 0; // 清理掉的进程数
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		// 获取正在运行的service列表
		List<RunningServiceInfo> serviceList = am.getRunningServices(100);
		if(serviceList != null) {
			for(RunningServiceInfo service : serviceList) {
				if(service.pid == android.os.Process.myPid())
					continue;
				try {
					android.os.Process.killProcess(service.pid);
					count++;
				} catch(Exception e) {
					e.getStackTrace();
				}
			}
		}

		// 获取正在运行的进程列表
		List<RunningAppProcessInfo> processList = am.getRunningAppProcesses();
		if(processList != null) {
			for(RunningAppProcessInfo process : processList) {
				// 一般数值大于RunningAppProcessInfo.IMPORTANCE_SERVICE的进程都长时间没用或者空进程了
				// 一般数值大于RunningAppProcessInfo.IMPORTANCE_VISIBLE的进程都是非可见进程，也就是在后台运行着
				if(process.importance > RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
					// pkgList 得到该进程下运行的包名
					String[] pkgList = process.pkgList;
					for(String pkgName : pkgList) {
						//noinspection StatementWithEmptyBody
						if(DEBUG) {
							// TODO: 2016/6/14 0014 do some in debug mode
						}
						try {
							am.killBackgroundProcesses(pkgName);
							count++;
						} catch(Exception e) { // 防止意外发生
							e.getStackTrace();
						}
					}
				}
			}
		}
		//noinspection StatementWithEmptyBody
		if(DEBUG) {
			// TODO: 2016/6/14 0014 do some in debug mode
		}
		return count;
	}


	/**
	 获取设备的可用内存大小

	 @param context 应用上下文对象context

	 @return 当前内存大小
	 */
	public static int getDeviceUsableMemory(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo mi = new MemoryInfo();
		am.getMemoryInfo(mi);
		// 返回当前系统的可用内存
		return (int) (mi.availMem / (1024 * 1024));
	}

	/**
	 获取手机系统SDK版本

	 @return 如API 17 则返回 17
	 */
	public static int getSDKVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}


	/**
	 是否Dalvik模式

	 @return 结果
	 */
	public static boolean isDalvik() {
		return "Dalvik".equals(getCurrentRuntimeValue());
	}


	/**
	 是否ART模式

	 @return 结果
	 */
	public static boolean isART() {
		String currentRuntime = getCurrentRuntimeValue();
		return "ART".equals(currentRuntime) || "ART debug build".equals(currentRuntime);
	}

	/**
	 获取Mac地址 require permissions wifi access
	 @param context
	 @return
	 */
	@SuppressWarnings("all")
	public static String getMacAddress(Context context) {
		//在wifi未开启状态下，仍然可以获取MAC地址，但是IP地址必须在已连接状态下否则为0
		String macAddress = null, ip = null;
		WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = (null == wifiMgr ?
				null :
				wifiMgr.getConnectionInfo());
		if(null != info) {
			macAddress = info.getMacAddress();
		}
		return macAddress;
	}


	/**
	 * 获取手机当前的Runtime
	 *
	 * @return 正常情况下可能取值Dalvik, ART, ART debug build;
	 */
	public static String getCurrentRuntimeValue() {
		try {
			Class<?> systemProperties = Class.forName("android.os.SystemProperties");
			try {
				Method get = systemProperties.getMethod("get", String.class, String.class);
				if(get == null) {
					return "WTF?!";
				}
				try {
					final String value = (String) get
							.invoke(systemProperties, "persist.sys.dalvik.vm.lib",
						/* Assuming default is */"Dalvik");
					if("libdvm.so".equals(value)) {
						return "Dalvik";
					} else if("libart.so".equals(value)) {
						return "ART";
					} else if("libartd.so".equals(value)) {
						return "ART debug build";
					}

					return value;
				} catch(IllegalAccessException e) {
					return "IllegalAccessException";
				} catch(IllegalArgumentException e) {
					return "IllegalArgumentException";
				} catch(InvocationTargetException e) {
					return "InvocationTargetException";
				}
			} catch(NoSuchMethodException e) {
				return "SystemProperties.get(String key, String def) method is not found";
			}
		} catch(ClassNotFoundException e) {
			return "SystemProperties class is not found";
		}
	}


	private final static X500Principal DEBUG_DN = new X500Principal(
			"CN=Android Debug,O=Android,C=US");


	/**
	 * 检测当前应用是否是Debug版本
	 *
	 * @param ctx 上下文
	 * @return 是否是Debug版本
	 */
	public static boolean isDebuggable(Context ctx) {
		boolean debuggable = false;
		try {
			PackageInfo packageInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_SIGNATURES);
			Signature signatures[] = packageInfo.signatures;
			for(Signature signature : signatures) {
				CertificateFactory cf = CertificateFactory.getInstance("X.509");
				ByteArrayInputStream stream = new ByteArrayInputStream(signature.toByteArray());
				X509Certificate cert = (X509Certificate) cf.generateCertificate(stream);
				debuggable = cert.getSubjectX500Principal().equals(DEBUG_DN);
				if(debuggable)
					break;
			}
		} catch(NameNotFoundException | CertificateException e) {
			e.printStackTrace();
		}
		return debuggable;
	}
}