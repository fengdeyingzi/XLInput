package com.buscode.whatsinput.input;
import java.util.ArrayList;
/*
中文输入法数据库


*/
public class InputData
{
	
	private ArrayList<String> gbdata=null;
	
	public InputData(String data)
	{
		addData(data);
	}
	
	//添加词库
	public void addData(String data)
	{
		String items[] = data.split("\r\n|\n");
		gbdata = new ArrayList<String>();
		for(String item:items)
		{
			if(!item.startsWith("//"))
			{
				gbdata.add(item);
			}
		}
	}
	//清除词库
	public void clear()
	{
		gbdata.clear();
	}
	//获取输入候选字列表
	public ArrayList<String> getList(String pinyin)
	{
		ArrayList<String> array=new ArrayList<String>();
		String items[]=null;
		for(String item:gbdata)
		{
			if(item.startsWith(pinyin))
			{
				items = item.split(" |\t");
				break;
			}
		}
		if(items!=null)
		for(int i=items.length-1;i>=1;i--)
		{
			
			array.add(items[i]);
		}
		return array;
	}
	
	
	
	
	
	
}
