package redis_Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import sun.security.timestamp.TSRequest;

public class Main
{

	private static void ttt()
	{
		Jedis dis = RedisConn.GetJedis();

		// byte[] array = new byte[] {1,2,3};

		// String[] arr = ("1,2,3").split(",");

		// dis.lpush("Redis_Test", arr);

		// dis.lpush("Redis_Test", "123");

		String content = "DC000000000000000011320600010007000000006400649D00000001009D0000000A0001F112011E0F000A143004244838A6FF434D43432D7156747100C0E8F97FD8E8F97F000000000000000001000000A0D39A0000000000000000000000000000000000000000000000000000000000000000000100000000003344";

		String array[] = new String[3000];

		for (int i = 0; i < 3000; i++)
		{
			array[i] = content;
		}

		long startTime = System.currentTimeMillis(); // 获取开始时间

		dis.lpush("Redis_Test", array);

		// for (int i = 0; i < 2000; i++)
		// {
		// // dis.lpush("Redis_Test", content);
		//
		// }

		long EndTime = System.currentTimeMillis(); // 获取开始时间

		System.out.println("_程序运行时间： " + (EndTime - startTime) + "ms");

		// String string = dis.rpop("Redis_Test");

	}

	static void WritePack()
	{
		Jedis dis = RedisConn.GetJedis();

		String content = "DC000000000000000011320600010007000000006400649D00000001009D0000000A0001F112011E0F000A143004244838A6FF434D43432D7156747100C0E8F97FD8E8F97F000000000000000001000000A0D39A0000000000000000000000000000000000000000000000000000000000000000000100000000003344";

		int Lenght = 500;
		String array[] = new String[Lenght];

		for (int i = 0; i < Lenght; i++)
		{
			array[i] = content;
		}

		long startTime = System.currentTimeMillis(); // 获取开始时间

		dis.lpush("Redis_Test", array);

		long EndTime = System.currentTimeMillis(); // 获取开始时间

		System.out.println("_程序运行时间： " + (EndTime - startTime) + "ms");

	}

	static void WriteOne()
	{
		Jedis dis = RedisConn.GetJedis();

		String content = "DC000000000000000011320600010007000000006400649D00000001009D0000000A0001F112011E0F000A143004244838A6FF434D43432D7156747100C0E8F97FD8E8F97F000000000000000001000000A0D39A0000000000000000000000000000000000000000000000000000000000000000000100000000003344";

		int Lenght = 3;

		long startTime = System.currentTimeMillis(); // 获取开始时间

		for (int i = 0; i < Lenght; i++)
		{
			dis.lpush("Redis_Test", content);
		}

		long EndTime = System.currentTimeMillis(); // 获取开始时间

		System.out.println("_程序运行时间： " + (EndTime - startTime) + "ms");

	}

	static void ReadPack()
	{

		Jedis dis = RedisConn.GetJedis();

		long startTime = System.currentTimeMillis(); // 获取开始时间

		List<String> list = dis.lrange("Redis_Test", 0, 3); // 索引区间批量读取。

		for (String string : list)
		{

			System.out.println(string);
		}

		// List<String> list = dis.mget( "Redis_Test"); // 索引区间批量读取。

		long EndTime = System.currentTimeMillis(); // 获取开始时间

		System.out.println("_程序运行时间： " + (EndTime - startTime) + "ms");

	}

	static void Del()
	{

		Jedis dis = RedisConn.GetJedis();
		dis.ltrim("Redis_Test", 4, -1); // 删除区间之外的数据

	}

	static void ReadPack_Tran()
	{

		Jedis dis = RedisConn.GetJedis();

		int size = 3;

		long StartTime = System.currentTimeMillis(); // 获取开始时间

		Transaction ts = dis.multi();

		ts.lrange("Redis_Test", 0, size);
		ts.ltrim("Redis_Test", size + 1, -1);
		List<Object> list = ts.exec();
		List<String> list_N = (ArrayList<String>) list.get(0);

		long EndTime = System.currentTimeMillis(); // 获取开始时间

		System.out.println("_程序运行时间： " + (EndTime - StartTime) + "ms");

	}

	static void ReadOne()
	{

		Jedis dis = RedisConn.GetJedis();

		String sdd = "";

		long startTime = System.currentTimeMillis(); // 获取开始时间

		do
		{
			sdd = dis.rpop("Redis_Test");
		}
		while (sdd != null);

		long EndTime = System.currentTimeMillis(); // 获取开始时间

		System.out.println("_程序运行时间： " + (EndTime - startTime) + "ms");

	}

	static boolean isNumeric(String str)
	{
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches())
		{
			return false;
		}
		return true;
	}

	static void PrintInfo()
	{

		String TempStr = "";

		TempStr = "0：帮助" + "\n";
		TempStr += "1：帮助" + "\n";
		TempStr += "2：帮助" + "\n";
		TempStr += "3：退出" + "\n";

		System.out.println(TempStr);
	}

	public static void main(String[] args)
	{

		// WritePack();
		ReadPack_Tran();
		// Del();
		// ReadPack();
		// WriteOne();

		// int Key = 0;
		// String KeyStr = "";
		// while (true)
		// {
		//
		// KeyStr = new Scanner(System.in).nextLine();
		// if (KeyStr.equals(""))
		// {
		// System.out.println("没有任何输入，如要帮助请输入0");
		// continue;
		// }
		//
		// if (!isNumeric(KeyStr))
		// {
		// System.out.println("请输入数字，如要帮助请输入0");
		// continue;
		// }
		//
		// Key = Integer.parseInt(KeyStr);
		//
		// switch (Key)
		// {
		// case 0:
		//
		// PrintInfo();
		//
		// break;
		// case 1:
		//
		// break;
		// case 2:
		//
		// break;
		// case 3:
		// System.exit(0);
		//
		// break;
		//
		// default:
		// break;
		// }
		//
		// }

	}

}
