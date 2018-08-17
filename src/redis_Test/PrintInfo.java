package redis_Test;

public class PrintInfo
{

	
	public static void Print()
	{
		String TempStr = "";

		TempStr = "0：帮助（数字0）" + "\n";
		TempStr += "1：单条写入测试（数字1+逗号+参数：写入次数）（实例：1,2000）" + "\n";
		TempStr += "2：单条读取测试（数字2）                    （实例：2）" + "\n";
		TempStr += "3：批量写入测试（数字3+次数+每次写入数量）  （实例：3,5,2000）" + "\n";
		TempStr += "4：批量（一行多条）写入测试（数字4+行数+每行多少条）       （实例：4,5,100）" + "\n";
		TempStr += "5：批量读取测试（数字4+每次读取数量）       （实例：4,2000）" + "\n";
		TempStr += "6：退出" + "\n";

		System.out.println(TempStr);
	}
}
