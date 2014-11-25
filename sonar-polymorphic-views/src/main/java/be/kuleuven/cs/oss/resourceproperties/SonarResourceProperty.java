package be.kuleuven.cs.oss.resourceproperties;

import be.kuleuven.cs.oss.sonarfacade.Metric;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class SonarResourceProperty implements ResourceProperty {
	
	private SonarFacade f;
	private Metric m;
	
	public SonarResourceProperty(SonarFacade f, Metric m) {
		this.f = f;
		this.m = m;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double getValue(Resource r) {
		return f.findMeasure(r, m).getValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPropertyName() {
		return m.getName();
	}

}
