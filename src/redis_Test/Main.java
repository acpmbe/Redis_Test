package redis_Test;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import sun.security.timestamp.TSRequest;

public class Main
{

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

		int size = 2000;

		long StartTime = System.currentTimeMillis(); // 获取开始时间

		Transaction ts = dis.multi();

		ts.lrange("Redis_Test", 0, size);
		ts.ltrim("Redis_Test", size + 1, -1);
		List<Object> list = ts.exec();
		List<String> list_N = (ArrayList<String>) list.get(0);

		long EndTime = System.currentTimeMillis(); // 获取开始时间

		System.out.println("_程序运行时间： " + (EndTime - StartTime) + "ms");

		System.out.println("数量：" + list_N.size());

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

		TempStr = "0：帮助（数字0）" + "\n";
		TempStr += "1：单条写入测试（数字1+逗号+参数：单条写入数量）（实例：1,2000）" + "\n";
		TempStr += "2：单条读取测试（数字2）（实例：2）" + "\n";
		TempStr += "3：批量写入测试（数字3+次数+每次写入数量）（实例：3,5,2000）" + "\n";
		TempStr += "4：批量读取测试（数字4+每次读取数量）（实例：4,2000）" + "\n";
		TempStr += "3：退出" + "\n";

		System.out.println(TempStr);
	}

	static void Write_Always(int count, int lenght)
	{
		Jedis dis = RedisConn.GetJedis();

		SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String content = "DC000000000000000011320600010007000000006400649D00000001009D0000000A0001F112011E0F000A143004244838A6FF434D43432D7156747100C0E8F97FD8E8F97F000000000000000001000000A0D39A0000000000000000000000000000000000000000000000000000000000000000000100000000003344";
		int Lenght = lenght;

		long startTime;
		long EndTime;
		long RunTime;
		long CountTime = 0;
		int Num = 0;
		DecimalFormat df = new DecimalFormat("0.00");
		String Ave;

		String array[] = new String[Lenght];

		for (int i = 0; i < Lenght; i++)
		{
			array[i] = content;
		}

		long start = System.currentTimeMillis();

		for (int i = 0; i < count; i++)
		{

			Num++;
			startTime = System.currentTimeMillis(); // 获取开始时间

			dis.lpush("Redis_Test", array);

			EndTime = System.currentTimeMillis(); // 获取开始时间

			RunTime = EndTime - startTime;

			CountTime += RunTime;

		}

		long end = System.currentTimeMillis();

		Ave = df.format((float) CountTime / Num);

		System.out.println("批量写入测试：" + "当前时间：" + tf.format(new Date()) + "  写入次数：" + Num + "  数据总量：" + (Num * Lenght)
				+ "  总用时：" + (end - start) + "ms" + "  写入平均时间： " + Ave + "ms");

	}

	static void Read_Always(int lenght)
	{

		try
		{

			Jedis dis = RedisConn.GetJedis();

			SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			int size = lenght;

			long startTime;
			long EndTime;
			long RunTime;
			long CountTime = 0;
			int Num = 0;
		
			DecimalFormat df = new DecimalFormat("0.00");
			String Ave = "";

			while (true)
			{

				startTime = System.currentTimeMillis(); // 获取开始时间

				Transaction ts = dis.multi();
				ts.lrange("Redis_Test", 0, size - 1);
				ts.ltrim("Redis_Test", size, -1);

				// ts.lrange("Redis_Test",5, 7 );
				// ts.ltrim("Redis_Test",size, -1 );
				List<Object> list = ts.exec();
				List<String> list_N = (ArrayList<String>) list.get(0);
				
				if (list_N.size() == 0)
				{
					
					continue;
				}


				EndTime = System.currentTimeMillis(); // 获取开始时间

				RunTime = EndTime - startTime;

				CountTime += RunTime;

			
				Num++;
				
				
				Ave = df.format((float) CountTime / Num);

			

				String print = "批量读取测试：" + "当前时间：" + tf.format(new Date()) + "  读取次数：" + Num + "  数据总量：" + (Num * size)
						+ "  总用时：" + CountTime + "ms" + "  读取平均时间： " + Ave + "ms";

				System.out.println(print);
				
				

			}

	

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	static void ReadOne()
	{

		Jedis dis = RedisConn.GetJedis();
		SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DecimalFormat df = new DecimalFormat("0.00");

		int Num = 0;
		String content = "";
		String Ave;

		long startTime = System.currentTimeMillis(); // 获取开始时间

		while (true)
		{
			content = dis.rpop("Redis_Test");
			if (content == null)
			{
				break;
			}

			Num++;
		}

		long EndTime = System.currentTimeMillis(); // 获取开始时间

		Long CountTime = EndTime - startTime;

		Ave = df.format((float) CountTime / Num);

		System.out.println("单条读取测试： 当前时间：" + tf.format(new Date()) + "  读取次数：" + Num + "  数据总量：" + (Num * 1) + "  总用时："
				+ CountTime + "ms" + "  写入平均时间： " + Ave + "ms");

	}

	static void WriteOne(int count)
	{

		Jedis dis = RedisConn.GetJedis();

		SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String content = "DC000000000000000011320600010007000000006400649D00000001009D0000000A0001F112011E0F000A143004244838A6FF434D43432D7156747100C0E8F97FD8E8F97F000000000000000001000000A0D39A0000000000000000000000000000000000000000000000000000000000000000000100000000003344";

		int Num = count;
		long CountTime = 0;
		DecimalFormat df = new DecimalFormat("0.00");
		String Ave;

		long startTime = System.currentTimeMillis(); // 获取开始时间

		for (int i = 0; i < Num; i++)
		{
			dis.lpush("Redis_Test", content);
		}

		long EndTime = System.currentTimeMillis(); // 获取开始时间

		CountTime = EndTime - startTime;

		Ave = df.format((float) CountTime / Num);

		System.out.println("单条写入测试： 当前时间：" + tf.format(new Date()) + "  写入次数：" + Num + "  数据总量：" + (Num * 1) + "  总用时："
				+ CountTime + "ms" + "  写入平均时间： " + Ave + "ms");

	}

	public static void main(String[] args)
	{

		// PrintInfo();
		// ReadOne();
		// WriteOne(500);
		// Read_Always(2000);
		// Write_Always(20, 2000);
		// WritePack();
		// ReadPack_Tran();
		// Del();
		// ReadPack();
		// WriteOne();

		int Key = 0;
		String KeyStr = "";
		boolean IsNum;
		while (true)
		{

			IsNum = true;

			KeyStr = new Scanner(System.in).nextLine();
			if (KeyStr.equals(""))
			{
				System.out.println("没有任何输入，如要帮助请输入0");
				continue;
			}

			String[] array = KeyStr.split(",");

			for (String str : array)
			{
				if (!isNumeric(str))
				{
					IsNum = false;
					break;
				}
			}
			if (!IsNum)
			{
				System.out.println("请输入数字，如要帮助请输入0");
				continue;
			}

			Key = Integer.parseInt(array[0]);

			switch (Key)
			{
			case 0:

				PrintInfo.Print();
				break;
			case 1:

				WriteOne(Integer.parseInt(array[1]));
				break;
			case 2:

				ReadOne();
				break;
			case 3:

				Write_Always(Integer.parseInt(array[1]), Integer.parseInt(array[2]));
				break;
			case 4:

				Read_Always(Integer.parseInt(array[1]));
				break;
			case 5:

				System.exit(0);
				break;
			default:
				break;
			}

		}

	}

}
