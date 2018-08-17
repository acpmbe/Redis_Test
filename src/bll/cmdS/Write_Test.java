package bll.cmdS;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import redis.clients.jedis.Jedis;
import util.Config;
import util.RedisConn;

public class Write_Test
{

	public final static String ListName = Config.ListName();
	
	public  void Write_Always(int count, int lenght)
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

			dis.lpush(ListName, array);

			EndTime = System.currentTimeMillis(); // 获取开始时间

			RunTime = EndTime - startTime;

			CountTime += RunTime;

		}
		

		long end = System.currentTimeMillis();

		Ave = df.format((float) CountTime / Num);

		System.out.println("批量写入测试：" + "当前时间：" + tf.format(new Date()) + "  写入次数：" + Num + "  数据总量：" + (Num * Lenght)
				+ "  总用时：" + (end - start) + "ms" + "  写入平均时间： " + Ave + "ms");

	}

	
}
