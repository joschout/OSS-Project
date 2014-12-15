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

public class ColorHandler implements IHandler<ResourceVisualizationFactory> {

	private final static Logger LOG = LoggerFactory.getLogger(ColorHandler.class);

	private IHandler<ResourceVisualizationFactory> next;

	private String key = "boxcolor";
	
	private static final ConstantResourceProperty DEFAULT_COLOR = new ConstantResourceProperty(255);

	SonarFacade sf;

	public ColorHandler(SonarFacade sf) {
		this.sf = sf;
	}

	@Override
	public void setNext(IHandler<ResourceVisualizationFactory> handler) {
		this.next = handler;
	}


	/**
	 * Create new resource properties for the boxcolor and add it to the resource property manager
	 * @throws Exception if the creation of the resource properties fails
	 */
	@Override
	public void handleRequest(ResourceVisualizationFactory rvf, ChartParameters params) {
		List<ResourceProperty> result = new ArrayList<ResourceProperty>();
		try {
			String colorValue = retrieveValue(key, params);

			if(colorValue.matches("r[0-9]{1,3}g[0-9]{1,3}b[0-9]{1,3}")){
				List<String> rgb = retrieveValues(colorValue,"[rgb]");

				for(String rgbString: rgb) {
					int rgbValue = Integer.parseInt(rgbString);
					ConstantResourceProperty resourcePropery = new ConstantResourceProperty(rgbValue);
					result.add(resourcePropery);
				}
			}
			else if(colorValue.matches("min[0-9]+(\\.[0-9]+)*max[0-9]+(\\.[0-9]+)*key(.)+")) {
				List<String> gs = retrieveValues(colorValue,"[(min)(max)(key)]");

				String gsString1 = gs.get(0);
				float gsValue1 = Float.parseFloat(gsString1);
				String gsString2 = gs.get(1);
				float gsValue2 = Float.parseFloat(gsString2);

				String gsKey = gs.get(2);
				Metric gsMetric = sf.findMetric(gsKey);
				if(gsMetric == null){
					throw new NoResultException("Grayscale metric not found");
				}

				ResourceProperty rp = new ScaledResourceProperty(gsValue1, gsValue2, 255, 0, sf, gsMetric);

				for(int i=0;i<3;++i){
					result.add(rp);
				}

			}
			else throw new NoResultException("Color not recognized");
		}
		catch(NoResultException e) {
			LOG.info("retrieve value failed, setting defaults");
			for(int i=0;i<3;++i){
				result.add(DEFAULT_COLOR);
			}
		}

		((BoxFactory) rvf).setRedProperty(result.get(0));
		((BoxFactory) rvf).setGreenProperty(result.get(1));
		((BoxFactory) rvf).setBlueProperty(result.get(2));

		if(next != null) {
			next.handleRequest(rvf, params);
		}

	}

	/**
	 * Retrieve the color values based on the given color and regular expression
	 * @param color Textual representation of the given color
	 * @param regex	Regular expression representing the indices of the color values
	 * @return a list containing the textual representation of the respective color values that are contained within the given color
	 */
	private List<String> retrieveValues(String color, String regex) {
		List<String> split = Arrays.asList(color.split(regex));
		split.remove(0);
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
			throw new NoResultException("value not retrieved");
		}

		return result;
	}



}
