package be.kuleuven.cs.oss.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.resourceproperties.ConstantResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.ScaledResourceProperty;
import be.kuleuven.cs.oss.resourcevisualizations.BoxFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.sonarfacade.Metric;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class ColorHandler implements ResourceVisualizationFactoryHandler {
	
	private final static Logger LOG = LoggerFactory.getLogger(ColorHandler.class);

	private ResourceVisualizationFactoryHandler next;

	private String key = "boxcolor";

	SonarFacade sf;

	public ColorHandler(SonarFacade sf) {
		this.sf = sf;
	}

	@Override
	public void setNext(ResourceVisualizationFactoryHandler handler) {
		this.next = handler;
	}

	
	/**
	 * Create new resource properties for the boxcolor and add it to the resource property manager
	 * @throws Exception if the creation of the resource properties fails
	 */
	@Override
	public void handleRequest(ResourceVisualizationFactory rvf, ChartParameters params) {
		String colorValue = retrieveValue(key, params);
		
		List<ResourceProperty> result = new ArrayList<ResourceProperty>();
		
		if(colorValue.matches("r[0-9]{1,3}g[0-9]{1,3}b[0-9]{1,3}")){
			List<String> rgb = parseRGB(colorValue);
			
			for(String rgbString: rgb) {
				int rgbValue = Integer.parseInt(rgbString);
				ConstantResourceProperty resourcePropery = new ConstantResourceProperty(rgbValue);
				result.add(resourcePropery);
			}
		}
		else if(colorValue.matches("min[0-9]+(\\.[0-9]+)*max[0-9]+(\\.[0-9]+)*key(.)+")) {
			List<String> gs = parseGrayScale(colorValue);
			
			String gsString1 = gs.get(0);
			float gsValue1 = Float.parseFloat(gsString1);
			String gsString2 = gs.get(1);
			float gsValue2 = Float.parseFloat(gsString2);
			
			String gsKey = gs.get(2);
			Metric gsMetric = sf.findMetric(gsKey);
			
			ResourceProperty rp = new ScaledResourceProperty(gsValue1, gsValue2, 255, 0, sf, gsMetric);
			
			for(int i=0;i<3;++i){
				result.add(rp);
			}
			
		}
		else throw new NoResultException("Color not recognized");
		
		((BoxFactory) rvf).setRedProperty(result.get(0));
		((BoxFactory) rvf).setGreenProperty(result.get(1));
		((BoxFactory) rvf).setBlueProperty(result.get(2));
		
		if(next != null) {
			next.handleRequest(rvf, params);
		}
		
	}
	
	/**
	 * Parse the given RGB color
	 * @param rgb Textual representation of the given RGB color
	 * @return a list containing the respective integers representing each RGB component
	 * @throws Exception if the given RGB color cannot be parsed
	 */
	private List<String> parseRGB(String rgb) {
		List<String> split = Arrays.asList(rgb.split("[rgb]"));
		
		return split;
	}

	/**
	 * Parse the floats contained in the given gray scale
	 * @param gs Textual representation of the given gray scale
	 * @return a list containing the respective floats representing each gray scale component
	 * @throws Exception if the given gray scale cannot be parsed
	 */
	private List<String> parseGrayScale(String gs) {
		List<String> split = Arrays.asList(gs.split("[(min)(max)(key)]"));
		
		return split;
	}
	
	/**
	 * Retrieve a parameter value for the given parameter key
	 * @param key The given parameter key
	 * @return the retrieved parameter value
	 */
	private String retrieveValue(String key, ChartParameters params) {
		String result = params.getValue(key);
		
		if(result.equals("")){
			LOG.info("retrieve value failed");
			throw new NoResultException("value not retrieved");
		}
		
		return result;
	}
	
	

}
