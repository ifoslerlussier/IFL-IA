
public class yarnColor extends java.awt.Color{

	public yarnColor(int rgb) {
		super(rgb);
	}
	
	public double compareTo(yarnColor c) {
		int red1 = this.getRed();
		int green1 = this.getGreen();
		int blue1 = this.getBlue();
		int red2 = c.getRed();
		int green2 = c.getGreen();
		int blue2 = c.getBlue();
		
		double[] tXYZ = RGBtoXYZ(red1,green1,blue1);
		double[] cXYZ = RGBtoXYZ(red2,green2,blue2);
		
		double L = 116 * calcLab(tXYZ[1] / 100) - 16;
		double a = 500 * (calcLab(tXYZ[0] / 95.0489) - calcLab(tXYZ[1] / 100));
		double b = 200 * (calcLab(tXYZ[1] / 100) - calcLab(tXYZ[2] / 108.8840));

		double L2 = 116 * calcLab(cXYZ[1] / 100) - 16;
		double a2 = 500 * (calcLab(cXYZ[0] / 95.0489) - calcLab(cXYZ[1] / 100));
		double b2 = 200 * (calcLab(cXYZ[1] / 100) - calcLab(cXYZ[2] / 108.8840));

		
		double distance = Math.sqrt(Math.pow((L-L2), 2)+Math.pow((a-a2), 2)+Math.pow((b-b2), 2));
		
		return distance;
	}
	
	public double[] RGBtoXYZ(int r, int g, int b) {
		double rDecimal = r/255.0;
		double gDecimal = g/255.0;
		double bDecimal = b/255.0;
		
		double x = (0.4124 * rDecimal) + (0.3756 * gDecimal) + (0.1805 * bDecimal);
		double y = (0.2126 * rDecimal) + (0.7152 * gDecimal) + (0.0722 * bDecimal);
		double z = (0.0193 * rDecimal) + (0.1192 * gDecimal) + (0.9505 * bDecimal);
	
		double[] out = {x,y,z};
		return out;
		
	}
	
	public double calcLab(double t) {
		if(t>Math.pow((6/29.0), 3)) {
			return Math.cbrt(t);
		}else {
			return (t / (3 * Math.pow((6 / 29.0),2))) + (4.0/29.0);
		}
		
		
		//return 0.0;
	}
	
}
