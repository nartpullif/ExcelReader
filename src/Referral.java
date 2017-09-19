import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.SAXException;

public class Referral
{
	ArrayList<Claimant> ListOfClaimant;
	
	public Referral(String FilePath) throws IOException, SAXException, OpenXML4JException
	{
		ListOfClaimant = new ArrayList<>();
		
		{
			File xlsxFile = new File(FilePath);
			OPCPackage pkg = OPCPackage.open(xlsxFile, PackageAccess.READ);
			XlsxParser parser = new XlsxParser(pkg, new ReferralHandler());
			parser.process();
			pkg.close();
		}
	}
	
	public class Claimant
	{
		String State;
		String ZipCode;
		String Date;
		String SpecialtyGroup;
		int NumOfReferral;
		
		public Claimant(){}
		
		public Claimant(String State, String ZipCode, String Date, String SpecialtyGroup, int NumOfReferral)
		{
			this.State = State;
			this.ZipCode = ZipCode;
			this.Date = Date;
			this.SpecialtyGroup = SpecialtyGroup;
			this.NumOfReferral = NumOfReferral;
		}
		
		public String toString()
		{
			return State + "\t" + ZipCode + "\t" + Date + "\t" + SpecialtyGroup + "\t" + NumOfReferral;
		}
	}
	
	public class ReferralHandler implements SheetContentsHandler
	{
		SimpleDateFormat ExcelFormat = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat CurrentFormat = new SimpleDateFormat("MM/dd/20yy");
		
		Claimant TempClaimant;
		
		int CurrentColNum;
		boolean isFirstRow = true;

		@Override
		public void startRow(int RowNum)
		{
			//*[DEBUG]*/System.out.println("RowNum: " + RowNum);
			TempClaimant = null;
			CurrentColNum = 0;
		}

		@Override
		public void endRow(int RowNum)
		{
			if(TempClaimant != null)
			{
				ListOfClaimant.add(TempClaimant);
			}
			if(isFirstRow)
			{
				isFirstRow = false;	
			}
			//*[DEBUG]*/System.out.println("\n");
		}

		@Override
		public void cell(String CellReference, String FormattedValue, XSSFComment Comment)
		{
			String value = FormattedValue;
			
			if(!isFirstRow)
			{
				if(TempClaimant == null)
				{
					TempClaimant = new Claimant();
				}
				
				switch(CurrentColNum)
				{
					case 0:
					{
						TempClaimant.State = value;
					}
					break;

					case 1:
					{
						TempClaimant.ZipCode = value;
					}
					break;
					case 2:
					{
						try
						{
							Date ExcelDate = ExcelFormat.parse(value);
							value = CurrentFormat.format(ExcelDate);
							TempClaimant.Date = value;
						}
						catch (ParseException e)
						{
							e.printStackTrace();
						}
					}
					break;
					case 3:
					{
						TempClaimant.SpecialtyGroup = value;
					}
					break;
					
					case 4:
					{
						TempClaimant.NumOfReferral = Integer.parseInt(value);
					}
					break;
				}
					
			}
			//*[DEBUG]*/System.out.print(value + "\t");
			CurrentColNum++;
		}

		@Override
		public void headerFooter(String text, boolean isHeader, String tagName){}
	}
}
