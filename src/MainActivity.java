
import java.io.IOException;
public class MainActivity
{
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		// File myFile = new File("D:\\Google_Drive\\Current Term Work\\CS
		// Senior Design\\QTC\\sample data\\Sample ERRA Data.xlsx");
		try
		{
			ERRA erra = new ERRA("D:\\My Document\\My stuff\\Google Drive\\Current Term Work\\CS Senior Design\\QTC\\sample data\\Sample ERRA Data.xlsx");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
