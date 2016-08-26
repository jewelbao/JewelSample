package com.jewelbao.library.utils;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;

import java.util.ArrayList;
import java.util.Collection;

/**
 Created by Administrator on 2016/2/16 0016. com.jewelbao.library.utils  LiteOrm工具
 */
public class LiteOrmUtil
{

	private LiteOrmUtil()
	{
		//        throw new Error("Do not need instantiate!");
	}

	//    private static LiteOrmUtil instance;
	//
	//    public static LiteOrmUtil getInstance() {
	//        if(instance == null) {
	//            instance = new LiteOrmUtil();
	//        }
	//        return instance;
	//    }
	//
	//    private static LiteOrm liteOrm;
	//
	//    private void initLiteOrm(Context mContext)
	//    {
	//        String userMobile = UserDBManager.getInstance().getLoginUser().mobile;
	//        if(!TextUtils.isEmpty(userMobile))
	//        {
	//            File path = PathUtil.getInstance().getVerifyPath();
	//            if(path == null || !path.exists())
	//            {
	//                PathUtil.getInstance().initUserDirs(userMobile);
	//            }
	//            liteOrm = LiteOrm.newSingleInstance(mContext,
	//                                                PathUtil.getInstance().getFilePath()
	//                                                        .getAbsolutePath() + File.separator + userMobile + ".db");
	//            liteOrm.setDebugged(true);
	//        }
	//    }

	/**
	 根据主键获取

	 @param liteOrm
	 @param clz
	 @param id      主键值
	 */
	public static Object getByID(LiteOrm liteOrm, Class<?> clz, String id)
	{
		return liteOrm.queryById(id, clz);
	}

	public static Object getByID(LiteOrm liteOrm, Class<?> clz, long id)
	{
		return liteOrm.queryById(id, clz);
	}

	/**
	 保存对象

	 @param liteOrm
	 @param bean
	 */
	public static long save(LiteOrm liteOrm, Object bean)
	{
		return liteOrm.save(bean);
	}

	/**
	 保存列表

	 @param liteOrm
	 @param list
	 */
	public static int save(LiteOrm liteOrm, Collection list)
	{
		return liteOrm.save(list);
	}

	/**
	 插入对象

	 @param liteOrm
	 @param bean
	 */
	public static long insert(LiteOrm liteOrm, Object bean)
	{
		return liteOrm.insert(bean);
	}

	/**
	 更新对象

	 @param liteOrm
	 @param bean
	 */
	public static int update(LiteOrm liteOrm, Object bean)
	{
		return liteOrm.update(bean);
	}

	/**
	 查询所有

	 @param liteOrm
	 @param clz
	 */
	public static ArrayList<?> query(LiteOrm liteOrm, Class<?> clz)
	{
		return liteOrm.query(clz);
	}

	/**
	 查询所有

	 @param liteOrm
	 @param clz
	 @param key     根据key来降序排列
	 */
	public static ArrayList<?> queryDesc(LiteOrm liteOrm, Class<?> clz, String key)
	{
		return liteOrm.query(new QueryBuilder<>(clz).appendOrderDescBy(key));
	}

	/**
	 查询所有

	 @param liteOrm
	 @param clz
	 @param key     根据key来升序排列
	 */
	public static ArrayList<?> queryAsc(LiteOrm liteOrm, Class<?> clz, String key)
	{
		return liteOrm.query(new QueryBuilder<>(clz).appendOrderAscBy(key));
	}

	/**
	 删除对象

	 @param liteOrm
	 @param obj
	 */
	public static int delete(LiteOrm liteOrm, Object obj)
	{
		return liteOrm.delete(obj);
	}

	/**
	 删除指定数量

	 @param liteOrm
	 @param clz
	 @param start   最少为0
	 @param length  最多为列表size
	 @param sortKey 按key升序
	 */
	public static int delete(LiteOrm liteOrm, Class<?> clz, long start, long length, String sortKey)
	{
		return liteOrm.delete(clz, start, length, sortKey);
	}

	/**
	 根据key-value删除数据

	 @param liteOrm
	 @param clz
	 @param key     键
	 @param value   值
	 */
	public static int deleteByID(LiteOrm liteOrm, Class<?> clz, String key, String value)
	{
		return liteOrm.delete(new WhereBuilder(clz).equals(key, value));
	}
}