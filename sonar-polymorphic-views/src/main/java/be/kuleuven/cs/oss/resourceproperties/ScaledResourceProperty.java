package be.kuleuven.cs.oss.resourceproperties;

import be.kuleuven.cs.oss.sonarfacade.Metric;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class ScaledResourceProperty implements ResourceProperty {
	
	private float minScale;
	private float maxScale;
	
	private int minValue;
	private int maxValue;
	
	private SonarFacade f;
	private Metric m;
	
	public ScaledResourceProperty(float minScale, float maxScale, int minValue, int maxValue, SonarFacade f, Metric m) {
		this.minScale = minScale;
		this.maxScale = maxScale;
		
		this.minValue = minValue;
		this.maxValue = maxValue;
		
		this.f = f;
		this.m = m;
	}
	
	/**
	 * linearly interpolates the measure of the given metric of the resource between the given values and scale.
	 * 
	 * @param the Resource to get the value for
	 * @return The interpolated value
	 */
	@Override
	public Double getValue(Resource r) {
		Double measure = f.findMeasure(r, m).getValue();
		double value;
		if (measure >= maxScale) value = maxValue;
		else if(measure <= minScale) value = minValue;
		else {
			value = minValue + (maxValue - minValue)*(measure - minScale)/(maxScale - minScale);	
		}
		return value;		
	}

	@Override
	public String getPropertyName() {
		return m.getName();
	}

}
