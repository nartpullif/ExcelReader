
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

public class MainActivity
{
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		ERRA erra = null;
		Referral referral = null;
		VaProviderNetwork vpNetwork = null;
		try
		{
			erra = new ERRA("D:\\My Document\\My stuff\\Google Drive\\Current Term Work\\CS Senior Design\\QTC\\sample data\\Sample ERRA Data.xlsx");
			referral = new Referral("D:\\My Document\\My stuff\\Google Drive\\Current Term Work\\CS Senior Design\\QTC\\sample data\\Sample referral data v2.xlsx");
			vpNetwork = new VaProviderNetwork ("D:\\My Document\\My stuff\\Google Drive\\Current Term Work\\CS Senior Design\\QTC\\sample data\\VA Provider Network List_non VetFed_083117.xlsx");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SAXException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (OpenXML4JException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("DONE");	
	}
}
