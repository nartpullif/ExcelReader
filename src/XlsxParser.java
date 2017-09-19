import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class XlsxParser
{
	OPCPackage xlsxPackage;
	SheetContentsHandler SheetHandler;

	public XlsxParser(OPCPackage pkg, SheetContentsHandler SheetHandler)
	{
		this.xlsxPackage = pkg;
		this.SheetHandler = SheetHandler;
	}

	public void process() throws IOException, SAXException, OpenXML4JException
	{
		ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
		XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
		StylesTable styles = xssfReader.getStylesTable();
		XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
		while(iter.hasNext())
		{
			InputStream stream = iter.next();
			processSheet(styles, strings, SheetHandler, stream);
			stream.close();
		}
	}

	public void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings,
			SheetContentsHandler sheetHandler, InputStream sheetInputStream) throws IOException, SAXException
	{
		DataFormatter formatter = new DataFormatter();
		InputSource sheetSource = new InputSource(sheetInputStream);
		try
		{
			XMLReader sheetParser = SAXHelper.newXMLReader();
			ContentHandler handler = new XSSFSheetXMLHandler(styles, null, strings, sheetHandler, formatter, false);
			sheetParser.setContentHandler(handler);
			sheetParser.parse(sheetSource);
		}
		catch (ParserConfigurationException e)
		{
			throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
		}
	}

}
