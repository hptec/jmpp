package cn.cerestech.framework.core.http;



public class IPEntry
{

	public String beginIp;
	public String endIp;
	public String country;
	public String area;

	public IPEntry()
	{
		beginIp = endIp = country = area = "";
	}

	public String toString()
	{
		return (new StringBuilder(String.valueOf(area))).append("  ").append(country).append("IP��Χ:").append(beginIp).append("-").append(endIp).toString();
	}

	public String getBeginIp()
	{
		return beginIp;
	}

	public void setBeginIp(String beginIp)
	{
		this.beginIp = beginIp;
	}

	public String getEndIp()
	{
		return endIp;
	}

	public void setEndIp(String endIp)
	{
		this.endIp = endIp;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getArea()
	{
		return area;
	}

	public void setArea(String area)
	{
		this.area = area;
	}
}
