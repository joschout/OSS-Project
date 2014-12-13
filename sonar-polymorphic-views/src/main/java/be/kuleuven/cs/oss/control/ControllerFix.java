package be.kuleuven.cs.oss.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class ControllerFix {

	private final static Logger LOG = LoggerFactory.getLogger(ControllerFix.class);

	private final ChartParameters params;
	private final SonarFacade sf;
	private final Chart chart;
	
	public ControllerFix(ChartParameters p, SonarFacade sf) throws Exception{
		this.params = p;
		this.sf = sf;
		this.chart = getChartType(p);
		createProcessor();
	}
	
	private void createProcessor() {
		ParameterProcessor processor = new ParameterProcessor();
		//processor.addHandler(handler);
	}
	
	
	private Chart getChartType(ChartParameters params) {
		ChartParameter chartHandler = new ChartParameter(params);
		Chart chart = chartHandler.getChart();
		return chart;
	}
	
	
	
}
