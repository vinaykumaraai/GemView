package com.luretechnologies.tms.backend.data;

import java.util.LinkedHashMap;
import java.util.List;

public class DashboardData {

	public DashboardData(List<Number> incomingServiceCalls/*, Number[][] salesPerMonth*/) {
		super();
		this.incomingServiceCalls = incomingServiceCalls;
		//this.salesPerMonth=salesPerMonth;
	}

	private ConnectionStats deliveryStats;
	private List<Number> incomingServiceCalls;
	private List<Number> deliveriesThisYear;
	private Object salesLastYears;
	private Number[][] salesPerMonth;
	//private LinkedHashMap<Product, Integer> productDeliveries;

	public ConnectionStats getDeliveryStats() {
		return deliveryStats;
	}

	public void setDeliveryStats(ConnectionStats deliveryStats) {
		this.deliveryStats = deliveryStats;
	}

	public List<Number> getDeliveriesThisMonth() {
		return incomingServiceCalls;
	}

	public void setDeliveriesThisMonth(List<Number> incomingServiceCalls) {
		this.incomingServiceCalls = incomingServiceCalls;
	}

	public List<Number> getDeliveriesThisYear() {
		return deliveriesThisYear;
	}

	public void setDeliveriesThisYear(List<Number> deliveriesThisYear) {
		this.deliveriesThisYear = deliveriesThisYear;
	}

	public Object getSalesLastYears() {
		return salesLastYears;
	}

	public void setSalesLastYears(Object salesLastYears) {
		this.salesLastYears = salesLastYears;
	}

	public Number[][] getSalesPerMonth() {
		return salesPerMonth;
	}

	public void setSalesPerMonth(Number[][] salesPerMonth) {
		this.salesPerMonth = salesPerMonth;
	}

	public Number[] getSalesPerMonth(int i) {
		return salesPerMonth[i];
	}

//	public LinkedHashMap<Product, Integer> getProductDeliveries() {
//		return productDeliveries;
//	}
//
//	public void setProductDeliveries(LinkedHashMap<Product, Integer> productDeliveries) {
//		this.productDeliveries = productDeliveries;
//	}

}
