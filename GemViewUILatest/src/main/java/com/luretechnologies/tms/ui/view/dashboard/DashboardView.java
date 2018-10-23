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

import java.sql.Array;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import com.luretechnologies.tms.backend.data.entity.ConnectionStats;
import com.luretechnologies.tms.backend.data.entity.Downloads;
import com.luretechnologies.tms.backend.service.DashboardService;
import com.luretechnologies.tms.backend.service.UserService;
import com.luretechnologies.tms.ui.MainView;
import com.luretechnologies.tms.ui.components.MainViewIconsLoad;
import com.luretechnologies.tms.ui.navigation.NavigationManager;
import com.luretechnologies.tms.ui.view.Header;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.Marker;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.board.Row;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

/**
 * The dashboard view showing statistics about sales and deliveries.
 * <p>
 * Created as a single View class because the logic is so simple that using a
 * pattern like MVP would add much overhead for little gain. If more complexity
 * is added to the class, you should consider splitting out a presenter.
 */
@SpringView
public class DashboardView extends DashboardViewDesign implements View {

	
	private static final DateTimeFormatter  dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	private final NavigationManager navigationManager;
	private ListSeries[] callsPerPeriod;
	
	private static final String CALLS = "Calls";
	
	private final BoardLabel currentConnectionsLabel = new BoardLabel("CURRENT CONNECTIONS", "", "current connections");
	private final BoardLabel successfulDownloadsLabel = new BoardLabel("SUCCESSFUL DOWNLOADS (24 HOURS)", "", "sucessful connections (24 hours)");
	private final BoardLabel requestPerSecondLabel = new BoardLabel("REQUESTS PER SECOND", "", "request per second");
	private final BoardLabel downloadFailuresLabel = new BoardLabel("DOWNLOAD FAILURES (24 HOURS)", "", "download failures (24 hours)");
	private final BoardBox downloadFailuresBox = new BoardBox(downloadFailuresLabel);
	private final Label currentDownloadsLabel = new Label("CURRENT DOWNLOADS");

	private final Chart incomingRequestCallsPerWeek = new Chart(ChartType.COLUMN);
	private final Chart incomingServiceCallsArea = new Chart(ChartType.AREA);
	private final Chart incomingServiceCallsPie = new Chart(ChartType.PIE);
	private  Grid<Downloads> grid ;
	
	private DataSeries serviceCalls;
	private DataSeries incomingCallsSeries;
	private DataSeries heartBeatServicesPerWeek;
	private DataSeries downloadServicesPerWeek;
	private List<Number> list = new ArrayList<Number>();
	private Row row;
	private Map<String, Number> heartBeatPerWeek;
	private Map<String, Number> downloadDataPerWeek;


	@Autowired
	public DashboardView(NavigationManager navigationManager) {
		this.navigationManager = navigationManager;
	}
	
	@Autowired
	DashboardService dashBoardService;
	
	@Autowired
	MainView mainView;
	
	@Autowired
	UserService userService;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
//		ComboBox refresh = new ComboBox();
//		refresh.setItems("Refresh 30 secs","Refresh 1 min","Refresh 5 mins","Refresh 30 mins","Refresh 1 hr");
//		refresh.setPlaceholder("Select Time");
//		refresh.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", "header-Components");
//		
//		refresh.addSelectionListener(listener->{
//			if(listener!=null && listener.getValue().toString().equals("Refresh 30 secs")) {
//				UI.getCurrent().setPollInterval(30000);
//			}
//		});
//		
//		ComboBox downloads = new ComboBox();
//		downloads.setItems("Downloads 1","Downloads 2","Downloads 3","Downloads 4","Downloads 5");
//		downloads.setPlaceholder("Select Option");
//		downloads.addStyleNames(ValoTheme.LABEL_LIGHT, "v-textfield-font", "v-combobox-size", 
//				"header-Components");
		
		Header header = new Header(userService,navigationManager, "Dashboard");
		header.setId("header");
		setResponsive(true);
		grid = new Grid<>(Downloads.class);
		grid.setWidth("100%");
		grid.setResponsive(true);
		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.setColumns("serialNumber", "organizationName", "OS", "incomingIP", "device", "completion");
		grid.getColumn("serialNumber").setCaption("Serial Number");
		grid.getColumn("organizationName").setCaption("Organization Name");
		grid.getColumn("OS").setCaption("OS");
		grid.getColumn("incomingIP").setCaption("Incoming IP");
		grid.getColumn("device").setCaption("Device");
		grid.getColumn("completion").setCaption("Completion");
		
		grid.setDataProvider(new ListDataProvider<Downloads>( dashBoardService.getDownloadsData()));
		grid.setCaptionAsHtml(true);
		grid.setCaption("<h2 style=margin-bottom:10px;color:#0000008f;font-weight:400;>Current Downloads"
				+ "</h2>");
		grid.setHeaderVisible(true);
		grid.addStyleName("dashboard-gridBackground");
		
