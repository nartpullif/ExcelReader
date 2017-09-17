
import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.xml.sax.SAXException;

public class MainActivity
{
	public static void main(String[] args) throws IOException, OpenXML4JException, SAXException
	{
		// TODO Auto-generated method stub
		// File myFile = new File("D:\\Google_Drive\\Current Term Work\\CS
		// Senior Design\\QTC\\sample data\\Sample ERRA Data.xlsx");
		ERRA erra = null;
		try
		{
		erra = new ERRA(
					"D:\\My Document\\My stuff\\Google Drive\\Current Term Work\\CS Senior Design\\QTC\\sample data\\Sample ERRA Data.xlsx");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		{
			File xlsxFile = new File("D:\\My Document\\My stuff\\Google Drive\\Current Term Work\\CS Senior Design\\QTC\\sample data\\Sample referral data v2.xlsx");
			OPCPackage pkg = OPCPackage.open(xlsxFile, PackageAccess.READ);
			XlsxReferralParser parser = new XlsxReferralParser(pkg);
			parser.process();
			pkg.close();
		}

	}
}
