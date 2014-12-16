package be.kuleuven.cs.oss.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

/**
 * A class that is responsible for the conversion of the raw chart parameters to the internal data representation and the subsequent return of the resulting chart
 * 
 * @author jeroenreinenbergh
 *
 */
public class Controller {

	private final static Logger LOG = LoggerFactory.getLogger(Controller.class);

	private final ChartParameters params;
	private final SonarFacade sf;
	private Chart chart;
	
	/**
	 * Creates a new Controller with the given chartparameters and sonarfacade
	 * @param p the given chartparameters
	 * @param sf an instance of SonarFacade
	 */
	public Controller(ChartParameters p, SonarFacade sf) {
		this.params = p;
		this.sf = sf;
	}
	
	/**
	 * Create a new chart based on the available chartparameters
	 * @return a new chart (currently, only scatterplot and system complexity view are supported)
	 */
	public Chart createChart() {
		this.chart = getChartType(params);
		
		chart.setSonarFacade(sf);
		startProcess();
		
		return chart;
	}
	
	/**
	 * Starts the process for setting the parameters in the chart
	 */
	private void startProcess() {
		Processor<Chart> processor = new Processor<Chart>();
		
		processor.addHandler(new ResourcesHandler(sf));
		processor.addHandler(new ResourceVisualizationCreatorHandler(sf));
		processor.addHandler(new AxisMetricHandler(sf));
		processor.addHandler(new SizeHandler(sf));
		processor.addHandler(new LineFactoryHandler());
		
		processor.startProcess(chart, params);
	}
	
	/**
	 * Retrieves an empty chart of the type that is defined in the given chartparameters
	 * @param params the given chartparameters
	 * @return an empty chart of the type that was specified in the given chartparameters
	 */
	private Chart getChartType(ChartParameters params) {
		ChartParameter chartHandler = new ChartParameter(params);
		Chart chart = chartHandler.getChart();
		
		return chart;
	}
	
	
	
}
