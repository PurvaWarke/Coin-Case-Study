package com.coin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Coin
{
	int coinId;
	String country;
	String denomination;
	int yearOfMinting;
	double currentValue;
	LocalDate acquiredDate;
	
	public Coin() 
	{
		// TODO Auto-generated constructor stub
	}

	public Coin(int coinId, String country, String denomination, int yearOfMinting, double currentValue,
			String acquiredDate) {
		this.coinId = coinId;
		this.country = country;
		this.denomination = denomination;
		this.yearOfMinting = yearOfMinting;
		this.currentValue = currentValue;
		this.acquiredDate = LocalDate.parse(acquiredDate);
	}

	public int getCoinId() {
		return coinId;
	}

	public void setCoinId(int coinId) {
		this.coinId = coinId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public int getYearOfMinting() {
		return yearOfMinting;
	}

	public void setYearOfMinting(int yearOfMinting) {
		this.yearOfMinting = yearOfMinting;
	}

	public double getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}

	public LocalDate getAcquiredDate() {
		return acquiredDate;
	}
	public String getAcquiredDateString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return acquiredDate.format(formatter);
	}

	public void setAcquiredDate(LocalDate acquiredDate) {
		this.acquiredDate = acquiredDate;
	}

	@Override
	public String toString() {
//		return "Coin [coinId=" + coinId + ", country=" + country + ", denomination=" + denomination + ", yearOfMinting="
//				+ yearOfMinting + ", currentValue=" + currentValue + ", acquiredDate=" + acquiredDate + "]";
//	
		String format = "| %-8s | %-10s | %-12s | %-15s | %-13s | %-15s |%n";
        StringBuilder sb = new StringBuilder();
//        
//        // Adding header
//        sb.append(String.format(format, "Coin ID", "Country", "Denomination", "Year of Minting", "Current Value", "Acquired Date"));
// 
        // Adding coin details
        sb.append(String.format(format, coinId, country, denomination, yearOfMinting, currentValue, acquiredDate));
        
        return sb.toString();	
	}

}
