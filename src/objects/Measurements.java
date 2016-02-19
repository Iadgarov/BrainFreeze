package objects;

import java.util.ArrayList;
import java.util.Arrays;

public class Measurements {
	
	private int valid;
	public Measurements(){
		this.valid = 0;
	}
	
	// Measurement results for clicks:
	// 9x9 matrix, left to right are the time/levels. Bottom to top are the positions 1-9.
	private double[][] ITD_Results = new double[9][9];
	private double[][] LTD_results = new double[9][9];
	
	public void injectData (ArrayList<String> data){
		
		if (data.size() != 2){
			System.out.println("[Measurements Class->injectData] invalid data input size: " + data.size());
			System.out.println("[Measurements Class->injectData] The data: " + data.toString());
			System.exit(-1);
		}
		
		ArrayList<String> dataPoints = new ArrayList<String>( 
				Arrays.asList(data.get(0).substring(7, data.get(0).length()).split("\\;")));
		
		double[][] iTD_Results = new double [9][9];
		double[][] lTD_Results = new double [9][9];
		
		//System.out.println(dataPoints.toString());
		//System.out.println("************");
		
		for (int i = 0; i < dataPoints.size(); i++){
			
			int temp = Integer.parseInt(dataPoints.get(i));
			iTD_Results[i / 9][i % 9] = temp;
			//System.out.print(iTD_Results[i/9][i%9]+"|");
			
		}
		
		dataPoints = new ArrayList<String>( 
				Arrays.asList(data.get(1).substring(7, data.get(1).length()).split("\\;")));
		
		for (int i = 0; i < dataPoints.size(); i++){
			
			int temp = Integer.parseInt(dataPoints.get(i));
			lTD_Results[i / 9][i % 9] = temp;
			
		}
		
		setITD_Results(iTD_Results);
		setLTD_Results(lTD_Results);
		dataIsValid(); // we set teh data, it is now valid
	}
	
	
	public double[][] getITD_Results() {
		return ITD_Results;
	}
	
	public void setITD_Results(double[][] iTD_Results) {
		ITD_Results = iTD_Results;
	}
	
	public double[][] getLTD_Results() {
		return LTD_results;
	}
	
	public void setLTD_Results(double[][] lTD_Results) {
		LTD_results = lTD_Results;
	}
	
	public void dataIsValid(){
		this.valid = 1;
	}
	public boolean isValid(){
		return (!(valid == 0));
	}
	
	

}
