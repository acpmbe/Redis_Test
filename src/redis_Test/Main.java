package redis_Test;

import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import bll.cmdS.Redis_Bll;


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

	public static void main(String[] args)
	{

		String Result = Init();
		if (!Result.equals("0"))
		{
			System.out.println("初始化出错：" + Result);
			return;

		}

		System.out.println("Redis测试运行成功：请输入相关命令，如需帮助请按0");

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

				Redis_Bll.Read_Always(Integer.parseInt(array[1]));
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
