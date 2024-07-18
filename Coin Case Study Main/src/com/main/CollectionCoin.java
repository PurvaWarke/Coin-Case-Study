package com.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.coin.Coin;
import com.db.connection.DatabaseConnectivity;


public class CollectionCoin {
	
	static Connection con=DatabaseConnectivity.getConnection();
	static List<Coin> collection=new ArrayList<Coin>();
	static List<String> operation=new ArrayList<String>();
	static Scanner sc=new Scanner(System.in);
	static int coinid=1;
	public static void main(String[] args) throws FileNotFoundException
	{
		CoinOperation();
	}
	public static void CoinOperation()
	{
		dataFromDatabase();
		
		int choice;
		
		do
		{
			System.out.println("\n------- COIN COLLECTION SYSTEM -------");
			System.out.println("\n\t1.Insert Coins \n\t2.Update Coins \n\t3.Delete Coins \n\t4.Display Collection \n\t5.Search Coin \n\t0.Exit");
			System.out.println("\nEnter Your Choice : ");
			choice=sc.nextInt();
			switch(choice)
			{
				case 1:
				{
					System.out.println("\t1.Insert New Coin \n\t2.Bulk Coin Collection \n\t0.Exit");
					System.out.println("\nEnter Your Choice : ");
					choice=sc.nextInt();
					switch(choice)
					{
						case 1:
						{
							insertCoin();
							break;
						}
							
						case 2:
						{
							try 
							{
								addFileData();
							} 
							catch (FileNotFoundException e) 
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						}	
					}
					break;
				}
				case 2:
				{
					updateCoin();
					break;
				}
				case 3:
				{
					deleteCoin();
					break;
				}
				case 4:
				{
					try {
						displayConnection();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case 5:
				{
					searchCoin();
				}
				case 0:
				{
					addDataInDatabase();
					System.out.println("THANK YOU !!");
					break;
				}
			}
		}while(choice!=0);
	}

	public static void dataFromDatabase() {
		// TODO Auto-generated method stub
		String query="select * from coin";
		try 
		{
			Statement stmt=con.createStatement();
			ResultSet rs= stmt.executeQuery(query);
			while(rs.next())
			{
				Coin coin=new Coin(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getDouble(5),rs.getDate(6).toString());
				collection.add(coin);
				operation.add("un");
				coinid++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void addDataInDatabase() {
		
		for(int i=0;i<operation.size();i++)
		{
			if(!operation.get(i).equalsIgnoreCase("uc"))
			{
				switch(operation.get(i))
				{
					case "ins":
					{
						String query="insert into coin values(?,?,?,?,?,?)";
						try
						{
							PreparedStatement pstmt=con.prepareStatement(query);
							pstmt.setObject(1, collection.get(i).getCoinId());
							pstmt.setObject(2, collection.get(i).getCountry());
							pstmt.setObject(3, collection.get(i).getDenomination());
							pstmt.setObject(4, collection.get(i).getYearOfMinting());
							pstmt.setObject(5, collection.get(i).getCurrentValue());
							pstmt.setObject(6, collection.get(i).getAcquiredDateString());
							pstmt.executeUpdate();
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
					case "up":
					{
						String query="update coin set country = ?, denomination=?,yearOfMinting=?,currentValue= ?,acquiredDate=? where coinid=?";
						try 
						{
							PreparedStatement pstmt=con.prepareStatement(query);
							pstmt.setString(1,collection.get(i).getCountry() );
							pstmt.setString(2,collection.get(i).getDenomination() );
							pstmt.setInt(3,collection.get(i).getYearOfMinting() );
							pstmt.setDouble(4,collection.get(i).getCurrentValue());
//							pstmt.setString(5,(collection.get(i).getAcquiredDate().toString()));		//it also works
							pstmt.setDate(5,java.sql.Date.valueOf(collection.get(i).getAcquiredDate()));

							pstmt.setInt(6, collection.get(i).getCoinId());
							int rows =pstmt.executeUpdate();
							System.out.println(rows+" Updated Succesfully !!");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
					case "del":
					{
						String query="delete from coin where coinid = ?";
						try 
						{
							PreparedStatement pstmt=con.prepareStatement(query);
							pstmt.setInt(1, collection.get(i).getCoinId());
							int rows=pstmt.executeUpdate();
							System.out.println(rows+" Updated Succesfully !!");
						} 
						catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
				}
			}
		}
	}

	public static void searchCoin() {
		// TODO Auto-generated method stud
		int choice;
		System.out.println("\n\t1.Create a List \n\t2.Search a Specific Coin \n\t0.Exit");
		System.out.println("Enter Choice : ");
		choice=sc.nextInt();
		switch(choice)
		{
			case 1:
			{
				do {
						System.out.println("\n\t1.Country \n\t2.Year of Minting \n\t3.Current Value (sorted) \n0.Exit");
						System.out.println("Enter Choice : ");
						choice=sc.nextInt();
						switch(choice)
						{
							case 1:
							{
								countryList();
								break;
							}
							case 2:
							{
								yearOfMintingList();
								break;
							}
							case 3:
							{
								currentValueList();
								break;
							}
						}
				}while(choice!=0);
				
				break;
			}
			case 2:
			{
				do {
						System.out.println("\n\t1.Country + Denomination \n\t2.Country + Year of Minting \n\t3.Country + Denomination + Year of Minting \n\t4.Acquired Date + Country \n\t0.Exit");
						System.out.println("Enter Choice : ");
						choice=sc.nextInt();
						switch(choice)
						{
							case 1:
							{
								countryAndDemonimation();
								break;
							}
							case 2:
							{
								countryAndYearofMinting();
								break;
							}
							case 3:
							{
								CountryAndDenominationAndYearofMinting();
								break;
							}
							case 4:
							{
								acquiredDateAndCountry();
								break;
							}
						}
				}while(choice!=0);
				
				break;
			}
		}
	}

	//b part
	private static void CountryAndDenominationAndYearofMinting() {
		// TODO Auto-generated method stub
			System.out.println("Enter Country And Denomination And Year Of Minting want To Search : ");
			System.out.println("Country : ");
			String country=sc.next();
			System.out.println("Denomination : ");
			String denomination=sc.next();
			System.out.println("Year of Minting : ");
			int year=sc.nextInt();
			for(int i=0;i<collection.size();i++)
			{
				if(collection.get(i).getCountry().equalsIgnoreCase(country) && collection.get(i).getDenomination().equalsIgnoreCase(denomination) && collection.get(i).getYearOfMinting()==year )
				{
					System.out.println(collection.get(i));
				}
			}
	}

	private static void acquiredDateAndCountry() {
		// TODO Auto-generated method stub
		System.out.println("Enter Acquire Date And Country want To Search : ");
			
			System.out.println("Acquire Date : ");
			String datestr=sc.next();
			LocalDate date=LocalDate.parse(datestr);
			System.out.println("Country : ");
			String country=sc.next();
			for(int i=0;i<collection.size();i++)
			{
				if(collection.get(i).getAcquiredDate().compareTo(date)==0 && collection.get(i).getCountry().equalsIgnoreCase(country))
				{
					System.out.println(collection.get(i));
				}
			}
	}

	private static void countryAndYearofMinting() {
		// TODO Auto-generated method stub
			System.out.println("Country : ");
			String country=sc.next();
			System.out.println("Year Of Minting : ");
			int yearOfMinting=sc.nextInt();
			for(int i=0;i<collection.size();i++)
			{
				if(collection.get(i).getCountry().equalsIgnoreCase(country) && collection.get(i).getYearOfMinting()==(yearOfMinting))
				{
					System.out.println(collection.get(i));
				}
			}
	}

	private static void countryAndDemonimation() {
		// TODO Auto-generated method stub
			System.out.println("Enter Country And Denomination want To Search : ");
			System.out.println("Country : ");
			String country=sc.next();
			System.out.println("Denomination : ");
			String denomination=sc.next();
			for(int i=0;i<collection.size();i++)
			{
				if(collection.get(i).getCountry().equalsIgnoreCase(country) && collection.get(i).getDenomination().equalsIgnoreCase(denomination))
				{
					System.out.println(collection.get(i));
				}
			}
	}

	///a part
	public static void currentValueList() {
		// TODO Auto-generated method stub
		
		System.out.println("-------------- SORTED COLLECION OF COINS --------------\n");

			Collections.sort(collection,new Comparator<Coin>() 
			{
				@Override
				public int compare(Coin o1, Coin o2) {
					// TODO Auto-generated method stub
					return Double.compare(o1.getCurrentValue(), o2.getCurrentValue());
				}
			});
			for(int i=0;i<collection.size();i++)
			{
				System.out.print(collection.get(i));
			}
//			System.out.println(collection);
	}

	public static void yearOfMintingList() {
		// TODO Auto-generated method stub
		System.out.println("--------------LIST OF YEAR OF MINTING --------------");
			
			for(int i=0;i<collection.size();i++)
			{
					System.out.println(collection.get(i).getYearOfMinting());
			}
	}

	public static void countryList() {
		// TODO Auto-generated method stub
		System.out.println("--------------LIST OF COUNTRY --------------");
			for(int i=0;i<collection.size();i++)
			{
				System.out.println(collection.get(i).getCountry());
			}
	}

	public static void displayConnection() throws FileNotFoundException {
		// TODO Auto-generated method stub
		String format = "| %-8s | %-10s | %-12s | %-15s | %-13s | %-15s |%n";
        StringBuilder sb = new StringBuilder();
        System.out.println(sb.append(String.format(format, "Coin ID", "Country", "Denomination", "Year of Minting", "Current Value", "Acquired Date")));
		for(int i=0;i<collection.size();i++)
			System.out.print(collection.get(i));
		System.out.println(operation);
	}

	public static void deleteCoin() {
		// TODO Auto-generated method stub
			System.out.println("Enter Coin Id You Want to Delete : ");
			int coinid=sc.nextInt();
			for(int i=0;i<collection.size();i++)
			{
				if(collection.get(i).getCoinId()==coinid)
				{
					operation.set(i,"del");
					System.out.println("Entry Will Be Delete After Saving Data !!");
				}
			}
	}
	public static void updateCoin() {
		// TODO Auto-generated method stub

			System.out.println("Enter Coin Id You Want to Update : ");
			int coinid=sc.nextInt();
			int choice=0;
			do {
				
				int count=0;
				for(int i=0;i<collection.size();i++)
				{
					if(collection.get(i).getCoinId()==coinid && !operation.get(i).equalsIgnoreCase("del"))
					{
						System.out.println("1.Update Country \n2.Denomination \n3.Update Year Of Minting \n4.Update Current Value \n5.Update Acquired Date \n0.Back");
						choice=sc.nextInt();
						count=1;
						switch(choice)
						{
							case 1:
							{
								System.out.println("Enter New Country : ");
								String country=sc.next();
								collection.get(i).setCountry(country);
								break;
							}
							case 2:
							{
								System.out.println("Enter New Denomination : ");
								String denomination=sc.next();
								collection.get(i).setDenomination(denomination);
								break;
							}
							case 3:
							{
								System.out.println("Enter New Year Of Minting : ");
								int year=sc.nextInt();
								collection.get(i).setYearOfMinting(year);
								break;
							}
							case 4:
							{
								System.out.println("Enter New Current Value : ");
								double currentValue=sc.nextDouble();
								collection.get(i).setCurrentValue(currentValue);
								break;
							}
							case 5:
							{
								System.out.println("Enter New Acquire Date : ");
								String str=sc.next();
								LocalDate date=LocalDate.parse(str);
								collection.get(i).setAcquiredDate(date);
								break;
							}
						}
						if(operation.get(i).equalsIgnoreCase("un"))
							operation.set(i,"up");
						else if(operation.get(i).equalsIgnoreCase("ins"))
							operation.set(i,"ins");		
					}
				}
				if(count==0)
					System.out.println(coinid+" Coin Id Doesn't Exists !! OR It Was Deleted After Saving Data !!");
		
			}while(choice!=0);
		}

	public static void insertCoin() {
		// TODO Auto-generated method stub

//			System.out.println("Enter Coin Id : ");
//			int coinid=sc.nextInt();
			System.out.println("Enter Country Of Coin : ");
			String country=sc.next();
			System.out.println("Enter Demonination : ");
			String denomination=sc.next();
			System.out.println("Enter Year Of Minting : ");
			int year=sc.nextInt();
			System.out.println("Enter Current Value : ");
			double currentValue=sc.nextDouble();
			System.out.println("Enter Acquired Date : yyyy-mm-dd ");
			String date=sc.next();
			coinid=collection.get(collection.size()-1).getCoinId();
			Coin c1=new Coin(++coinid,country,denomination,year,currentValue,date);
			collection.add(c1);
			System.out.println("Coin Inserted Successfully !!");
			operation.add("ins");
	}
	public static void addFileData() throws  FileNotFoundException
	{
		try (Scanner s = new Scanner(new File("coin.csv"));)
		{
			while(s.hasNext())
			{
				String str=s.nextLine();
				String[] strarr= str.split(",");
				Coin coin=new Coin(Integer.parseInt(strarr[0]),strarr[1],strarr[2],Integer.parseInt(strarr[3]),Double.parseDouble(strarr[4]),strarr[5]);
				 boolean found = false;
	                for (Coin c : collection) 
	                {
	                    if (c.getCoinId() == coin.getCoinId()) 
	                    {
	                        found = true;
	                        break;
	                    }
	                }

	                if (!found) 
	                {
	                    collection.add(coin);
	                    operation.add("ins");
	                    System.out.println(coin.getCoinId()+" Coin Id Inserted Successfully !!");
	                }
	                else 
	                	System.err.println(coin.getCoinId()+" Coin Id Already Added !!");
			}
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
