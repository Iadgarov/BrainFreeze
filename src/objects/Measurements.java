package objects;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import utility.Settings;
import utility.UsefulMethods;

/**
 * Main data holding class. 
 * Every patient has its Measurements. Only one set at the time of writing this. 
 * @author David
 *
 */
public class Measurements {

	// some consts because I'm cool that way (this was before enums became cool)
	public static final int LEFT = 0;
	public static final int CENTER = 1;
	public static final int RIGHT = 2;
	public static final int TIME = 0;
	public static final int LEVEL = 3;

	// >>>>   NOTE:   <<<<
	// hardcoded limitations based on research papers
	// should be added to settings screen
	public static final double[] SLOPE_LIMITS = {0.84, 1};
	public static final double INTERCEPT_LIMIT = 0.7;


	private int valid;	// valid = 1 if data exists for this patient

	/** Measurement results for clicks:
	 * 9x9 matrix, left to right are the time/levels. Top to bottom are the positions 1-9.
	 * Every slot counts the number of times the patient chose it per each stimulant. 
	 * an ideal result would be diagonal with every item being the number of times each stimulant was presented. 
	 * 
	 * Each row of the matrix represents one position. Each slot in a row represents a difference (time or level)
	 * and every number represents the amount of times the spot was chosen for that difference. 
	 * Therefore, when calculating the regression line a transpose is needed!
	 * 
	 */
	public Measurements(){
		this.valid = 0;
		ITD_Results = new int[9][9];
		ILD_results = new int[9][9];
		iDataPointCount = 0;
		lDataPointCount = 0;
		thetaArray = new DescriptiveStatistics[6]; 
		
	}

	private int[][] ITD_Results;
	private int[][] ILD_results;

	private int iDataPointCount;
	private int lDataPointCount;

	private SimpleRegression ITD_Line;
	private SimpleRegression LTD_Line;
	
	// At this point I can't even remember why these exist, but here they are just in case... 
//	private final static double[] X_ITD = {-900, -675, -450, -225, 0, 225, 450, 675, 900};
//	private final static double[] X_ILD = {-10, -7.5, -5, -2.5, 0, 2.5, 5, 7.5, 10};
//	private final static double[] Y = {1, 2, 3, 4, 5, 6, 7, 8, 9};
	
//	private LinearRegression ITD_Regression;
//	private LinearRegression ILD_Regression;

	// The theta values, first the time then the level in the order of left/center/right.
	//  = [ITD+left, ITD+center, ITD+right, ILD+left, ILD+center, ILD+right]
	private DescriptiveStatistics[] thetaArray; 

	/**
	 *  get the data as a bunch of strings, used to load saved data from a patient file. 
	 * @param data = ArrayList of strings, each one a number. We know where each goes based on the patient file format. 
	 * @return true is successful, false otherwise. 
	 */
	public boolean injectData (ArrayList<String> data){


		// some prints that helped way back when
		// Crudely done but wait till you see some of the other.. pff.. I am not proud. 
		if (data.size() != 2){
			System.out.println("[Measurements Class->injectData] invalid data input size: " + data.size());
			System.out.println("[Measurements Class->injectData] The data: " + data.toString());
			return false;
		}

		// taking the first batch of data points, ITD
		ArrayList<String> dataPoints = new ArrayList<String>( 
				Arrays.asList(data.get(0).substring(7, data.get(0).length()).split("\\;")));

		int[][] iTD_Results = new int [9][9];
		int[][] iLD_Results = new int [9][9];  

		for (int i = 0; i < dataPoints.size(); i++){

			int temp = Integer.parseInt(dataPoints.get(i));
			iTD_Results[i / 9][i % 9] = temp;
		}


		// second batch, ILD
		dataPoints = new ArrayList<String>( 
				Arrays.asList(data.get(1).substring(7, data.get(1).length()).split("\\;")));

		for (int i = 0; i < dataPoints.size(); i++){

			int temp = Integer.parseInt(dataPoints.get(i));
			iLD_Results[i / 9][i % 9] = temp;

		}

		// I made getters and setter, might as well use them... Don't look for logic  
		setITD_Results(iTD_Results);
		setLTD_Results(iLD_Results);

		//////////

		dataIsValid(); // we set the data, it is now valid (no longer null data)
		
		return true; // success
	}

