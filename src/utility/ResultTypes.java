package utility;

public enum ResultTypes {

	ITD ("ITD Histogram"),
	LTD ("LTD Histogram"),
	TEST ("test");
	
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
