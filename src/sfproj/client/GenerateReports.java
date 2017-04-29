package sfproj.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GenerateReports {

	ClientNetHandler cnh;
	private final String serverIPA = "localhost";
	private final int port = 5000;
	
	@FXML Button deptFullBtn;
	@FXML Button deptBreifBtn;
	@FXML Button empBtn;
	@FXML Button exportBtn;
	@FXML Button timeCardBtn;
	
	
private Stage generateReports;
	
	public GenerateReports(Stage generateReports){
		this.generateReports = generateReports;
	}
	
	public void deptFull(){
		
	}
	
	public void deptBreif(){
		
	}
	
	public void emp(){
		try {
			cnh = new ClientNetHandler(serverIPA, port);
			//cnh.sendToServer("RequestFullTimes");
			cnh.sendToServer("Emp");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//PDF START
			String FILE = "C:/temp/EmpReport.pdf";
			Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		    Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
		    Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
		    Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
		    Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            document.addTitle("Emp Report");
            PdfPTable table = new PdfPTable(5);
            table.getDefaultCell().setBorder(0);

            PdfPCell c1 = new PdfPCell(new Phrase("Employee", smallBold));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(0);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Department", smallBold));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(0);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Production", smallBold));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(0);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Hours", smallBold));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(0);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Pay", smallBold));
            c1.setBorder(0);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.setHeaderRows(1);
            
            BufferedReader reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/emp.txt")));
            String line;
            while((line = reader.readLine()) != null){
				String[] payLines = ((String) line).split("\\|");
				if(payLines[0].equals("End")){
					table.addCell(" ");
					table.addCell(" ");
					table.addCell(" ");
					c1 = new PdfPCell(new Phrase(payLines[1], smallBold));
		            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		            c1.setBorder(0);
					table.addCell(c1);
					c1 = new PdfPCell(new Phrase(payLines[2], smallBold));
		            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		            c1.setBorder(0);
					table.addCell(c1);
				}
				else{
					table.addCell(payLines[0]);
					table.addCell(payLines[1]);
					table.addCell(payLines[2]);
					table.addCell(payLines[3]);
					table.addCell(payLines[4]);
				}
            }

            
            Anchor anchor = new Anchor("Emp Report", catFont);
            anchor.setName("Emp Report");
            table.setSpacingBefore(10);  
            document.add(table);
            
            document.close();
			//PDF END
			
		} catch (UnknownHostException e) {
			// TODO
			e.printStackTrace();
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void export(){
		
	}
	
	public void timeCard(){
		
	}
}
