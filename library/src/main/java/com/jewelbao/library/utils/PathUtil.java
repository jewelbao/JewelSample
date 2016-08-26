package com.jewelbao.library.utils;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;

/**
 Created by PC on 2016/3/17.
 */
public class PathUtil
{
	private Context context;

	public static String pathPrefix; // app所有文件保存根路径
	public static final String resPathName = "/res/";  // 本地资源文件保存路径(程序唯一)
	public static final String dataPathName = "/data/";  // 本地数据文件保存路径(程序唯一)
	public static final String cachePathName = "/cache/"; // 本地缓存资源(程序唯一)

	private static File storageDir = null;
	private static PathUtil instance = null;
	private File resPath = null;
	private File dataPath = null;
	private File cachePath = null;

	private PathUtil(Context context)
	{
		this.context = context;
		String packageName = context.getPackageName();
		pathPrefix = "/Android/data/" + packageName + "/";
	}

	public synchronized static PathUtil getInstance(Context context)
	{
		if(instance == null)
		{
			instance = new PathUtil(context);
		}

		return instance;
	}

	/**
	 初始化本应用管理文件夹

	 @param userName
	 */
	public void initDirs(String userName)
	{
//		initUserDirs(userName);
		initGeneratePath();
	}

	/**
	 每个用户生成一个管理文件夹

	 @param userName
	 */
	public void initUserDirs(@NonNull String userName)
	{
		if(TextUtils.isEmpty(userName))
		{
			throw new NullPointerException("param otherId cant been null!");
		}

//		this.voicePath = generatePath(userName, voicePathName, context);
//		if(!this.voicePath.exists())
//		{
//			this.voicePath.mkdirs();
//		}
//
//		this.imagePath = generatePath(userName, imagePathName, context);
//		if(!this.imagePath.exists())
//		{
//			this.imagePath.mkdirs();
//		}
//
//		this.historyPath = generatePath(userName, historyPathName, context);
//		if(!this.historyPath.exists())
//		{
//			this.historyPath.mkdirs();
//		}
//
//		this.videoPath = generatePath(userName, videoPathName, context);
//		if(!this.videoPath.exists())
//		{
//			this.videoPath.mkdirs();
//		}
//
//		this.filePath = generatePath(userName, filePathName, context);
//		if(!this.filePath.exists())
//		{
//			this.filePath.mkdirs();
//		}
//
//		this.verifyPath = generatePath(userName, verifyPathName, context);
//		if(!this.verifyPath.exists())
//		{
//			this.verifyPath.mkdirs();
//		}
//
//		this.contactPath = generatePath(userName, contactPathName, context);
//		if(!this.contactPath.exists())
//		{
//			this.contactPath.mkdirs();
//		}
	}

	/**
	 如果执行了{@link com.jewelbao.library.utils.PathUtil#initUserDirs(String)}这个文件，那么这个方法可不执行
	 */
	public void initGeneratePath()
	{
		this.resPath = generatePath(null, resPathName, context);
		if(!this.resPath.exists())
		{
			boolean mkdirs = this.resPath.mkdirs();
			JLog.i("resPath create :" + mkdirs);
		}

		this.dataPath = generatePath(null, dataPathName, context);
		if(!this.dataPath.exists())
		{
			boolean mkdirs = this.dataPath.mkdirs();
			JLog.i("dataPath create :" + mkdirs);
		}

		this.cachePath = generatePath(null, cachePathName, context);
		if(!this.cachePath.exists())
		{
			boolean mkdirs = this.cachePath.mkdirs();
			JLog.i("cachePath create :" + mkdirs);
		}
	}


	public File getResourcePath()
	{
		return this.resPath;
	}

	public File getDataPath()
	{
		return this.dataPath;
	}

	public File getCachePath()
	{
		return this.cachePath;
	}


	private static File getStorageDir(Context context)
	{
		if(storageDir == null)
		{
			File directory = Environment.getExternalStorageDirectory();
			if(directory.exists() && directory.canWrite())
			{
				return directory;
			}

			storageDir = context.getFilesDir();
		}

		return storageDir;
	}

	private static File generatePath(String userName, String pathName, Context context)
	{
		String name = pathPrefix + userName + pathName;
		if(TextUtils.isEmpty(userName))
		{
			name = pathPrefix + pathName;
		}
		return new File(getStorageDir(context), name);
	}
}

