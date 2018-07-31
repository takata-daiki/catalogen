/**
 * @(#)StringUtil.java 
 * 
 * create by x_chenbiwu at 2008-3-7
 * 
 * Copyright 2008 fivemen,Inc. All rights reserved.
 * 
 * fivemen proprietary/confidential.Use is subject to license terms
 */
package com.ett.common.util;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Description:</p>
 * <p>?­&#x2014;ç??ä¸??&#x201C;?ä?&#x153;????&#x2026;?ç??</p>
 * @author x_chenbiwu
 *
 */
public class StringUtil {
	
	protected static final Log log = LogFactory.getLog(StringUtil.class);
	/**
	 * é?&#x2DC;?¤ç&#x161;&#x201E;ç??­&#x2014;ç??ä¸&#x201E;1¤7
	 */
	public static final String EmptyString="";
	
	 private StringUtil()
     {
    	 
     }
	
	/**
	 * <p>??&#x201A;?&#x17E;&#x153;?&#x2DC;?ä¸?ç??&#x2C6;&#x2013;?&#x20AC;&#x201E;1¤7""??&#x201D;?&#x203A;&#x17E;ç&#x153;&#x;??&#x152;????&#x2C6;&#x2122;? &#x201E;1¤7p>
	 * @param arg ???é?&#x152;?? ç&#x161;&#x201E;?­&#x2014;ç??ä¸&#x201E;1¤7
	 * @return ??&#x201A;?&#x17E;&#x153;?&#x2DC;?ä¸?ç??&#x2C6;&#x2013;?&#x20AC;&#x201E;1¤7""??&#x201D;?&#x203A;&#x17E;ç&#x153;&#x;??&#x152;????&#x2C6;&#x2122;? &#x201E;1¤7
	 */
	public static boolean isNullOrEmpty(final String arg)
	{
		return arg==null||EmptyString.equals(arg);
	}
	
	/**
	 * @param map ??&#x201A;?&#x2022;°Map
	 * @param key key
	 * @param value ?&#x20AC;&#x201E;1¤7
	 */
	public static void addParamterToMap(HashMap map,String key,Object value)
	{
		log.info("??&#x17E;?&#x160; ??&#x201A;?&#x2022;°??&#x201E;1¤7"+key+"?&#x20AC;???&#x161;"+value+"?&#x2C6;°HashMapä¸&#x201E;1¤7");
		map.put(key, value);
		/*if(map.containsKey(key))
		{
			map.remove(key);
		}*/
		
	}
	
	/**
	 * @param map ?­&#x2014;ç??ä¸???&#x201A;?&#x2022;°ç&#x161;&#x201E;HashMap
	 * @return ç?&#x201E;?&#x2C6;?ç&#x161;&#x201E;?­&#x2014;ç??ä¸???&#x201A;?&#x2022;°
	 */
	public static String makeParamter(HashMap map)
	{
		StringBuilder sb=new StringBuilder();
		String result=null;
		java.util.Iterator iterator=map.keySet().iterator();
		String key=null;
		Object obj=null;
		boolean flag=iterator.hasNext();
		while(iterator.hasNext())
		{
			key=iterator.next().toString();
			log.info("??&#x201A;?&#x2022;°Mapä¸­ç&#x161;&#x201E;keyä¸???&#x161;"+key);
			obj=map.get(key);
			log.info("??&#x201A;?&#x2022;°Mapä¸­ç&#x161;&#x201E;Valueä¸???&#x161;"+obj);
			if(obj==null)
			{
				obj="";
			}
			sb.append(key);
			sb.append("=");
			sb.append(obj.toString());
			sb.append("&");
		}
		result=sb.toString();
		if(flag)
		{
			result=result.substring(0,result.length()-1);
		}
		log.info("ç&#x201D;?HashMapç?&#x201E;?&#x2C6;?ç&#x161;&#x201E;?­&#x2014;ç??ä¸???&#x201A;?&#x2022;°ä¸???&#x161;"+result);
		return result;
	}
	
	/**
	 * @param map ?&#x2030;&#x201C;??°HashMap
	 */
	public static void printMap(HashMap map)
	{
		java.util.Iterator iterator=map.keySet().iterator();
		Object key=null;
		Object obj=null;
		while(iterator.hasNext())
		{
			key=iterator.next();
			obj=map.get(key);
			System.out.println("key:"+key+" value:"+obj);
			
		}
	}
	
	/**
	 * @param url ??&#x201A;?&#x2022;°url
	 * @return ??&#x201A;?&#x2022;°?¤&#x201E;ç?&#x2020;??&#x17D;?&#x201D;?&#x2026;?ä¸&#x20AC;ä¸?map
	 */
	public static HashMap getParamters(String url)
	{
		log.info("é&#x153;&#x201E;1¤7?? ?&#x17D;???&#x2013;??&#x201A;?&#x2022;°ç&#x161;&#x201E;urlä¸???&#x161;"+url);
		HashMap<String,String> map=new HashMap<String, String>();
		int index=url.indexOf("?");
		if(index!=-1)
		{
			url=url.substring(index+1);
			log.info("urlç&#x161;&#x201E;??&#x201A;?&#x2022;°?&#x2DC;???&#x201E;1¤7"+url);
			//String[] array=str.split("?");
			String[] array2=url.split("&");
			log.info("urlç&#x161;&#x201E;??&#x201A;?&#x2022;°ä¸??&#x2022;°?&#x2DC;???&#x201E;1¤7"+array2.length);
			for(String s:array2)
			{
				log.info("??&#x201A;?&#x2022;°??&#x201E;1¤7"+s);
				String[] array=s.split("=");	
				if(array.length==2)
				{
					log.info("?&#x160; ?&#x2026;???&#x201A;?&#x2022;°key??&#x201E;1¤7"+array[0]+" value:"+array[1]+"?&#x2C6;°mapä¸&#x201E;1¤7");
					map.put(array[0], array[1]);
				}
				else
				{
					log.info("?&#x160; ?&#x2026;???&#x201A;?&#x2022;°key??&#x201E;1¤7"+array[0]+" value:?&#x2C6;°mapä¸&#x201E;1¤7");
					map.put(array[0], "");
				}
				
			}
			
		}
		
		return map;
		
		
	}
	

}
