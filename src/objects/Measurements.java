package objects;

public class Measurements {
	
	private int valid = 0;
	
	// Measurement results for clicks:
	// 9x9 matrix, left to right are the time/levels. Bottom to top are the positions 1-9.
	private int[][] ITD_Results = new int[9][9];
	private int[][] LTD_results = new int[9][9];
	
	
	public int[][] getITD_Results() {
		return ITD_Results;
	}
	
	public void setITD_Results(int[][] iTD_Results) {
		ITD_Results = iTD_Results;
	}
	
	public int[][] getLTD_results() {
		return LTD_results;
	}
	
	public void setLTD_results(int[][] lTD_results) {
		LTD_results = lTD_results;
	}
	
	public void dataIsValid(){
		this.valid = 1;
	}
	public boolean isValid(){
		return (!(valid == 0));
	}
	
	

}
