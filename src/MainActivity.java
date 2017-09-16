import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MainActivity {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File myFile = new File(
				"D:\\My Document\\My stuff\\Programming\\WorkStation\\java\\ExcelReader\\Sample ERRA Data.xlsx");
		FileInputStream fis = new FileInputStream(myFile);

		// Finds the workbook instance for XLSX file
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);

		// Return first sheet from the XLSX workbook
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);

		// Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = mySheet.iterator();

		// Traversing over each row of XLSX file
		ArrayList<Date_Time> date_time = new ArrayList<>();
		for(int i = 1; i < 2; i++)
		{
			Row row = mySheet.getRow(i);
			int SkipFirstTwo = 0;
			for(Cell cell: row)
			{
				if(SkipFirstTwo < 2)
				{
					SkipFirstTwo++;
					continue;
				}
				
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					if(cell.getStringCellValue().contains("AM") || cell.getStringCellValue().contains("PM"))
					{
						String dateWithNoSpaces = cell.getStringCellValue().replaceAll(" ", "");
						int TimeOfDayIndex = (dateWithNoSpaces.length() - 1) - 1;
						String date = dateWithNoSpaces.substring(0, TimeOfDayIndex);
						String TimeOfDay = dateWithNoSpaces.substring(TimeOfDayIndex, dateWithNoSpaces.length());
						date_time.add(new Date_Time(date, TimeOfDay));
					}
					else
					{
						System.out.println("MainActivity.java: error in switch case [Cell.CELL_TYPE_STRING]: not suppose to enter this branch. formatting issue");
						System.exit(1);
					}
					break;
				case Cell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(cell)) 
					{
						SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						date_time.add(new Date_Time(dateFormat.format(cell.getDateCellValue())));
					} 
					else 
					{
						System.out.println("MainActivity.java: error in switch case [Cell.CELL_TYPE_NUMERIC]: not suppose to enter this branch. formatting issue");
						System.exit(1);
					}
					break;
				default:
					date_time.add(new Date_Time(date_time.get(date_time.size() - 1).Date));
				}
			}
		}
		System.out.println("HELLO");
		/*
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			// For each row, iterate through each columns
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {

				Cell cell = cellIterator.next();

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					System.out.print(cell.getStringCellValue() + "\t");
					break;
				case Cell.CELL_TYPE_NUMERIC:
					String strCellValue = null;
					if (DateUtil.isCellDateFormatted(cell)) {
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"MM/dd/yyyy");
						strCellValue = dateFormat.format(cell.getDateCellValue());
					} else {
						Double value = cell.getNumericCellValue();
						Long longValue = value.longValue();
						strCellValue = new String(longValue.toString());
					}
					
					System.out.print(strCellValue + "\t");
					break;
					
				default:
					System.out.print(cell.getStringCellValue() + "\t");
				}
			}
			System.out.println("");
		}
		*/
	}
}
