/**
 * COPYRIGHT @ Lure Technologies, LLC.
 * ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or
 * form other than in accordance with and subject to the terms of a written
 * license from Lure or with the prior written consent of Lure or as
 * permitted by applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure.  If you are neither the
 * intended recipient, nor an agent, employee, nor independent contractor
 * responsible for delivering this message to the intended recipient, you are
 * prohibited from copying, disclosing, distributing, disseminating, and/or
 * using the information in this email in any manner. If you have received
 * this message in error, please advise us immediately at
 * legal@luretechnologies.com by return email and then delete the message from your
 * computer and all other records (whether electronic, hard copy, or
 * otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */
package com.luretechnologies.tms.ui.view.dashboard;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.luretechnologies.tms.backend.data.ConnectionStats;
import com.luretechnologies.tms.backend.data.DashboardData;
import com.luretechnologies.tms.ui.components.DownloadGrid;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.Marker;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.board.Row;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;

/**
 * The dashboard view showing statistics about sales and deliveries.
 * <p>
 * Created as a single View class because the logic is so simple that using a
 * pattern like MVP would add much overhead for little gain. If more complexity
 * is added to the class, you should consider splitting out a presenter.
 */
@SpringView
public class DashboardView extends DashboardViewDesign implements View {


	private final NavigationManager navigationManager;
	private ListSeries[] callsPerPeriod;
	
	private static final String CALLS = "Calls";
	
	private final BoardLabel currentConnectionsLabel = new BoardLabel("CURRENT CONNECTIONS", "3/7", "current connections");
	private final BoardLabel successfulDownloadsLabel = new BoardLabel("SUCCESSFUL DOWNLOADS(24 HOURS)", "1", "sucessful connections(24 hours)");
	private final BoardLabel requestPerSecondLabel = new BoardLabel("REQUESTS PER SECOND", "2", "request per second");
	private final BoardLabel downloadFailuresLabel = new BoardLabel("DOWNLOAD FAILURES(24 HOURS)", "4", "download failures(24 hours)");
	private final BoardBox downloadFailuresBox = new BoardBox(downloadFailuresLabel);
	
	private final Chart incomingServiceCalls = new Chart(ChartType.COLUMN);
	private final Chart incomingServiceCallsArea = new Chart(ChartType.AREA);
	private final Chart incomingServiceCallsPie = new Chart(ChartType.PIE);
	private final Grid<HashMap<String, String>> grid = new Grid<>();
	
	private DataSeries serviceCalls;
	private DataSeries incomingCallsSeries;
	private List<Number> list = new ArrayList<Number>();


	@Autowired
	public DashboardView(NavigationManager navigationManager) {
		this.navigationManager = navigationManager;
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		setResponsive(true);
		//grid.addColumn(hashmap -> )
		
		grid.setItems((new DownloadGrid().getFakeData()));
		
		HashMap<String, String> s = new DownloadGrid().getFakeData().get(0);
		
		for (Map.Entry<String, String> entry : s.entrySet()) {
			grid.addColumn(h -> h.get((entry.getKey()))).setCaption(entry.getKey());
			}
		
		Row row = board.addRow(new BoardBox(currentConnectionsLabel),new BoardBox(successfulDownloadsLabel),
				new BoardBox(requestPerSecondLabel), downloadFailuresBox);
		row.addStyleName("board-row-group");
		
		row = board.addRow(new BoardBox(incomingServiceCalls));
		row.addStyleName("board-row-panels");
		
		row = board.addRow(new BoardBox(incomingServiceCallsArea));
		row.addStyleName("board-row-panels");
		
		row = board.addRow(new BoardBox(incomingServiceCallsPie), new BoardBox(grid, "due-grid"));
		row.addStyleName("board-row-panels");


		
		initIncomingCallsGraphs();
		initIncomingCallsGraphsArea();
		initIncomingCallsGraphsPie();
		
		grid.setId("grid");
		grid.setSizeFull(); 
		 
	}
	
	private void initIncomingCallsGraphsPie() {
		incomingServiceCallsPie.setId("Incoming Calls");
		incomingServiceCallsPie.setSizeFull();

		LocalDate today = LocalDate.now();

		Configuration conf = incomingServiceCallsPie.getConfiguration();
		String thisMonth = today.getMonth().getDisplayName(TextStyle.FULL, Locale.US);
		conf.setTitle("INCOMING CALLS " + thisMonth);
		incomingCallsSeries = new DataSeries(CALLS);
		conf.addSeries(incomingCallsSeries);

		conf.getyAxis().setTitle("");

	}
	
