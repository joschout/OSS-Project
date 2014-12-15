package be.kuleuven.cs.oss.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class Controller {

	private final static Logger LOG = LoggerFactory.getLogger(Controller.class);

	private final ChartParameters params;
	private final SonarFacade sf;
	private Chart chart;
	
	public Controller(ChartParameters p, SonarFacade sf) throws Exception{
		this.params = p;
		this.sf = sf;
	}
	
	/**
	 * Create a new chart based on the available chart parameters
	 * @return a new chart (currently, only scatterplot and system complexity view are supported)
	 * @throws Exception if the creation of the chart failed
	 */
	public Chart createChart() {
		this.chart = getChartType(params);
		
		chart.setSonarFacade(sf);
		startProcess();
		
		return chart;
	}
	
	private void startProcess() {
		Processor<Chart> processor = new Processor<Chart>();
		
		processor.addHandler(new ResourcesHandler(sf));
		processor.addHandler(new ResourceVisualizationFactoryHandler(sf));
		processor.addHandler(new AxisMetricHandler(sf));
		processor.addHandler(new SizeHandler(sf));
		processor.addHandler(new LineFactoryHandler());
		
		processor.startProcess(chart, params);
	}
	
	
	private Chart getChartType(ChartParameters params) {
		ChartParameter chartHandler = new ChartParameter(params);
		Chart chart = chartHandler.getChart();
		return chart;
	}
	
	
	
}