	private void setUpNewData(){

		ITD_Line = new SimpleRegression(true);
		LTD_Line = new SimpleRegression(true);


		double w[][] = 
				UsefulMethods.int_To_Double_2DArray(ITD_Results);
	
		// more debug stuff for yours truly 
		for (double y[] : w){
			for (double x : y)
				System.out.print(x + " ");
			System.out.println();
		}
		
		// lots of points make a trend line
		addLineData(( UsefulMethods.int_To_Double_2DArray(ITD_Results)), ITD_Line);
		addLineData(( UsefulMethods.int_To_Double_2DArray(ILD_results)), LTD_Line);

		//////////

		// Theta data input:
		for (int i = 0; i < 3; i++){

			
			thetaArray[i] = new DescriptiveStatistics();
			thetaArray[i].addValue(StatUtils.sum(UsefulMethods.int_To_Double_Array(ITD_Results[i * 3 + 0]))/iDataPointCount);
			thetaArray[i].addValue(StatUtils.sum(UsefulMethods.int_To_Double_Array(ITD_Results[i * 3 + 1]))/iDataPointCount);
			thetaArray[i].addValue(StatUtils.sum(UsefulMethods.int_To_Double_Array(ITD_Results[i * 3 + 2]))/iDataPointCount);
			
			System.out.println(lDataPointCount);
			int d[][] = ILD_results;
			System.out.println(d[0] + " -- " + d[1] + " -- " + d[2]);

			thetaArray[i + 3] = new DescriptiveStatistics();		
			thetaArray[i + 3].addValue(StatUtils.sum(UsefulMethods.int_To_Double_Array(ILD_results[i * 3 + 0]))/lDataPointCount);
			thetaArray[i + 3].addValue(StatUtils.sum(UsefulMethods.int_To_Double_Array(ILD_results[i * 3 + 1]))/lDataPointCount);
			thetaArray[i + 3].addValue(StatUtils.sum(UsefulMethods.int_To_Double_Array(ILD_results[i * 3 + 2]))/lDataPointCount);

			
		}
		
		
	}


	/**
	 * Helps create trend line from data points
	 * @param data = the data points
	 * @param r = the simpleRegression object that makes sense of the points
	 */
	private void addLineData (double [][] data, SimpleRegression r){

		for(int x = 0; x < data.length; x++) {
			for(int y = 0; y < data[0].length; y++) {
				for(int i = 0; i < data[x][y]; i++) {
					r.addData(x, y);
				}
			}
		}

	}

	/**
	 * Checks if trend line slope is in range for either ITD or ILD data
	 * Checks done based on research papers
	 * @param type = ILD or ITD
	 * @return true if in range, false otherwise 
	 */
	public boolean isSlopeInRange(int type){

		if (type == TIME){
			double a = this.ITD_Line.getSlope();
			if (a < SLOPE_LIMITS[0] || a > SLOPE_LIMITS[1])
				return false;
		}
		else if (type == LEVEL){
			double a = this.LTD_Line.getSlope();
			if (a < SLOPE_LIMITS[0] || a > SLOPE_LIMITS[1])
				return false;
		}
		// debug nonsense, should NEVER happen
		else{
			System.out.println("[Measurments->isSlopeInRange] Illeal type!");
			System.exit(0);
		}

		return true;

	}

