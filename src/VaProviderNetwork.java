import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.SAXException;

public class VaProviderNetwork
{
	ArrayList<Provider> ListOfProvider;

	public VaProviderNetwork(String FilePath) throws IOException, SAXException, OpenXML4JException
	{
		ListOfProvider = new ArrayList<>();

		{
			File xlsxFile = new File(FilePath);
			OPCPackage pkg = OPCPackage.open(xlsxFile, PackageAccess.READ);
			XlsxParser parser = new XlsxParser(pkg, new VaProviderNetworkHandler());
			parser.process();
			pkg.close();
		}
	}

	public class Provider
	{
		String ZipCode;
		boolean Audio;
		boolean Dental;
		boolean Eye;
		boolean GYN;
		boolean MentalHealth;
		boolean Sleep;
		boolean TBI;
		boolean Other;

		public Provider(){}
		
		public String toString()
		{
			return ZipCode + 
					"\tAudio: " + Audio + 
					"\tDental: " + Dental+ 
					"\tEye: " + Eye + 
					"\tGYN: " + GYN + 
					"\tMentalHealth: " + MentalHealth + 
					"\tSleep: " + Sleep + 
					"\tTBI: " + TBI + 
					"\tOther: " + Other;
		}

	}

	public class VaProviderNetworkHandler implements SheetContentsHandler
	{
		Provider TempProvider;

		int CurrentColNum;
		boolean isFirstRow = true;

		@Override
		public void startRow(int RowNum)
		{
			// * [DEBUG] */System.out.println("RowNum: " + RowNum);
			TempProvider = null;
			CurrentColNum = 0;
		}

		@Override
		public void endRow(int RowNum)
		{
			if(TempProvider != null)
			{
				ListOfProvider.add(TempProvider);
			}
			if(isFirstRow)
			{
				isFirstRow = false;
			}
			// * [DEBUG] */System.out.println("\n");
		}

		@Override
		public void cell(String CellReference, String FormattedValue, XSSFComment Comment)
		{
			if(!isFirstRow)
			{
				if(TempProvider == null)
				{
					TempProvider = new Provider();
				}

				switch(CurrentColNum)
				{
					case 0:
					{
						TempProvider.ZipCode = FormattedValue;
					}
					break;

					case 1:
					{
						TempProvider.Audio = isServiceSupported(FormattedValue);
					}
					break;
					case 2:
					{
						TempProvider.Dental = isServiceSupported(FormattedValue);
					}
					break;
					case 3:
					{
						TempProvider.Eye = isServiceSupported(FormattedValue);
					}
					break;

					case 4:
					{
						TempProvider.GYN = isServiceSupported(FormattedValue);
					}
					break;
					case 5:
					{
						TempProvider.MentalHealth = isServiceSupported(FormattedValue);
					}
					break;
					case 6:
					{
						TempProvider.Sleep = isServiceSupported(FormattedValue);
					}
					break;
					case 7:
					{
						TempProvider.TBI = isServiceSupported(FormattedValue);
					}
					break;
					case 8:
					{
						TempProvider.Other = isServiceSupported(FormattedValue);
					}
					break;

				}

			}

			// * [DEBUG] */System.out.print(FormattedValue + "\t");

			CurrentColNum++;
		}

		@Override
		public void headerFooter(String text, boolean isHeader, String tagName)
		{
		}

		public boolean isServiceSupported(String value)
		{
			boolean result = (value.toUpperCase().contains("YES")) ? true : false;
			return result;
		}

	}
}
