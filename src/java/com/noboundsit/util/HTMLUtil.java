package com.noboundsit.util;

public class HTMLUtil
{
	public static String stripHTMLTags(Object value)
	{
		StringBuffer ret = new StringBuffer();
		
		if (value == null)
			return "";

		try
		{
			ret.append(value.toString());
			int index = ret.toString().indexOf("<");
			while (index != -1)
			{
				int endindex = ret.toString().indexOf(">");
				ret.delete(index, endindex+1);
				index = ret.toString().indexOf("<");
			}
		}
		catch (Exception e)
		{
			System.out.println("Could not strip :"+value);
		}

		return ret.toString();	
	}

	public static String toHTML(Object value)
	{
		return toHTML(value, false);
	}

	public static String toHTML(Object value, boolean forInput)
	{
		if (value == null)
			return "&nbsp;";
			    
		String ret = value.toString().trim();
		StringBuffer sb = new StringBuffer();
		char ch;
			
		for (int i = 0; i < ret.length(); i++)
		{
			ch = ret.charAt(i);
			switch (ch)
			{
				case '&':	sb.append("&amp;"); break;
				case '"':	sb.append("&quot;"); break;
				case '<':	sb.append("&lt;"); break;
				case '>':	sb.append("&gt;"); break;
				case '\n':	if (!forInput)
							{
								sb.append("<br>");
								break;	
							}
				default:	sb.append(ch); break;
			}
		}
			
		ret = sb.toString();
		return ret;
	}

	public static String toHTMLInput(Object value)
	{
		if (value == null)
			return "";
		return toHTML(value, true);
	}
}