package com.noboundsit.util;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class StringUtil
{
	public static String checkNull(Object obj)
	{
		if (obj == null)
			return "";
		return obj.toString();
	}

	public static String checkNull(String str)
	{
		if (str == null)
			return "";
		return str;
	}

	public static void deleteFromBuffer(StringBuffer sb, String str, boolean all)
	{
		int index = sb.toString().indexOf(str, 0);
		while (index != -1)
		{
			sb.delete(index, index+str.length());
			index = sb.toString().indexOf(str, 0);
			if (!all)
				return;
		}
	}

	public static void deleteFromBuffer(StringBuffer sb, String beginstr, String endstr, boolean all)
	{
		beginstr = beginstr.toLowerCase();
		endstr = endstr.toLowerCase();
		int index = sb.toString().toLowerCase().indexOf(beginstr, 0);
		int endindex;
		
		while (index != -1)
		{
			endindex = sb.toString().toLowerCase().indexOf(endstr, index+beginstr.length())+endstr.length();
			if (endindex == -1)
				return;
			sb.delete(index, endindex);
			index = sb.toString().toLowerCase().indexOf(beginstr, 0);
			if (!all)
				return;
		}
	}

	public static String deleteFromString(String orig, String str, boolean all)
	{
		StringBuffer sb = new StringBuffer(orig);
		deleteFromBuffer(sb, str, all);
		return sb.toString();
	}

	public static String deleteFromString(String str, String beginstr, String endstr, boolean all)
	{
		StringBuffer sb = new StringBuffer(str);
		deleteFromBuffer(sb, beginstr, endstr, all);
		return sb.toString();
	}

	public static int extractInteger(String str)
	{
		String ret = "";
		int i;
		
		for (i = 0; i < str.length(); i++)
			if (Character.isDigit(str.charAt(i)))
				break;
		for (; i < str.length(); i++)
			if (Character.isDigit(str.charAt(i)))
				ret += str.charAt(i);
			else
				break;
		if (ret.length() == 0)
			return 0;	
		return Integer.parseInt(ret);
	}

	public static String format(Object obj, int spaces, int justification)
	{
		String spacestr = "                                                  ";
		String str = obj.toString();
		int len = spaces-str.length();
		int half = len/2;

		if (len <= 0)
			return str.substring(0, spaces);

		while (spacestr.length() < spaces)			// make sure we have enough padding
			spacestr += spacestr;

		if (justification == 1)						// center
			return spacestr.substring(0, half)+str+spacestr.substring(0, len-half);
		else if (justification == 2)				// right
			return spacestr.substring(0, len)+str;	// left
		return str+spacestr.substring(0, len);
	}

	public static String[] getColumns(String str)
	{
		int sindex = 0;
		int eindex = str.indexOf("	");
		ArrayList<String> list = new ArrayList<String>();
		
		while (eindex != -1)
		{
			list.add(str.substring(sindex, eindex));
			sindex = eindex+1;
			eindex = str.indexOf("	", sindex);
		}
		list.add(str.substring(sindex));
		String[] ret = new String[list.size()];
		
		for (int i = 0; i < ret.length; i++)
			ret[i] = list.get(i);
		return ret;
	}

	public static String getString(String str, String start, String end, int index, boolean incStartEnd)
	{
		StringBuffer sb = new StringBuffer(str);
		return getString(sb, start, end, index, incStartEnd);
	}

	public static String getString(StringBuffer sb, String start, String end, int index, boolean incStartEnd)
	{
		if (sb.toString().indexOf(start, index) == -1)
			return null;
		int startindex = sb.toString().indexOf(start, index) + (incStartEnd ? 0 : start.length());
		int endindex = sb.toString().indexOf(end, startindex+(incStartEnd ? start.length() : 0))+(incStartEnd ? end.length() : 0);
//System.out.println(sb.substring(startindex));
//System.out.println("s="+startindex+" e="+endindex);
		return sb.substring(startindex, endindex);
	}

	public static String[] getTokens(String str, String seps)
	{
		StringTokenizer toks = new StringTokenizer(str, seps);
		String[] ret = new String[toks.countTokens()];
		
		for (int i = 0; i < ret.length; i++)
			ret[i] = toks.nextToken();
		return ret;
	}

	public static boolean hasAlpha(String str)
	{
		for (int i = 0; i < str.length(); i++)
			if (Character.isLetter(str.charAt(i)))
				return true;
		return false;	
	}

	public static boolean isEqual(Object obj1, Object obj2)
	{
		String value1 = (obj1 == null ? "" : obj1.toString());
		String value2 = (obj2 == null ? "" : obj2.toString());
		
		if (obj1 == obj2)
			return true;
		if ((obj1 == null) && value2.equals(""))
			return false;
		if ((obj2 == null) && value1.equals(""))
			return false;
		return value1.equals(value2);
	}

	public static boolean isValueEmpty(Object str)
	{
		if ((str == null) || (str.toString().length() == 0))
			return true;
		return false;
	}

	public static String replaceString(String orig, String oldstr, String newstr, boolean all)
	{
		StringBuffer sb = new StringBuffer(orig);
		replaceString(sb, oldstr, newstr, all);
		return sb.toString();
	}

	public static void replaceString(StringBuffer sb, String oldstr, String newstr, boolean all)
	{
		int index = sb.toString().indexOf(oldstr, 0);
		
		while (index != -1)
		{
			sb.delete(index, index+oldstr.length());
			sb.insert(index, newstr);
			if (!all)
				return;
			index = sb.toString().indexOf(oldstr, index+newstr.length());
		}
	}

	public static String stripQuotes(String str)
	{
		if (str.startsWith("\"") && str.endsWith("\""))
			str = str.substring(1, str.length()-1);
		if (str.startsWith("'") && str.endsWith("'"))
			str = str.substring(1, str.length()-1);
			
		return str;
	}
}