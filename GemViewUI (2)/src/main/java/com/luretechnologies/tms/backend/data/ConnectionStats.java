package com.luretechnologies.tms.backend.data;

public class ConnectionStats {

	public ConnectionStats(int currentConnections, int successfulDownloads, double requestPerSeconds, int downloadFaliures) {
		super();
		this.currentConnections = currentConnections;
		this.successfulDownloads = successfulDownloads;
		this.requestPerSeconds = requestPerSeconds;
		this.downloadFaliures = downloadFaliures;
	}

	private int currentConnections;
	private int successfulDownloads;
	private double requestPerSeconds;
	private int downloadFaliures;
	
	public int getCurrentConnections() {
		return currentConnections;
	}
	public void setCurrentConnections(int currentConnections) {
		this.currentConnections = currentConnections;
	}
	public int getSuccessfulDownloads() {
		return successfulDownloads;
	}
	public void setSuccessfulDownloads(int successfulDownloads) {
		this.successfulDownloads = successfulDownloads;
	}
	public double getRequestPerSeconds() {
		return requestPerSeconds;
	}
	public void setRequestPerSeconds(double requestPerSeconds) {
		this.requestPerSeconds = requestPerSeconds;
	}
	public int getDownloadFaliures() {
		return downloadFaliures;
	}
	public void setDownloadFaliures(int downloadFaliures) {
		this.downloadFaliures = downloadFaliures;
	}
	//private int newOrders;

}