		heartBeatPerWeek = dashBoardService.getHeartBeatDataPerWeek();
		downloadDataPerWeek = dashBoardService.getDownloadDataPerWeek();
		
		/*Header header = new Header(userService,navigationManager, "Dashboard", new Label());
		row = board.addRow(header);
		row.addStyleName("board-row-group");*/
		board.addStyleNames("board-top");
		Row row1 = board.addRow(new BoardBox(currentConnectionsLabel),new BoardBox(successfulDownloadsLabel),
				new BoardBox(requestPerSecondLabel), downloadFailuresBox);
		row1.addStyleName("board-row-group");
		
		
		/*row = board.addRow(new BoardBox(incomingServiceCalls));
		row.addStyleName("board-row-panels");*/
		
		row = board.addRow();
		row.addStyleName("board-row-panels");
		
		Row row2 = board.addRow(new BoardBox(incomingRequestCallsPerWeek), new BoardBox(incomingServiceCallsArea));
		row2.addStyleName("board-row-panels");
		
		Row row3 = board.addRow(new BoardBox(grid, "due-grid"));
		row3.addStyleNames("board-row-panels");

		
		initIncomingCallsGraphs();
		initIncomingCallsGraphsArea();
		initIncomingCallsGraphsPie();
		
		grid.setId("grid");
		grid.setSizeFull(); 
		
		int width = Page.getCurrent().getBrowserWindowWidth();
		if(width<=600) {
			mainView.getTitle().setValue(userService.getLoggedInUserName());
			grid.setHeight("88%");
			removeComponent(header);
			board.removeStyleName("board-top");
			MainViewIconsLoad.noIconsOnDesktopMode(mainView);
		}else if(width>600 && width <=1000) {
			grid.setHeight("86%");
			if((getComponent(0).getId()!=null || getComponent(0).getId()!="") &&
					getComponent(0).getId().equals("header")) {
				
			} else {
				addComponentAsFirst(header);
				board.addStyleName("board-top");
			}
			MainViewIconsLoad.iconsOnTabMode(mainView);
		}else {
			mainView.getTitle().setValue("gemView");/*  "+ userService.getLoggedInUserName());
*/			grid.setHeight("85%");
			if((getComponent(0).getId()!=null || getComponent(0).getId()!="") &&
					getComponent(0).getId().equals("header")) {
	
			} else {
				addComponentAsFirst(header);
				board.addStyleName("board-top");
			}
			MainViewIconsLoad.noIconsOnDesktopMode(mainView);
		}
		
		Page.getCurrent().addBrowserWindowResizeListener(resizeListener->{
			if(resizeListener.getWidth()<=600) {
				mainView.getTitle().setValue(userService.getLoggedInUserName());
				grid.setHeight("88%");
				removeComponent(header);
				board.removeStyleName("board-top");
				MainViewIconsLoad.noIconsOnDesktopMode(mainView);
			}else if(resizeListener.getWidth()>600 && resizeListener.getWidth() <=1000) {
				grid.setHeight("86%");
				if((getComponent(0).getId()!=null || getComponent(0).getId()!="") &&
						getComponent(0).getId().equals("header")) {

				} else {
					addComponentAsFirst(header);
					board.addStyleName("board-top");
				}
				MainViewIconsLoad.iconsOnTabMode(mainView);
			}else {
				mainView.getTitle().setValue("gemView");/*  "+ userService.getLoggedInUserName());
*/				grid.setHeight("85%");
				if((getComponent(0).getId()!=null || getComponent(0).getId()!="") &&
						getComponent(0).getId().equals("header")) {

				} else {
					addComponentAsFirst(header);
					board.addStyleName("board-top");
				}
				MainViewIconsLoad.noIconsOnDesktopMode(mainView);
			}
		});
		