	/**
	 * Checks if trend line intercept with Y axis is in range for either ITD or ILD data
	 * Checks done based on research papers
	 * @param type = ILD or ITD
	 * @return true if in range, false otherwise 
	 */
	public boolean isInterceptInRange(int type){

		if (type == TIME){
			double b = this.ITD_Line.getIntercept();
			if (b > INTERCEPT_LIMIT)
				return false;
		}
		else if (type == LEVEL){
			double b = this.LTD_Line.getIntercept();
			if (b > INTERCEPT_LIMIT)
				return false;
		}
		// debug nonsense, should NEVER happen
		else{
			System.out.println("[Measurments->isSlopeInRange] Illeal type!");
			System.exit(0);
		}

		return true;
	}

	/**
	 * Checks if patient is center oriented
	 * Checks done based on research papers
	 * @param type = ILD or ITD
	 * @return true if in range, false otherwise 
	 */
	public boolean isCenterOriented(int type){

		boolean condition1 = false;
		boolean condition2 = false;

		// condition 1:
		DescriptiveStatistics theta;
		if (type == TIME){
			theta =  this.thetaArray[1]; // center ITD theta array (sum is the theta value itself)

			if (theta.getSum() > Settings.thetaMean_ITD[1]+ 2.5 * Settings.thetaSD_ITD[1]){
				condition1 = true;
			}
		}
		else{	// Level
			
			theta = this.thetaArray[4];
			if (theta.getSum() > Settings.thetaMean_ILD[1]+ 2.5 * Settings.thetaSD_ILD[1]){
				condition1 = true;
			}
			
		}

		//condition 2:
		if (type == TIME){

			theta = (this.thetaArray[0].getSum() > this.thetaArray[2].getSum()) ?
					this.thetaArray[2] : this.thetaArray[0];	// get min theta for ITD between left or right 
					
			if (theta.getSum() < Settings.thetaMean_ITD[0] - 2.5 * Settings.thetaSD_ITD[0]){
				condition2 = true;
			}
		}
		else if (type == LEVEL){
			theta = (this.thetaArray[3].getSum() > this.thetaArray[5].getSum()) ?
					this.thetaArray[5] : this.thetaArray[3];
					
			if (theta.getSum() < Settings.thetaMean_ILD[0] - 2.5 * Settings.thetaSD_ILD[0]){
				condition2 = true;
			}

		}


		return (condition1 && condition2);
			
	}


	/**
	 * Checks if patient is side oriented
	 * Checks done based on research papers
	 * @param type = ILD or ITD
	 * @return true if in range, false otherwise 
	 */
	public boolean isSideOriented(int type){

		boolean condition1 = false;
		boolean condition2 = false;

		// condition 1:
		DescriptiveStatistics theta;
		if (type == TIME){
			theta =  this.thetaArray[1];

			if (theta.getSum() < Settings.thetaMean_ITD[1] - 2.5 * Settings.thetaSD_ITD[1]){
				condition1 = true;
			}
		}
		else{
			
			theta = this.thetaArray[4];
			if (theta.getSum() < Settings.thetaMean_ILD[1] - 2.5 * Settings.thetaSD_ILD[1]){
				condition1 = true;
			}
			
		}

		//condition 2:
		if (type == TIME){

			theta = (this.thetaArray[0].getSum() < this.thetaArray[2].getSum()) ?
					this.thetaArray[2] : this.thetaArray[0];
					
			if (theta.getSum() > Settings.thetaMean_ITD[0] + 2.5 * Settings.thetaSD_ITD[0]){
				condition2 = true;
			}
		}
		else if (type == LEVEL){
			theta = (this.thetaArray[3].getSum() < this.thetaArray[5].getSum()) ?
					this.thetaArray[5] : this.thetaArray[3];
					
			if (theta.getSum() > Settings.thetaMean_ILD[0] + 2.5 * Settings.thetaSD_ILD[0]){
				condition2 = true;
			}

		}


		return (condition1 && condition2);
	}


