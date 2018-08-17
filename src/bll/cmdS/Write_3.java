package bll.cmdS;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import java.util.*;


public class Write_3
{


	public void Write_Always_N_1(int count, int lenght)
	{

		long start;
		long end;

	
		
		
		String Url="192.168.135.25";
		int Port= 6380;
		String PassWord= "tendency123456";
		
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(-1);
		config.setMaxIdle(200);
		config.setMaxWait(10000);
		config.setTestOnBorrow(false);

		JedisPool JedisPool = new JedisPool(config, Url, Port, 100000, PassWord, 1);	
		Jedis	dis = JedisPool.getResource();
		
		

		SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String content = "DC000000000000000011320600010007000000006400649D00000001009D0000000A0001F112011E0F000A143004244838A6FF434D43432D7156747100C0E8F97FD8E8F97F000000000000000001000000A0D39A0000000000000000000000000000000000000000000000000000000000000000000100000000003344";
		String content_N = ",DC000000000000000011320600010007000000006400649D00000001009D0000000A0001F112011E0F000A143004244838A6FF434D43432D7156747100C0E8F97FD8E8F97F000000000000000001000000A0D39A0000000000000000000000000000000000000000000000000000000000000000000100000000003344";
		StringBuffer buffer = new StringBuffer(content);

		String ConN = "";
		int Lenght = lenght;

		int cc_Lenght = lenght - 1;

		long startTime;
		long EndTime;
		long RunTime;
		long CountTime = 0;
		int Num = 0;
		DecimalFormat df = new DecimalFormat("0.00");
		String Ave;

		ConN = content;

		start = System.currentTimeMillis();
		for (int i = 0; i < cc_Lenght; i++)
		{

			buffer.append(content_N);

		}

		end = System.currentTimeMillis();
		;

		System.out.println("组织数据时间：" + (end - start) + "ms");

		start = System.currentTimeMillis();

		for (int i = 0; i < count; i++)
		{

			Num++;
			startTime = System.currentTimeMillis(); // 获取开始时间

			dis.lpush("Redis_Test", buffer.toString());

			EndTime = System.currentTimeMillis(); // 获取开始时间

			RunTime = EndTime - startTime;

			CountTime += RunTime;

		}

		end = System.currentTimeMillis();

		Ave = df.format((float) CountTime / Num);

		System.out.println("批量写入测试：" + "当前时间：" + tf.format(new Date()) + "  写入次数：" + Num + "  数据总量：" + (Num * Lenght)
				+ "  总用时：" + (end - start) + "ms" + "  写入平均时间： " + Ave + "ms");

	}

}
