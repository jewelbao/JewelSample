package com.jewelbao.library.utils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.text.TextUtils;

import com.jewelbao.library.utils.db.DbHelper;


/**
 SqliteUtils

 @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-10-21 */
public class SqliteUtils
{

	private static final boolean DEBUG = true;
	private static final String TAG = "SqliteUtils";

	private static volatile SqliteUtils instance;

	private DbHelper dbHelper;
	private SQLiteDatabase db;

	private SqliteUtils(Context context)
	{
		dbHelper = new DbHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	public static SqliteUtils getInstance(Context context)
	{
		if(instance == null)
		{
			synchronized(SqliteUtils.class)
			{
				if(instance == null)
				{
					instance = new SqliteUtils(context);
				}
			}
		}
		return instance;
	}

	public SQLiteDatabase getDb()
	{
		return db;
	}

	/**
	 开始导出数据 此操作比较耗时,建议在线程中进行

	 @param context      上下文
	 @param targetFile   目标文件
	 @param databaseName 要拷贝的数据库文件名

	 @return 是否倒出成功
	 */
	public boolean startExportDatabase(Context context, String targetFile, String databaseName)
	{
		if(DEBUG)
		{
		}
		if(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
		{
			if(DEBUG)
			{
			}
			return false;
		}
		String sourceFilePath = Environment.getDataDirectory() + "/data/" + context
				.getPackageName() + "/databases/" + databaseName;
		String destFilePath = Environment.getExternalStorageDirectory() + (TextUtils
				.isEmpty(targetFile) ?
				(context.getPackageName() + ".db") :
				targetFile);
		boolean isCopySuccess = FileUtils.copyFile(sourceFilePath, destFilePath);
		if(DEBUG)
		{
			if(isCopySuccess)
			{

			} else
			{
			}
		}
		return isCopySuccess;
	}
}
