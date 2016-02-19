package objects;

import java.util.ArrayList;

public class Patient {
	
	// Patient attributes:
	
	private String[] name = new String[3]; // Patient name [first, middle, last]
	private String age; // patient age
	private String ID; // patient ID number
	private String condition; // patient medical condition
	Measurements data = new Measurements(); // data (to be) gathered for the patient
	
	//constants:
	
	public final static int INFO_AMOUNT = 6; // data field amount other than measurements for a patient
	public final static String FIRST_NAME_IDENTIFIER = "<firstName>";
	public final static String MIDDLE_NAME_IDENTIFIER = "<middleName>";
	public final static String LAST_NAME_IDENTIFIER = "<lastName>";
	public final static String AGE_IDENTIFIER = "<age>";
	public final static String ID_IDENTIFIER = "<ID>";
	public final static String CONDITON_IDENTIFIER = "<condition>";
	
	//public final static String iDATA_IDENTIFIER = "<dataI>";
	//public final static String lDATA_IDENTIFIER = "<datal>";
	
	private final static String NOT_KNOWN = "Unspecified";
	
	public Patient(String[] args){
				
		for (String s:args){
			
			if (s.startsWith(FIRST_NAME_IDENTIFIER))
				this.name[0] = s; // strings are immutable, no worries
			
			else if (s.startsWith(MIDDLE_NAME_IDENTIFIER))
				this.name[1] = s;
			
			else if (s.startsWith(LAST_NAME_IDENTIFIER))
				this.name[2] = s;
			
			else if (s.startsWith(AGE_IDENTIFIER))
				this.age = s;
			
			else if (s.startsWith(ID_IDENTIFIER))
				this.ID = s;
			
			else if (s.startsWith(CONDITON_IDENTIFIER))
				this.condition = s;
		
			
		}
		
		this.data = new Measurements();
		
		
		
		// TEMPORARY!!! print out the info:
		System.out.println(this.toString());
	}
	
	
	// getters & setters:
	
	/**
	 * 
	 * @return array with first, middle and last name, Identifiers not included!!!
	 */
	public String[] getName() {
		String returnMe[] = new String[3];
		String identifier = null;
		
		if (name[0] == null &&
			name[1] == null &&
			name[2] == null){
			
			return (new String[] {NOT_KNOWN,"",""});
		}
		for (int i = 0; i < 3; i++){
			
			switch (i){
			
				case 0: identifier = FIRST_NAME_IDENTIFIER; 	break;
				case 1: identifier = MIDDLE_NAME_IDENTIFIER; 	break;
				case 2: identifier = LAST_NAME_IDENTIFIER; 		break;
			
			}
			
			// take care of null option
			if (this.name[i] != null)				
				returnMe[i] = name[i].substring(identifier.length());
			else 
				returnMe[i] = "";
		}
		return returnMe;
	}
	
	/**
	 * name array should include identifiers 
	 * @param name
	 */
	public void setName(String[] name) {
		this.name = name;
	}
	
	public String getAge() {
		if (age == null)
			return NOT_KNOWN;
		return age.substring(AGE_IDENTIFIER.length());
	}
	
	public void setAge(String age) {
		this.age = (AGE_IDENTIFIER + age);
	}
	
	public String getID() {
		if (ID == null)
			return NOT_KNOWN;
		return ID.substring(ID_IDENTIFIER.length());
	}
	
	public void setID(String iD) {
		ID = (ID_IDENTIFIER + iD);
	}
	
	public String getCondition() {
		if (condition == null)
			return NOT_KNOWN;
		return condition.substring(CONDITON_IDENTIFIER.length());
	}
	
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public Measurements getData() {
		return data;
	}
	
	public void setData(ArrayList<String> data) {
		this.data.injectData(data);
		return;
		//either load data or take the test
	}
	
	//
	
	public String toString() {
		String [] name = this.getName();
		
		String nameString = "";
		for (String s:name){
			
			if (s != null  && s.length()>0)
				nameString = nameString.concat(s + " ");
			
			
		}
		
		return ("Name: " + nameString + "<br>" + 
				"Age: " + this.getAge() + "<br>" +
				"I.D.: " + this.getID() + "<br>" +
				"Condition: " + this.getCondition());

	};
	
	
	
	public void saveDataToFile (){
		
	}
	
	//
	
	
	
	
	
	

}
