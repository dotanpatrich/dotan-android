package foodAdditives.app;

public class Additive {
	
	public Additive(String eNumber, String name, Safety safety, String category, String comment)
	{
		this.eNumber = eNumber;
		this.safety = safety;
		this.comment = comment;
		this.name = name;
		this.category = category;
	}
	
	public String eNumber;
	public Safety safety; // Safe/Forbidden/Suspicious
	public String category;
	public String name;
	public String comment;
	
	@Override
	public String toString() {
		return eNumber;
	}
	
	
}
