package util;

import redis.clients.jedis.*;

public class RedisConn
{

	private RedisConn()
	{

	}

	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static final int MAX_ACTIVE = -1;

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static final int MAX_IDLE = 200;

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static final int MAX_WAIT = 10000;

	private static final int TIMEOUT = 100000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static final boolean TEST_ON_BORROW = false;

	private static Object object = new Object();

	private static JedisPool JedisPool;

	private static Jedis Dis;

	private static final String Url = Config.RedisIP();

	private static final int Port = Config.RedisPort();

	private static final String PassWord = Config.PassWord();

	private static final int Db = Config.RedisDb();
	
	
	public void tt()
	{
		String Url="192.168.135.25";
		int Port= 6380;
		String PassWord= "tendency123456";
		
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(-1);
		config.setMaxIdle(200);
		config.setMaxWait(10000);
		config.setTestOnBorrow(false);

		JedisPool	JedisPool = new JedisPool(config, Url, Port, 100000, PassWord, 1);
		

		
		Jedis	Dis = JedisPool.getResource();
	}

	public static Jedis GetJedis()
	{

		if (Dis == null)
		{

			synchronized (object)
			{

				if (Dis == null)
				{
					JedisPoolConfig config = new JedisPoolConfig();
					config.setMaxActive(MAX_ACTIVE);
					config.setMaxIdle(MAX_IDLE);
					config.setMaxWait(MAX_WAIT);
					config.setTestOnBorrow(TEST_ON_BORROW);

					JedisPool = new JedisPool(config, Url, Port, TIMEOUT, PassWord, Db);

					
					Dis = JedisPool.getResource();
				}

			}

		}
		return Dis;

	}

}
