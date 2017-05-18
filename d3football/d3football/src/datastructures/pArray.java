package datastructures;

import runner.Runner;

public class pArray {
	double[] perFG;
	double[] perGFI;
	//Punt has no success rate	
	
	public pArray(double[] perFG,double[] perGFI){
		this.perFG = perFG;
		this.perGFI = perGFI;
	}
	
	public pArray(){
		double[] yd = new double[Runner.yardlineSize];
		double[] d = new double[Runner.distanceSize];
		
		for(int i = 0; i < yd.length;i++){
			yd[i] = 1;
		}
		
		for(int i = 0; i < d.length;i++){
			d[i] = 1;
		}
		
		perFG= yd;
		perGFI= d;
	}

	public double getPerFG(int yardline) {
		return perFG[yardline];
	}


	public void setPerFG(double[] perFG) {
		this.perFG = perFG;
	}


	public double getPerGFI(int distance) {
		return perGFI[distance];
	}


	public void setPerGFI(double[] perGFI) {
		this.perGFI = perGFI;
	}


	
}
