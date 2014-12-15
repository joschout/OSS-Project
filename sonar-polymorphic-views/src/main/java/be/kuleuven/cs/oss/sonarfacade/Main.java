package be.kuleuven.cs.oss.sonarfacade;

import org.sonar.api.charts.ChartParameters;
import org.sonar.wsclient.Sonar;

import be.kuleuven.cs.oss.polymorphicviews.plugin.PolymorphicViewsChart;

public class Main {
	
	private static String queryString = "ck=polymorphic&resources=classes&parent="+ u(@project.key) +"&type=scatter&xmetric=lines&ymetric=comment_lines";

	public static void main(String[] args) {
		WebServiceSonarFacade wssf = new WebServiceSonarFacade();
		PolymorphicViewsChart pvc = new PolymorphicViewsChart(wssf);
		
		pvc.generateImage(new ChartParameters(queryString));

	}

}
