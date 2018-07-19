package util;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Config
{

	public static String RedisIP()
	{
		return redisIP;
	}

	public static int RedisPort()
	{
		return redisPort;
	}

	public static String PassWord()
	{
		return passWord;
	}
	
	public static int RedisDb()
	{
		return redisDb;
	}
	
	public static String ListName()
	{
		return listName;
	}
	



	private static String redisIP;
	private static int redisPort;
	private static String passWord;
	private static int redisDb;
	private static String listName;

	public static String Init(String path)
	{
		try
		{

			File f = new File(path + "/config.xml");

			if (!f.exists())
			{
				return "配置文件不存在";
			}
			SAXReader reader = new SAXReader();
			Document doc;
			doc = reader.read(f);
			Element root = doc.getRootElement();
			Element data;
			Iterator<?> itr = root.elementIterator("VALUE");
			data = (Element) itr.next();

			redisIP = data.elementText("RedisIP").trim();
			redisPort = Integer.parseInt(data.elementText("RedisPort").trim());
			passWord = data.elementText("PassWord").trim();
			redisDb = Integer.parseInt(data.elementText("RedisDb").trim());
			listName = data.elementText("ListName").trim();

			return "0";

		}
		catch (Exception ex)
		{
			return ex.getMessage();
		}
	}

}
