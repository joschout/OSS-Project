package be.kuleuven.cs.oss.polymorphicviews.plugin;

import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.Chart;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.control.Controller;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

/**
 * Binding to the Sonar charts servlet
 * 
 * @author Pieter Agten <pieter.agten@cs.kuleuven.be>
 */
public class PolymorphicViewsChart implements Chart {
	private final static Logger LOG = LoggerFactory.getLogger(PolymorphicViewsChart.class);
	private final SonarFacade sonar;
	
	public PolymorphicViewsChart(SonarFacade sonar) {
		this.sonar = sonar;
	}
	
	@Override
	public String getKey() {
		return "polymorphic";
	}

	@Override
	public BufferedImage generateImage(ChartParameters params){
		LOG.info("PolymorphicViewsChart generateImage() called!");
		try{
			Controller c = new Controller(params,sonar);
			be.kuleuven.cs.oss.charts.Chart chart = c.createChart();
			//TODO parameters van draw aanpassen voor consistentie: aanmaken in controller of niet?
			return chart.draw();
		}
		catch(Exception e){
			return null;
		}
	}
	
	public SonarFacade getSonar() {
		return this.sonar;
	}
}
