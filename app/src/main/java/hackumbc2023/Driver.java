package hackumbc2023;

public class Driver {
	String number;
	String name;
	boolean is21;
    DriverLevel level;
	
    public Driver(String name, String number, boolean is21, DriverLevel level) {
    	this.name = name;
    	this.number = number;
        this.level = level;
    }

    public String getNumber(){
        return number;
    }

    public void setNumber(String newNumber){
        this.number = newNumber;
    }

    public DriverLevel getDriverLevel() {
        return level;
    }

    public boolean getIs21() {
        return is21;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

}
