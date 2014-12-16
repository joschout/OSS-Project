package be.kuleuven.cs.oss.sonarfacade;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.sonar.api.charts.ChartParameters;
import org.sonar.wsclient.Sonar;

import be.kuleuven.cs.oss.polymorphicviews.plugin.PolymorphicViewsChart;

public class Main {

	//"ck=polymorphic&resources=classes&boxheight=complexity&boxwidth=10&parent=java:chess&type=scatter&xmetric=lines&ymetric=comment_lines&boxcolor=min20.3max1000.28keylines";
	private static String queryString = "ck=polymorphic&resources=classes&parent=java:chess&type=scatter";

	public static void main(String[] args) throws IOException {
		WebServiceSonarFacade wssf = new WebServiceSonarFacade();
		PolymorphicViewsChart pvc = new PolymorphicViewsChart(wssf);

		BufferedImage image = pvc.generateImage(new ChartParameters(queryString));

		output(image);

	}

	public static void output(BufferedImage bi) throws IOException{
		File outputfile = new File("saved.png");
		ImageIO.write(bi, "png", outputfile);
	}

}
