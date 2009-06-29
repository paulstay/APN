package com.teag.webapp;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.estate.db.DBObject;

//import com.estate.db.Account;

public class SystemGlobals extends HashMap<String,Object> implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final String ACCESS_FULL = "FULL";
	public static final String ACCESS_EDIT = "EDIT";
	public static final String ACCESS_VIEW = "VIEW";

	public static DecimalFormat number = new DecimalFormat("0.00");
	public static DecimalFormat weight = new DecimalFormat("0.0000");
	public static DecimalFormat moneyformat = new DecimalFormat("$0.00");
	public static DecimalFormat dollarformat = new DecimalFormat("0.00");
	public static DecimalFormat taxformat = new DecimalFormat("0.0000%");
	public static DecimalFormat percentformat = new DecimalFormat("0.00%");
	public static SimpleDateFormat timeshortformat = new SimpleDateFormat("hh:mm a");
	public static SimpleDateFormat timeshort24format = new SimpleDateFormat("HH:mm");
	public static SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm:ss a");
	public static SimpleDateFormat time24format = new SimpleDateFormat("HH:mm:ss");
	public static SimpleDateFormat dateformat = new SimpleDateFormat("M/d/yyyy");
	public static SimpleDateFormat dateblockformat = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat datetimeformat = new SimpleDateFormat("M/d/yyyy hh:mm:ss a");
	public static SimpleDateFormat datetimedbformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public String inc_path = "/inc";
	public String app_name = "";
	public String app_root = "";
	public String app_webroot = "";
	public String app_curpage = "";
	public String menuLink = "";
	public String menuTitle = "";
	public String user_mode = "";
	public String[] coltab = {"bg-color1", "bg-color2"};
	public String body_extra = "";
	public String pagetitle = "";
	public HashMap<String,Object> error_fields = new HashMap<String,Object>();
	public ArrayList<String> input_params = new ArrayList<String>();
	public HashMap<String,Object> variables = new HashMap<String,Object>();

	public String status_msg = "";
	public DBObject dbobj = new DBObject();

	public int help_width = 600;
	public int help_height = 600;

	public SystemGlobals()
	{
	}
	/**
	 */
	@Override
	protected void finalize()
	{
		//System.out.println("SystemGlobals Terminated!!!!!!!!!!!!!!!!!");
	}

	public String formatPhone(Object phone, boolean traditional)
	{
		if (phone == null)
			return "";
		return formatPhone(phone.toString(), traditional);
	}

	// traditional = (xxx) xxx-xxxx
	// new = xxx.xxx.xxxx
	public String formatPhone(String phone, boolean traditional)
	{
		if (phone == null)
			return "";
		phone = getBarePhone(phone);
		if (traditional)
		{
			if (phone.length() == 10)
				return "("+phone.substring(0, 3)+") "+phone.substring(3, 6)+"-"+phone.substring(6);
			if (phone.length() == 7)
				return phone.substring(0, 3)+"-"+phone.substring(3);
		}
		else
		{
			if (phone.length() == 10)
				return phone.substring(0, 3)+"."+phone.substring(3, 6)+"."+phone.substring(6);
			if (phone.length() == 7)
				return phone.substring(0, 3)+"."+phone.substring(3);
		}
		return phone;
	}

	public String getBarePhone(String phone)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < phone.length(); i++)
		{
			if (Character.isDigit(phone.charAt(i)))
				sb.append(phone.charAt(i));
		}
		if ((sb.length() == 10) || (sb.length() == 7))
			return sb.toString();
		return phone;
	}

	public int imageHeightMax(int width, int height, int max_width, int max_height)
	{
		if (height == 0)
			return max_height;
		double wratio = (double) width / (double) max_width;
		double hratio = (double) height / (double) max_height;
		double ratio = (wratio > hratio ? wratio : hratio);
		int new_height = (int)(height / ratio);
		return (new_height < height ? new_height : height);
	}

	public int imageWidthMax(int width, int height, int max_width, int max_height)
	{
		if (width == 0)
			return max_width;
		double wratio = (double) width / (double) max_width;
		double hratio = (double) height / (double) max_height;
		double ratio = (wratio > hratio ? wratio : hratio);
		int new_width = (int)(width / ratio);
		return (new_width < width ? new_width : width);
	}
}
