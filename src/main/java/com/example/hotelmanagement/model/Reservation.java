package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
	private String roomType;
    private int periodOfStay;
    private int numRooms;
    private int numPersons;
    private int numChildren;
    private String buffet;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date arrivalDate;
    
    private double totalAmount;
    
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public int getPeriodOfStay() {
		return periodOfStay;
	}
	public void setPeriodOfStay(int periodOfStay) {
		this.periodOfStay = periodOfStay;
	}
	public int getNumRooms() {
		return numRooms;
	}
	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}
	public int getNumPersons() {
		return numPersons;
	}
	public void setNumPersons(int numPersons) {
		this.numPersons = numPersons;
	}
	public int getNumChildren() {
		return numChildren;
	}
	public void setNumChildren(int numChildren) {
		this.numChildren = numChildren;
	}
	public String getBuffet() {
		return buffet;
	}
	public void setBuffet(String buffet) {
		this.buffet = buffet;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
    
    

}