	private void initIncomingCallsGraphsArea() {
		List<String> list = Arrays.asList("Heartbeat","Download");
		incomingServiceCallsArea.setId("yearlySales");
		incomingServiceCallsArea.setSizeFull();
		int year = Year.now().getValue();

		Configuration conf = incomingServiceCallsArea.getConfiguration();
		conf.setTitle("INCOMING CALLS");
		conf.getxAxis().setCategories(getMonthNames());
		conf.getChart().setMarginBottom(6);

		PlotOptionsLine options = new PlotOptionsLine();
		options.setMarker(new Marker(false));
		options.setShadow(true);
		conf.setPlotOptions(options);

		callsPerPeriod = new ListSeries[2];
		for (int i = 0; i < callsPerPeriod.length; i++) {
			callsPerPeriod[i] = new ListSeries(list.get(i));
			callsPerPeriod[i].setPlotOptions(new PlotOptionsLineWithZIndex(list.get(i)));
			conf.addSeries(callsPerPeriod[i]);
		}
		conf.getyAxis().setTitle("");

	}
	
	private String[] getMonthNames() {
		return Stream.of(Month.values()).map(month -> month.getDisplayName(TextStyle.SHORT, Locale.US))
				.toArray(size -> new String[size]);
	}
	
	public static class PlotOptionsLineWithZIndex extends PlotOptionsLine {
		@SuppressWarnings("unused")
		private String Index;

		public PlotOptionsLineWithZIndex(String Index) {
			this.Index = Index;
		};
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		updateLabels(new ConnectionStats(2,3,4.1,5));
		updateGraphs(fetchData());
	}
	
	private DashboardData fetchData() {
		return getDashboardData(MonthDay.now().getMonthValue(), Year.now().getValue());
	}
	
	public DashboardData getDashboardData(int month, int year) {
		
		DashboardData data = new DashboardData();
		data.setDeliveriesThisMonth(list);

		return data;
	}
	
	
	private void updateGraphs(DashboardData data) {
		serviceCalls.addData(new Number[][]{{0, 1}, {2, 2}, {3,8},{5,6},{10, 3}});
		
		callsPerPeriod[0].setData(Arrays.asList(5,16, 7,19,9));
		callsPerPeriod[1].setData(Arrays.asList(7,19,9,19,9));
		
		incomingCallsSeries.add(new DataSeriesItem("Admin", 22));
		incomingCallsSeries.add(new DataSeriesItem("Admin1", 23));
		incomingCallsSeries.add(new DataSeriesItem("Admin2", 24));
		incomingCallsSeries.add(new DataSeriesItem("Admin3", 25));
		incomingCallsSeries.add(new DataSeriesItem("Admin4", 26));
	}
	private void updateLabels(ConnectionStats deliveryStats) {
		currentConnectionsLabel.setContent(Integer.toString(deliveryStats.getCurrentConnections()));
		successfulDownloadsLabel.setContent(Integer.toString(deliveryStats.getSuccessfulDownloads()));
		requestPerSecondLabel.setContent(Double.toString(deliveryStats.getRequestPerSeconds()));
		downloadFailuresLabel.setContent(Integer.toString(deliveryStats.getDownloadFaliures()));
		downloadFailuresBox.setNeedsAttention((deliveryStats.getDownloadFaliures() > 0));
	}
	
	private void initIncomingCallsGraphs() {
		LocalDate today = LocalDate.now();

		incomingServiceCalls.setId("serviceCalls");
		incomingServiceCalls.setSizeFull();

		Configuration monthConf = incomingServiceCalls.getConfiguration();
		String thisMonth = today.getMonth().getDisplayName(TextStyle.FULL, Locale.US);
		monthConf.setTitle("INCOMING SERVICE CALLS");
		monthConf.getChart().setMarginBottom(6);
		monthConf.getLegend().setEnabled(false);
		serviceCalls = new DataSeries(CALLS);
		monthConf.addSeries(serviceCalls);
		configureColumnSeries(serviceCalls);

		int daysInMonth = YearMonth.of(today.getYear(), today.getMonthValue()).lengthOfMonth();
		String[] categories = IntStream.rangeClosed(1, daysInMonth).mapToObj(Integer::toString)
				.toArray(size -> new String[size]);
		monthConf.getxAxis().setCategories(categories);
		monthConf.getxAxis().setLabels(new Labels(false));
	}

	protected void configureColumnSeries(DataSeries series) {
		PlotOptionsColumn options = new PlotOptionsColumn();
		options.setPointWidth(100);
		Color color = SolidColor.GREEN;
		options.setBorderWidth(1);
		options.setGroupPadding(0);
		options.setColor(color);
		series.setPlotOptions(options);

		YAxis yaxis = series.getConfiguration().getyAxis();
		yaxis.setGridLineWidth(0);
		yaxis.setLabels(new Labels(false));
		yaxis.setTitle("Range");
		
		XAxis xaxis = series.getConfiguration().getxAxis();
		xaxis.setGridLineWidth(0);
		xaxis.setLabels(new Labels(false));
		xaxis.setTitle("HeartBeat");
	}
}