			mainView.getDashboard().setEnabled(true);
	}
	
	private void initIncomingCallsGraphsPie() {
		incomingServiceCallsPie.setId("Incoming Calls");
		incomingServiceCallsPie.setSizeFull();

		LocalDate today = LocalDate.now();

		Configuration conf = incomingServiceCallsPie.getConfiguration();
		String thisMonth = today.getMonth().getDisplayName(TextStyle.FULL, Locale.US);
		Title title = new Title();
		title.setUseHTML(true);
		title.setText("<style=color:black>Incoming Request Calls Per Week</style>");
		conf.setTitle(title);
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
		Title title = new Title();
		title.setUseHTML(true);
		title.setText("<span style=color:#0000008f !important;>Incoming Request Calls Per Day</span>");
		conf.setTitle(title);
		//conf.getxAxis().setCategories(getMonthNames());
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
		updateLabels(dashBoardService.getLabelData());
		updateGraphs();
	}
	
	
	private void updateGraphs() {
		//serviceCalls.addData(new Number[][]{{0, 1}, {2, 2}, {3,8},{5,6},{10, 3}});
		
		callsPerPeriod[0].setData(dashBoardService.getHeartBeatDataPerDay());
		callsPerPeriod[1].setData(dashBoardService.getDownloadDataPerDay());
		
		for (Map.Entry<String, Number> entry : heartBeatPerWeek.entrySet()) {
			heartBeatServicesPerWeek.add(new DataSeriesItem("Heartbeat", entry.getValue()));
		}
		
		for (Map.Entry<String, Number> entry : downloadDataPerWeek.entrySet()) {
			downloadServicesPerWeek.add(new DataSeriesItem("Downlaod", entry.getValue()));
		}
			
	}
	private void updateLabels(ConnectionStats deliveryStats) {
		currentConnectionsLabel.setContentSucess(Integer.toString(deliveryStats.getCurrentConnections()));
		successfulDownloadsLabel.setContentSucess(Integer.toString(deliveryStats.getSuccessfulDownloads()));
		requestPerSecondLabel.setContentSucess(Double.toString(deliveryStats.getRequestPerSeconds()));
		downloadFailuresLabel.setContentFaliure(Integer.toString(deliveryStats.getDownloadFaliures()));
		downloadFailuresBox.setNeedsAttention((deliveryStats.getDownloadFaliures() > 0));
	}
	
	private void initIncomingCallsGraphs() {
		List<String> list = Arrays.asList("Heartbeat","Download");
		incomingRequestCallsPerWeek.setId("Request Calls Per Week");
		incomingRequestCallsPerWeek.setSizeFull();
		
		Label availableSystem = new Label("Incoming Request Calls Per Week", ContentMode.HTML);
		availableSystem.addStyleName("label-chartStyle");
		
		Configuration Conf = incomingRequestCallsPerWeek.getConfiguration();
		Title title = new Title();
		title.setUseHTML(true);
		title.setText("<span style=color:#0000008f !important;>Incoming Request Calls Per Week</span>");
		Conf.setTitle(title);
		XAxis x = new XAxis();
		List<String> dates = new ArrayList<>();
		if(downloadDataPerWeek!=null && !downloadDataPerWeek.isEmpty()) {
			for(Map.Entry<String, Number> entry : downloadDataPerWeek.entrySet()) {
				dates.add(entry.getKey());
			}
		}
		if(dates.size()==7) {
			x.setCategories(dates.get(0), dates.get(1), dates.get(2), dates.get(3), dates.get(4), dates.get(5), dates.get(6));
		}
        Conf.addxAxis(x);
        
        YAxis y = new YAxis();
        y.setMin(0);
        y.setTitle("Range");
        Conf.addyAxis(y);
        
        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("function() { return ''+ this.series.name +': '+ this.y +'';}");
        Conf.setTooltip(tooltip);
		
		//String thisMonth = today.getMonth().getDisplayName(TextStyle.FULL, Locale.US);
		
		//monthConf.getChart().setMarginBottom(6);
		Conf.getLegend().setEnabled(false);
		heartBeatServicesPerWeek = new DataSeries("Heartbeat Calls");
		downloadServicesPerWeek= new DataSeries("Download Calls");
		//heartBeatServicesPerWeek.setPlotOptions(new PlotOptionsLineWithZIndex(list.get(0)));
		//downloadServicesPerWeek.setPlotOptions(new PlotOptionsLineWithZIndex(list.get(1)));
		Conf.addSeries(heartBeatServicesPerWeek);
		Conf.addSeries(downloadServicesPerWeek);
		configureColumnSeries(heartBeatServicesPerWeek);
		configureColumnSeries(downloadServicesPerWeek);
/*
		int daysInMonth = YearMonth.of(today.getYear(), today.getMonthValue()).lengthOfMonth();
		String[] categories = IntStream.rangeClosed(1, daysInMonth).mapToObj(Integer::toString)
				.toArray(size -> new String[size]);
		Conf.getxAxis().setCategories(categories);
		Conf.getxAxis().setLabels(new Labels(false));*/
	}

	protected void configureColumnSeries(DataSeries series) {
		PlotOptionsColumn options = new PlotOptionsColumn();
		options.setPointPadding(0.2);
		options.setBorderWidth(0);
		/*options.setPointWidth(100);
		Color color = SolidColor.GREEN;
		options.setBorderWidth(1);
		options.setGroupPadding(0);
		options.setColor(color);
		series.setPlotOptions(options);*/

		/*YAxis yaxis = series.getConfiguration().getyAxis();
		yaxis.setGridLineWidth(0);
		yaxis.setLabels(new Labels(false));
		yaxis.setTitle("Range");
		
		XAxis xaxis = series.getConfiguration().getxAxis();
		xaxis.setGridLineWidth(0);
		xaxis.setTitle("HeartBeat");*/
	}
}

