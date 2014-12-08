package be.kuleuven.cs.oss.control;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.resourceproperties.ConstantResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.ScaledResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.SonarResourceProperty;
import be.kuleuven.cs.oss.sonarfacade.Metric;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public enum Parameter {
	
	

	XMETRIC, YMETRIC {
		@SuppressWarnings("unused")
		public ResourceProperty getProperty(String metricString) {
			SonarFacade sf = getSonarFacade();
			Metric metric = sf.findMetric(metricString);
			SonarResourceProperty rp = new SonarResourceProperty(sf, metric);
			
			return rp;
		}
	},
	PARENT {
		@SuppressWarnings("unused")
		public Resource getResource(String parent) {
			SonarFacade sf = getSonarFacade();
			Resource resource = sf.findResource(parent);
			
			return resource;
		}
	},
	CLASSES {
		@SuppressWarnings("unused")
		public List<Resource> getResources(Resource parent) {
			SonarFacade sf = getSonarFacade();
			List<Resource> resources = sf.findClasses(parent);
			
			return resources;
		}
	},
	PACKAGES {
		@SuppressWarnings("unused")
		public List<Resource> getResources(Resource parent) {
			SonarFacade sf = getSonarFacade();
			List<Resource> resources = sf.findPackages(parent);
			
			return resources;
		}
	},
	SIZE {
		@SuppressWarnings("unused")
		public Size getSize(int x, int y) {
			Size size = new Size(x, y);
			
			return size;
		}
	},
	BOXCOLOR {
		@SuppressWarnings("unused")
		public List<ResourceProperty> getProperties(int r, int g, int b) {
			List<ResourceProperty> properties = new ArrayList<ResourceProperty>();
			
			properties.add(new ConstantResourceProperty(r));
			properties.add(new ConstantResourceProperty(g));
			properties.add(new ConstantResourceProperty(b));
			return properties;
		}
		
		@SuppressWarnings("unused")
		public List<ResourceProperty> getProperties(float grey1, float grey2, String metricString) {
			SonarFacade sf = getSonarFacade();
			
			List<ResourceProperty> properties = new ArrayList<ResourceProperty>();
			
			Metric metric = sf.findMetric(metricString);
			ScaledResourceProperty propertie = new ScaledResourceProperty(grey1, grey2, 255, 0, sf, metric);
			
			properties.add(propertie);
			properties.add(propertie);
			properties.add(propertie);
			return properties;
		}
	},
	DIMENSION {
		@SuppressWarnings("unused")
		public ResourceProperty getProperties(int dimension) {
			ConstantResourceProperty propertie = new ConstantResourceProperty(dimension);
			return propertie;
		}
		
		@SuppressWarnings("unused")
		public ResourceProperty getProperties(String metricString) {
			SonarFacade sf = getSonarFacade();
			
			Metric metric = sf.findMetric(metricString);
			
			SonarResourceProperty propertie = new SonarResourceProperty(sf, metric);
			return propertie;
		}
	}
	;
	



	private SonarFacade sf;

	public Parameter forString(String input, SonarFacade sf) {
		this.sf = sf;
		return null;
	}
	
	public SonarFacade getSonarFacade() {
		return sf;
	}
	
	
	
	

}
