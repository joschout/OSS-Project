package be.kuleuven.cs.oss.resourceproperties;

public class ScaledResourceProperty extends ResourcePropertyDecorator {
	
	private float minScale;
	private float maxScale;
	
	private int minValue;
	private int maxValue;
	
	public ScaledResourceProperty(ResourceProperty rpToBeDecorated, float minScale, float maxScale, int minValue, int maxValue) {
		super(rpToBeDecorated);
		this.minScale = minScale;
		this.maxScale = maxScale;
		
		this.minValue = minValue;
		this.maxValue = maxValue;

	}
	
	/**
	 * linearly interpolates the measure of the given metric of the resource between the given values and scale.
	 * 
	 * @param the Resource to get the value for
	 * @return The interpolated value
	 */
	@Override
	public Double getValue(Resource r) {
		Double measure = super.getValue(r);
		double value;
		if (measure >= maxScale) value = maxValue;
		else if(measure <= minScale) value = minValue;
		else {
			value = minValue + (maxValue - minValue)*(measure - minScale)/(maxScale - minScale);	
		}
		return value;		
	}
}
