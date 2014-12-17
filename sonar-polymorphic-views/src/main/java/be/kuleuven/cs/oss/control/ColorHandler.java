package be.kuleuven.cs.oss.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.datautils.ParamValueRetriever;
import be.kuleuven.cs.oss.resourceproperties.ConstantResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.ScaledResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.SonarResourceProperty;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.sonarfacade.Metric;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

/**
 * A handler for the color in the resource visualization factory
 * 
 * @author jeroenreinenbergh
 *
 */
public class ColorHandler implements IHandler<ResourceVisualizationFactory> {

	private final static Logger LOG = LoggerFactory.getLogger(ColorHandler.class);

	private IHandler<ResourceVisualizationFactory> next;

	private String key;

	private static final ConstantResourceProperty DEFAULT_COLOR = new ConstantResourceProperty(255);

	SonarFacade sf;

	/**
	 * Creates a new ColorHandler with the given instance of SonarFacade and a color key
	 * @param sf a SonarFacade instance
	 * @param colorKey a String representing the color key
	 */
	public ColorHandler(SonarFacade sf, String colorKey) {
		this.sf = sf;
		this.key = colorKey;
	}

	@Override
	public void setNext(IHandler<ResourceVisualizationFactory> handler) {
		this.next = handler;
	}

	/**
	 * Creates the color properties and sets them in the given resource visualization factory. If the color key cannot be recognized, the properties are set to default (white).
	 * @throws IllegalArgumentException if the given color value is not valid
	 */
	@Override
	public void handleRequest(ResourceVisualizationFactory rvf, ParamValueRetriever params) throws IllegalArgumentException {
		List<ResourceProperty> result = new ArrayList<ResourceProperty>();
		try {
			String colorValue = params.retrieveValue(key);
			if(colorValue.matches("r[0-9]{1,3}g[0-9]{1,3}b[0-9]{1,3}")){
				List<String> rgb = retrieveValues(colorValue,"[rgb]");
				for(String rgbString: rgb) {
					int rgbValue = Integer.parseInt(rgbString);
					ConstantResourceProperty resourcePropery = new ConstantResourceProperty(rgbValue);
					result.add(resourcePropery);
				}
			}
			else if(colorValue.matches("min[0-9]+(\\.[0-9]+)*max[0-9]+(\\.[0-9]+)*key(.)+")) {
				List<String> gs = retrieveValues(colorValue,"(min|max|key)");
				String gsString1 = gs.get(0);
				float gsValue1 = Float.parseFloat(gsString1);
				String gsString2 = gs.get(1);
				float gsValue2 = Float.parseFloat(gsString2);

				String gsKey = gs.get(2);
				Metric gsMetric = sf.findMetric(gsKey);
				if(gsMetric == null){
					throw new NoResultException("Grayscale metric not found");
				}

				ResourceProperty rp = new ScaledResourceProperty(new SonarResourceProperty(sf, gsMetric), gsValue1, gsValue2, 255, 0);

				for(int i=0;i<3;++i){
					result.add(rp);
				}

			}

			else throw new IllegalArgumentException(key+" not valid");
		}
		catch(NoResultException e) {
			LOG.info("setting defaults for "+key);
			for(int i=0;i<3;++i){
				result.add(DEFAULT_COLOR);
			}
		}

		rvf.setRedProperty(result.get(0));
		rvf.setGreenProperty(result.get(1));
		rvf.setBlueProperty(result.get(2));
		
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
		split = split.subList(1, split.size());
		return split;
	}

}