	/**
	 * Checks if patient behaves like the control group did 
	 * Checks done based on research papers
	 * @param type = ILD or ITD
	 * @return true if in range, false otherwise 
	 */
	public boolean isNormal(int type){

		boolean condition1 = false;
		boolean condition2_left = false;
		boolean condition2_right = false;
		boolean condition3 = false;

		// condition 1:
		DescriptiveStatistics theta;
		if (type == TIME){
			theta =  this.thetaArray[1];

			if ((theta.getSum() >= Settings.thetaMean_ITD[1] - 2.5 * Settings.thetaSD_ITD[1])
					&& (theta.getSum() <= Settings.thetaMean_ITD[1] + 2.5 * Settings.thetaSD_ITD[1])){
				condition1 = true;
			}
		}
		else{
			
			theta = this.thetaArray[4];
			if ((theta.getSum() >= Settings.thetaMean_ILD[1] - 2.5 * Settings.thetaSD_ILD[1])
					&& (theta.getSum() <= Settings.thetaMean_ILD[1] + 2.5 * Settings.thetaSD_ILD[1])){
				condition1 = true;
			}
			
		}

		// condition 2 - left:
		if (type == TIME){
			theta =  this.thetaArray[0];

			if ((theta.getSum() >= Settings.thetaMean_ITD[0] - 2.5 * Settings.thetaSD_ITD[0])
					&& (theta.getSum() <= Settings.thetaMean_ITD[0] + 2.5 * Settings.thetaSD_ITD[0])){
				condition2_left = true;
			}
		}
		else{
			
			theta = this.thetaArray[3];
			if ((theta.getSum() >= Settings.thetaMean_ILD[0] - 2.5 * Settings.thetaSD_ILD[0])
					&& (theta.getSum() <= Settings.thetaMean_ILD[0] + 2.5 * Settings.thetaSD_ILD[0])){
				condition2_left = true;
			}
			
		}
		
		// condition 2 - right:
		if (type == TIME){
			theta =  this.thetaArray[2];

			if ((theta.getSum() >= Settings.thetaMean_ITD[0] - 2.5 * Settings.thetaSD_ITD[0])
					&& (theta.getSum() <= Settings.thetaMean_ITD[0] + 2.5 * Settings.thetaSD_ITD[0])){
				condition2_right = true;
			}
		}
		else{
			
			theta = this.thetaArray[5];
			if ((theta.getSum() >= Settings.thetaMean_ILD[0] - 2.5 * Settings.thetaSD_ILD[0])
					&& (theta.getSum() <= Settings.thetaMean_ILD[0] + 2.5 * Settings.thetaSD_ILD[0])){
				condition2_right = true;
			}
			
		}

		// condition 3:
		condition3 = isSlopeInRange(type);

		return (condition1 && condition2_left && condition2_right && condition3);

	}

	/////////
	// Getting/Setting & Misc.:
	/////////


	public int[][] getITD_Results() {
		return ITD_Results;
	}

	public void setITD_Results(int[][] iTD_Results) {
		ITD_Results = iTD_Results;
		for (int y[] : iTD_Results)
			for (int x: y)
				iDataPointCount += x;
	}

	public int[][] getLTD_Results() {
		return ILD_results;
	}

	public void setLTD_Results(int[][] lTD_Results) {
		ILD_results = lTD_Results;
		for (int y[] : lTD_Results)
			for (int x: y)
				lDataPointCount += x;
	}

	public void dataIsValid(){
		this.valid = 1;
		setUpNewData();
	}
	public boolean isValid(){
		return (!(valid == 0));
	}

	public int getPointCount_I(){
		return this.iDataPointCount;
	}

	public int getPointCount_L(){
		return this.lDataPointCount;
	}

	public SimpleRegression getLTD_Line(){
		return this.LTD_Line;
	}

	public SimpleRegression getITD_Line(){
		return this.ITD_Line;
	}

	public DescriptiveStatistics[] getThetaArray(){
		return this.thetaArray;
	}


}
