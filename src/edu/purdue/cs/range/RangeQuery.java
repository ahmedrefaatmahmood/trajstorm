package edu.purdue.cs.range;

import java.util.HashSet;

public class RangeQuery {
	private int queryID; 
	private Double xMin, yMin, xMax, yMax;
	
	private HashSet<Integer> currentObjects; 
	
	public boolean objectAlreadyContained(int objectID) {
		return currentObjects.contains(objectID);
	}
	
	public void addObject(int objectID) {
		this.currentObjects.add(objectID);
	}
	
	public void removeObject(int objectID) {
		this.currentObjects.remove(objectID);
	}
	
	public int getQueryID() {
		return queryID;
	}
	
	public void setQueryID(int id) {
		this.queryID = id;
	}
	
	public Double getXMin() {
		return xMin;
	}
	
	public void setXMin(Double xMin) {
		this.xMin = xMin;
	}
	
	public Double getYMin() {
		return yMin;
	}
	
	public void setYMin(Double yMin) {
		this.yMin = yMin;
	}
	
	public Double getXMax() {
		return xMax;
	}
	
	public void setXmax(Double xMax) {
		this.xMax = xMax;
	}
	
	public Double getYMax() {
		return yMax;
	}
	
	public void setYMax(Double yMax) {
		this.yMax = yMax;
	}
	
	public RangeQuery(int id, Double xMin, Double yMin, Double xMax, Double yMax) {
		this.currentObjects = new HashSet<Integer>();
		
		this.queryID = id;
	
		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
	}
	
	public boolean isInsideRange(Double double1, Double double2){
		return (double1 >= xMin && 
					double1 <= xMax &&
					double2 >= yMin &&
					double2 <= yMax);		
	}
}
