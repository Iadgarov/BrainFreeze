package objects;

import java.util.ArrayList;

/**
 * Represents a patient created int he program. 
 * Holds all relevant data to a patient, name, age... also the test resutls and ntoes should they exist. 
 * @author David
 *
 */
public class Patient {
	
	// Patient attributes:
	
	private String[] name = new String[3]; // Patient name [first, middle, last]
	private String age; // patient age
	private String ID; // patient ID number
	private String condition; // patient medical condition
	Measurements data = new Measurements(); // data (to be) gathered for the patient
	private String notes;
	
	//constants:
	
	public final static int INFO_AMOUNT = 6; // data field amount other than measurements for a patient (name, Id, ... ) 6 of those
	public final static String FIRST_NAME_IDENTIFIER = "<firstName>";
	public final static String MIDDLE_NAME_IDENTIFIER = "<middleName>";
	public final static String LAST_NAME_IDENTIFIER = "<lastName>";
	public final static String AGE_IDENTIFIER = "<age>";
	public final static String ID_IDENTIFIER = "<ID>";
	public final static String CONDITON_IDENTIFIER = "<condition>";
	
	public final static String iDATA_IDENTIFIER = "<dataI>";
	public final static String lDATA_IDENTIFIER = "<datal>";
	
	public final static String NOTES = "";
	
	private final static String NOT_KNOWN = "Unspecified";
	
	public Patient(String[] args){
				
		this.notes = "";
		this.updateInfo(args, false);
		
		this.data = new Measurements();
		
	}
	
	public void updateInfo(String []args, boolean update){
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
			
			else if (!update && !s.startsWith(iDATA_IDENTIFIER) && !s.startsWith(lDATA_IDENTIFIER)){
				this.notes += s + "\n";
			}
		
			
		}
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
	 * name array should include identifiers before each string. for instance <firstName> before the first name. 
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
	
	public boolean setData(ArrayList<String> data) {
		return this.data.injectData(data);
		
		//either load data or take the test
	}
	
	public void setData(Measurements m) {
		this.data = m;
	
	}
	
	public void eraseData(){
		this.data = new Measurements();
		//System.out.println("patient: " + this + " has had data reset");
	}
	
	public String getNotes(){
		if (notes == null)
			return NOT_KNOWN;
		return this.notes;
	}
	
	public void setNotes(String s){
		this.notes = NOTES + s;
	}
	
	//
	
	public String toString() {
		String [] name = this.getName();
		
		String nameString = "";
		for (String s:name){
			
			if (s != null  && s.length()>0)
				nameString = nameString.concat(s + " ");
			
			
		}
		
		return ("<pre>Name:\t" + nameString + "<br>" + 
				"Age:\t" + this.getAge() + "<br>" +
				"I.D:\t" + this.getID() + "<br>" +
				"Condition: " + this.getCondition() + "</pre>");

	};
	
	public String getNameString(){
		String nameString = "";
		for (String s:name){
			
			if (s != null  && s.length()>0)
				nameString = nameString.concat(s + " ");
			
			
		}
		return "<html>" + nameString + "</html>";
	}
	
	
	
	/**
	 * Transform Pateint object into a string to be saved in a file for later loading
	 * @return
	 */
	public String patientToFileFormat (){
		
		String returnMe = "";
		
		// Name:
		returnMe += this.name[0] + ",";
		returnMe += this.name[1] + ",";
		returnMe += this.name[2] + ",";
		
		// Age, ID, Condition
		returnMe += this.age + ",";
		returnMe += this.ID + ",";
		returnMe += this.condition + ",";
		
		
		
		// Data:
		returnMe += iDATA_IDENTIFIER;
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++){
				if (j != 8 || i!= 8)
					returnMe += data.getITD_Results()[i][j] + ";";
				else
					returnMe += data.getITD_Results()[i][j] + ",";
			}
		returnMe += lDATA_IDENTIFIER;
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++){
				if (j != 8 || i!= 8)
					returnMe += data.getLTD_Results()[i][j] + ";";
				else
					returnMe += data.getLTD_Results()[i][j];
			}
					
		
		//Notes
		returnMe += "," + this.notes;
		
		return returnMe;
	}
	
	//
	
	
	
	
	
	

}
