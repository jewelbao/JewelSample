package com.jewelbao.library.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 Created by PC on 2016/3/7.
 系统/proc文件读取工具
 */
public class ProcFileReader
{
	/**
	 这个文件给出了内存状态信息。他显示出系统中空闲内存，已用物理内存和交换内存的总量。
	 他还显示出内核使用的共享内存和缓冲区总量。
	 @return 总内存
	 */
	public static String getTotalMemory() {
		String str1 = "/proc/meminfo";
		List<String> mResult = readFileLines(str1);
//		JLog.e("=====start read /proc/meminfo =======");
//		for(int i=0; i<mResult.size(); i++) {
//			JLog.e(mResult.get(i));
//		}
//		JLog.e("=====read end /proc/meminfo =======");

		if (mResult != null && mResult.size() >= 1) {
			for (int i = 0; i < mResult.size(); i++) {
				String[] mType = mResult.get(i).replaceAll(" ", "").split(":");
				if (mType[0] != null && mType[0].length() > 0 && mType[0].equalsIgnoreCase("MemTotal"))
				{
					return mType[1];
				}
			}
		}
		return "0";
	}

	/**
	 这个文件提供了有关系统CPU的信息。这些信息从内核里对CPU的测试代码中得到。
	 文件列出了CPU的普通型号（386, 486, 586等），以及能得到的更多特定信息(制造商、型号和版本)。
	 文件还包含了以bogomips表示的处理器速度，而且如果检测到CPU的多种特性或者BUG，文件还会包含相应的标志。
	 这个文件的格式为：文件由多行构成，每行包括一个域名称、一个冒号和一个值。
	 @return [0]CPU的型号，[1]CPU的频率
	 */
	public static String[] getCPUInfo() {
		String str1 = "/proc/cpuinfo";
		String str2="";
		String[] cpuInfo={"",""};
		String[] arrayOfString;
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			for (int i = 2; i < arrayOfString.length; i++) {
				cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
			}
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			cpuInfo[1] += arrayOfString[2];
			localBufferedReader.close();
		} catch (IOException e) {
		}
		return cpuInfo;
	}

	/**
	 * 从文件 /proc/net/arp 中获取IP对应的MAC地址集合。
	 * /proc/net子目录下的文件描述或修改了联网代码的行为。可以通过使用arp, netstat, route和ipfwadm命令设置或查询这些特殊文件中的许多文件。
	 * arp=转储每个网络接口的arp表中dev包的统计。
	 * dev=来自网络设备的统计。
	 * dev_mcast=列出二层（数据链路层）多播组。
	 * igmp=加入的IGMP多播组。
	 * netlink=套接口的信息。
	 * netstat=网络流量的多种统计。第一行是信息头，带有每个变量的名称。接下来的一行保存相应变量的值。
	 * raw=原始套接口的套接口表。
	 * route=静态路由表。
	 * rpc=包含RPC信息的目录。
	 * rt_cache=路由缓冲。
	 * snmp=snmp agent的ip/icmp/tcp/udp协议统计；各行交替给出字段名和值。
	 * sockstat=列出使用的tcp/udp/raw/pac/sys_cookies的数量。
	 * tcp=TCP连接的套接口。
	 * udp=UDP连接的套接口。
	 * unix=UNIX域套接口的套接口表。
	 * @return Mac Address
	 * @attention the file /proc/net/arp need exit
	 */
	public Map<String, String> getMacFromFile() {
		List<String> mResult = readFileLines("/proc/net/arp");

		JLog.e("=======  /proc/net/arp  =========");
		for (int i = 0; i < mResult.size(); ++i)
			Log.e("line", mResult.get(i));
		JLog.e("===========================");

		Map<String, String> resultList = new HashMap<>();

		if (mResult != null && mResult.size() > 1) {
			for (int j = 1; j < mResult.size(); ++j) {
				List<String> mList = new ArrayList<String>();
				String[] mType = mResult.get(j).split(" ");
				for (int i = 0; i < mType.length; ++i) {
					if (mType[i] != null && mType[i].length() > 0)
						mList.add(mType[i]);
				}

				if (mList != null && mList.size() > 4) {
					String result = "";
					String[] tmp = mList.get(3).split(":");
					for (int i = 0; i < tmp.length; ++i) {
						result += tmp[i];
					}
					result = result.toUpperCase();
					if (!result.contains("00000000")) {
						resultList.put(mList.get(0), result);
					}
				}
			}
		}
		return resultList;
	}

	/**
	 获取proc目录下文件的信息：<p>
	 /proc/cmdline:内核启动命令行</p>
	 /proc/devices:字符和块设备的主设备号以及分配到这些设备号的设备名称<p>
	 /proc/dma;</p>
	 /proc/filesystems;<p>
	 /proc/interrupts;</p>
	 /proc/ioports;<p>
	 /proc/kcore;</p>
	 /proc/kmsg;<p>
	 /proc/ksyms;</p>
	 /proc/loadavg;<p>
	 /proc/locks;</p>
	 /proc/mdstat;<p>
	 /proc/misc;</p>
	 /proc/modules;<p>
	 /proc/mounts;</p>
	 /proc/pci;<p>
	 /proc/stat;</p>
	 /proc/uptime;<p>
	 /proc/version;</p>
	 /proc/scsi;</p>
	 /proc/sys;
	 */
	public static void getInfo(){
		String[] procFile = {"/proc/cmdline", "/proc/devices", "/proc/dma", "/proc/filesystems", "/proc/interrupts", "/proc/ioports",
							 "/proc/kcore", "/proc/kmsg", "/proc/ksyms", "/proc/loadavg", "/proc/locks", "/proc/mdstat", "/proc/misc",
							 "/proc/modules", "/proc/mounts", "/proc/pci", "/proc/stat", "/proc/uptime", "/proc/version", " /proc/scsi",
							 "/proc/sys"};
		for(int j=0; j<procFile.length; j++)
		{
			List<String> mResult = readFileLines(procFile[j]);

			JLog.e("=======  " + procFile[j] + "  =========");
			for(int i = 0; i < mResult.size(); ++i)
				Log.e("line", mResult.get(i));
			JLog.e("===========================");
		}


	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	private static List<String> readFileLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		String tempString = "";
		List<String> mResult = new ArrayList<String>();
		try {
			Log.i("result", "以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			while ((tempString = reader.readLine()) != null) {
				mResult.add(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return mResult;
	}
}
