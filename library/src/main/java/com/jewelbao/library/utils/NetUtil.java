package com.jewelbao.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

//跟网络相关的工具类
@SuppressWarnings("all")
public class NetUtil
{
	private static final String ETHERNET = "eth0";
	private static final String WLAN = "wlan0";

	private static final String DNS1 = "[net.dns1]";
	private static final String DNS2 = "[net.dns2]";
	private static final String ETHERNET_GATEWAY = "[dhcp.eth0.gateway]";
	private static final String WLAN_GATEWAY = "[dhcp.wlan0.gateway]";
	private static final String ETHERNET_MASK = "[dhcp.eth0.mask]";
	private static final String WLAN_MASK = "[dhcp.wlan0.mask]";

	private NetUtil()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 获取WIFI SSID
	 */
	public static String getWifiSSID(Context context)
	{
		WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		int wifiState = wifiMgr.getWifiState();
		WifiInfo info = wifiMgr.getConnectionInfo();
		return info != null ?
				info.getSSID() :
				null;
	}

	/**
	 判断网络是否已连接

	 @param context
	 */
	public static boolean isConnected(Context context)
	{

		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if(null != connectivity)
		{

			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if(null != info && info.isConnected())
			{
				if(info.getState() == NetworkInfo.State.CONNECTED)
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 判断是否是wifi连接
	 */
	public static boolean isWifi(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if(cm == null)
			return false;
		if(cm.getActiveNetworkInfo() == null)
			return false;
		return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

	}

	/**
	 打开网络设置界面
	 */
	public static void openSetting(Activity activity)
	{
		activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
		//        Intent intent = new Intent("/");
		//        ComponentName cm = new ComponentName("com.android.settings",
		//                "com.android.settings.WirelessSettings");
		//        intent.setComponent(cm);
		//        intent.setAction("android.intent.action.VIEW");
		//        activity.startActivityForResult(intent, 0);
	}


	public static final String NETWORK_TYPE_WIFI = "wifi";
	public static final String NETWORK_TYPE_3G = "eg";
	public static final String NETWORK_TYPE_2G = "2g";
	public static final String NETWORK_TYPE_WAP = "wap";
	public static final String NETWORK_TYPE_UNKNOWN = "unknown";
	public static final String NETWORK_TYPE_DISCONNECT = "disconnect";

	/**
	 Get network type

	 @param context context

	 @return 网络状态
	 */
	public static int getNetworkType(Context context)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager == null ?
				null :
				connectivityManager.getActiveNetworkInfo();
		return networkInfo == null ?
				-1 :
				networkInfo.getType();
	}

	/**
	 Get network type name

	 @param context context

	 @return NetworkTypeName
	 */
	public static String getNetworkTypeName(Context context)
	{
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo;
		String type = NETWORK_TYPE_DISCONNECT;
		if(manager == null || (networkInfo = manager.getActiveNetworkInfo()) == null)
		{
			return type;
		}
		;

		if(networkInfo.isConnected())
		{
			String typeName = networkInfo.getTypeName();
			if("WIFI".equalsIgnoreCase(typeName))
			{
				type = NETWORK_TYPE_WIFI;
			} else if("MOBILE".equalsIgnoreCase(typeName))
			{
				String proxyHost = android.net.Proxy.getDefaultHost();
				type = TextUtils.isEmpty(proxyHost) ?
						(isFastMobileNetwork(context) ?
								NETWORK_TYPE_3G :
								NETWORK_TYPE_2G) :
						NETWORK_TYPE_WAP;
			} else
			{
				type = NETWORK_TYPE_UNKNOWN;
			}
		}
		return type;
	}

	/**
	 Whether is fast mobile network

	 @param context context

	 @return FastMobileNetwork
	 */
	private static boolean isFastMobileNetwork(Context context)
	{
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if(telephonyManager == null)
		{
			return false;
		}

		switch(telephonyManager.getNetworkType())
		{
			case TelephonyManager.NETWORK_TYPE_1xRTT:
				return false;
			case TelephonyManager.NETWORK_TYPE_CDMA:
				return false;
			case TelephonyManager.NETWORK_TYPE_EDGE:
				return false;
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
				return true;
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
				return true;
			case TelephonyManager.NETWORK_TYPE_GPRS:
				return false;
			case TelephonyManager.NETWORK_TYPE_HSDPA:
				return true;
			case TelephonyManager.NETWORK_TYPE_HSPA:
				return true;
			case TelephonyManager.NETWORK_TYPE_HSUPA:
				return true;
			case TelephonyManager.NETWORK_TYPE_UMTS:
				return true;
			case TelephonyManager.NETWORK_TYPE_EHRPD:
				return true;
			case TelephonyManager.NETWORK_TYPE_EVDO_B:
				return true;
			case TelephonyManager.NETWORK_TYPE_HSPAP:
				return true;
			case TelephonyManager.NETWORK_TYPE_IDEN:
				return false;
			case TelephonyManager.NETWORK_TYPE_LTE:
				return true;
			case TelephonyManager.NETWORK_TYPE_UNKNOWN:
				return false;
			default:
				return false;
		}
	}

	/**
	 * Get local ipv4 address
	 *
	 * @return
	 */
	public static String getLocalIPv4() {
		return getLocalIp(true);
	}

	/**
	 * Get local ipv6 address
	 *
	 * @return
	 */
	public static String getLocalIPv6() {
		return getLocalIp(false);
	}

	/**
	 * Get local ip address
	 *
	 * @param useIPv4
	 * @return
	 */
	private static String getLocalIp(boolean useIPv4) {
		try {
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			while (en.hasMoreElements()) {
				NetworkInterface nif = en.nextElement();
				Enumeration<InetAddress> inet = nif.getInetAddresses();
				while (inet.hasMoreElements()) {
					InetAddress addr = inet.nextElement();
					if (!addr.isLoopbackAddress()) {
						String ip = StringUtils.toUpperCase(addr.getHostAddress());
						boolean isIPv4 = addr instanceof Inet4Address;
						if (useIPv4) {
							if (isIPv4) {
								return ip;
							}
						} else {
							if (!isIPv4) {
								int delim = ip.indexOf('%');
								return delim < 0 ? ip : ip.substring(0, delim);
							}
						}
					}
				}

			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Get wlan mac address
	 *
	 * @return
	 */
	public static String getWlanMacAddress() {
		return getMacAddress(WLAN);
	}

	/**
	 * Get ethernet mac address
	 *
	 * @return
	 */
	public static String getEthernetMacAddress() {
		return getMacAddress(ETHERNET);
	}

	/**
	 * Get mac address
	 *
	 * @param interfaceName
	 * @return
	 */
	private static String getMacAddress(String interfaceName) {
		try {
			List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				if (interfaceName != null) {
					if (!intf.getName().equalsIgnoreCase(interfaceName))
						continue;
				}
				byte[] mac;
				mac = intf.getHardwareAddress();
				if (mac == null)
					return "";
				StringBuilder buf = new StringBuilder();
				for (int idx = 0; idx < mac.length; idx++)
					buf.append(String.format("%02X:", mac[idx]));
				if (buf.length() > 0)
					buf.deleteCharAt(buf.length() - 1);
				return buf.toString();
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Get dns1
	 *
	 * @return
	 */
	public static String getDNS1() {
		return getPropInfo(DNS1);
	}

	/**
	 * Get dns2
	 *
	 * @return
	 */
	public static String getDNS2() {
		return getPropInfo(DNS2);
	}

	/**
	 * Get ethernet gateway
	 *
	 * @return
	 */
	public static String getEthernetGateway() {
		return getPropInfo(ETHERNET_GATEWAY);
	}

	/**
	 * Get wlan gateway
	 *
	 * @return
	 */
	public static String getWlanGateway() {
		return getPropInfo(WLAN_GATEWAY);
	}

	/**
	 * Get ethernet mask
	 *
	 * @return
	 */
	public static String getEthernetMask() {
		return getPropInfo(ETHERNET_MASK);
	}

	/**
	 * Get wlan mask
	 *
	 * @return
	 */
	public static String getWlanMask() {
		return getPropInfo(WLAN_MASK);
	}

	/**
	 * Get prop information by different interface name
	 *
	 * @param interfaceName
	 * @return
	 */
	private static String getPropInfo(String interfaceName) {
		String re = "";
		try {
			Process process = Runtime.getRuntime().exec("getprop");
			Properties pr = new Properties();
			pr.load(process.getInputStream());
			re = pr.getProperty(interfaceName, "");
			if (!StringUtils.isEmpty(re) && re.length() > 6) {
				re = re.substring(1, re.length() - 1);
				return re;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return re;
	}
}
