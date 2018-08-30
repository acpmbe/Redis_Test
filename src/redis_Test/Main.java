package redis_Test;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bll.cmdS.Redis_Bll;
import bll.cmdS.Write_3;
import redis.clients.jedis.Jedis;
import util.RedisConn;

public class Main
{

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

	static String Init()
	{

		try
		{
			String Result;
			String urlString = ClassLoader.getSystemResource("").getPath();
			urlString = URLDecoder.decode(urlString, "UTF-8");

			Result = util.Config.Init(urlString);
			if (!Result.equals("0"))
			{
				return Result;
			}

			return "0";

		}
		catch (Exception e)
		{
			return e.getMessage();
		}

	}

	public static void RedisRead(String collName)
	{
		Jedis dis = RedisConn.GetJedis();

		Long start = System.currentTimeMillis();

		String content = dis.rpop(collName);

		if (content.equals(""))
		{
			System.out.println("数据为空");
		}

		String[] ListData = content.split(",");

		Long end = System.currentTimeMillis();

		Long time = end - start;

		System.out.println("程序运行：" + time + "ms");

		System.out.println("数量：" + ListData.length);

	}

	public static void RedisWrite(String collName)
	{

		String string = "DC000000000000000011320600010007000000006400649D00000001009D0000000A0001F112011E0F000A143004244838A6FF434D43432D7156747100C0E8F97FD8E8F97F000000000000000001000000A0D39A0000000000000000000000000000000000000000000000000000000000000000000100000000003344";

		String sssd = "";
		for (int i = 0; i < 3; i++)
		{
			if (i < (3 - 1))
			{
				sssd += string + ",";
			}
			else
			{
				sssd += string;
			}

		}

		Jedis dis = RedisConn.GetJedis();

		dis.lpush(collName, "");

	}

	public static void main(String[] args)
	{

//		dsfsfsfsffssf
		String Result = Init();
		if (!Result.equals("0"))
		{
			System.out.println("初始化出错：" + Result);
			return;

		}

		System.out.println("Redis测试运行成功：请输入相关命令，如需帮助请按0");

		// Redis_Bll.RedisCount();

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

				Redis_Bll.WriteOne(Integer.parseInt(array[1]));
				break;
			case 2:

				Redis_Bll.ReadOne();
				break;
			case 3:

				Redis_Bll.Write_Always(Integer.parseInt(array[1]), Integer.parseInt(array[2]));

				break;

			case 4:

				// Redis_Bll.Write_Always_N_1(Integer.parseInt(array[1]),
				// Integer.parseInt(array[2]));

				// Write_3 w = new Write_3();
				//
				// w.Write_Always_N_1(Integer.parseInt(array[1]), Integer.parseInt(array[2]));

				Redis_Bll.Write_Always_N_1(Integer.parseInt(array[1]), Integer.parseInt(array[2]));

				break;
			case 5:

				Redis_Bll.Read_Always(Integer.parseInt(array[1]));
				break;

			case 6:

				System.exit(0);
				break;
			default:
				break;
			}

		}

	}

}
