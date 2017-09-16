import java.util.Date;

public class ERRA 
{
	String VARO;
	String State;
	boolean HasBeenAssignedToUs;
	
	public ERRA(String VARO, String State, String Provider)
	{
		this.VARO = VARO;
		this.State = State;
		
		if(Provider.equals("QTC"))
		{
			HasBeenAssignedToUs = true;
		}
		else
		{
			HasBeenAssignedToUs = false;
		}
	}
}
