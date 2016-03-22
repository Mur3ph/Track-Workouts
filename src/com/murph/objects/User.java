package com.murph.objects;

public class User 
{
	private int userId;
	private String name;
	private String username;
	private String password;
	private String sex;
	private String age;
	private String county;
	private String country;
	private String about;
	
	public User() {
		super();
	}

	public User(int userId, String name, String username, String password, String sex,
			String age, String about, String county, String country) 
	{
		this.userId = userId;
		this.name = name;
		this.username = username;
		this.password = password;
		this.sex = sex;
		this.age = age;
		this.about = about;
		this.county = county;
		this.country = country;
	}
	
	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public User(int id, String name, String username, String password) 
	{
		userId = id;
		this.name = name;
		this.username = username;
		this.password = password;
	}
	
	public User(String name) 
	{
		this.name = name;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() 
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getUsername() 
	{
		return username;
	}
	
	public void setUsername(String username) 
	{
		this.username = username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password) 
	{
		this.password = password;
	}
	
	public String getSex()
	{
		return sex;
	}
	
	public void setSex(String sex) 
	{
		this.sex = sex;
	}
	
	public String getAge() 
	{
		return age;
	}
	
	public void setAge(String age) 
	{
		this.age = age;
	}
	
	public String getCounty() 
	{
		return county;
	}
	
	public void setCounty(String county) 
	{
		this.county = county;
	}
	
	public String getCountry() 
	{
		return country;
	}
	
	public void setCountry(String country) 
	{
		this.country = country;
	}

	@Override
	public String toString() {
		return "Number = " + userId + " " + " Username = "
				+ username;
	}
	
	
	
}
