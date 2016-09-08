package utility;

/**
 * Enum class
 * Holds all result types and their names 
 * @author David
 *
 */
public enum ResultTypes {

	ITD ("ITD Histogram"),
	LTD ("ILD Histogram"),
	BOTH_HIST("Both Histograms"),
	PARAM_DETAIL ("Lateralization Index (Advanced)"),
	DIAGNOSIS ("Patient Diagnosis (Advanced)");
	
	private final String resultName;
	
	private ResultTypes(final String str){
		resultName = str;
	}
	
	
	public static String[] resultNames() {
	    ResultTypes[] states = values();
	    String[] names = new String[states.length];

	    for (int i = 0; i < states.length; i++) {
	        names[i] = states[i].resultName;
	    }

	    return names;
	}
	
	
	public boolean equals(String otherResult) {
        return (otherResult == null) ? false : resultName.equals(otherResult);
    }

    public String toString() {
       return this.resultName;
    }
}
