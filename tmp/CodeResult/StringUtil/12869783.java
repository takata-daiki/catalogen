/**
 * @(#)StringUtil.java 
 * 
 * create by x_chenbiwu at 2008-3-7
 * 
 * Copyright 2008 fivemen,Inc. All rights reserved.
 * 
 * fivemen proprietary/confidential.Use is subject to license terms
 */
package com.austin.base.commons.util;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Description:</p>
 * <p>????????</p>
 * @author x_chenbiwu
 *
 */
public class StringUtil {
	
	protected static final Log log = LogFactory.getLog(StringUtil.class);
	/**
	 * ???????1?7
	 */
	public static final String EmptyString="";
	
	 private StringUtil()
     {
    	 
     }
	
	/**
	 * <p>???????1?7""???????1?7p>
	 * @param arg ???????1?7
	 * @return ???????1?7""???????1?7
	 */
	public static boolean isNullOrEmpty(final String arg)
	{
		return arg==null||EmptyString.equals(arg);
	}
	
	/**
	 * @param map ??Map
	 * @param key key
	 * @param value ?1?7
	 */
	public static void addParamterToMap(HashMap map,String key,Object value)
	{
		log.info("?????1?7"+key+"??"+value+"?HashMap?1?7");
		map.put(key, value);
		/*if(map.containsKey(key))
		{
			map.remove(key);
		}*/
		
	}
	
	/**
	 * @param map ??????HashMap
	 * @return ????????
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
			log.info("??Map??key??"+key);
			obj=map.get(key);
			log.info("??Map??Value??"+obj);
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
		log.info("?HashMap??????????"+result);
		return result;
	}
	
	/**
	 * @param map ??HashMap
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
	 * @param url ??url
	 * @return ?????????map
	 */
	public static HashMap getParamters(String url)
	{
		log.info("?1?7??????url??"+url);
		HashMap<String,String> map=new HashMap<String, String>();
		int index=url.indexOf("?");
		if(index!=-1)
		{
			url=url.substring(index+1);
			log.info("url?????1?7"+url);
			//String[] array=str.split("?");
			String[] array2=url.split("&");
			log.info("url???????1?7"+array2.length);
			for(String s:array2)
			{
				log.info("???1?7"+s);
				String[] array=s.split("=");	
				if(array.length==2)
				{
					log.info("????key?1?7"+array[0]+" value:"+array[1]+"?map?1?7");
					map.put(array[0], array[1]);
				}
				else
				{
					log.info("????key?1?7"+array[0]+" value:?map?1?7");
					map.put(array[0], "");
				}
				
			}
			
		}
		
		return map;
		
		
	}
	

}
