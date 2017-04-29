package sfproj.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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
		try {
			cnh = new ClientNetHandler(serverIPA, port);
			//cnh.sendToServer("RequestFullTimes");
			cnh.sendToServer("deptBreif");
			cnh.sendToServer("Emp");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			DecimalFormat payF = new DecimalFormat("0.00");
			DecimalFormat hourF = new DecimalFormat("0.0");
			BufferedWriter writer = null;
			String line, lineAgain, lineAgainAgain;
			writer = writer = new BufferedWriter(new FileWriter(new File("src/sfproj/client/dataSet/deptFull.txt")));
			double hours = 0.00, hoursTotal = 0.00, totalPay = 0.00, deptTotalHours = 0.00, deptTotalPay = 0.00;
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/departmentList.txt")));
			while((line = reader.readLine()) != null){
				String[] deptLines = ((String) line).split("\\|");
				writer.write("Department|" + deptLines[1]);
				writer.newLine();
				writer.flush();
				BufferedReader readerAgain = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/employeeList.txt")));
				while((lineAgain = readerAgain.readLine()) != null){
					String[] empLines = ((String) lineAgain).split("\\|");
					if(empLines[2].equals(deptLines[0])){
						BufferedReader readerAgainAgain = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/fullTimeList.txt")));
						while((lineAgainAgain = readerAgainAgain.readLine()) != null){
							String[] empTimes = ((String) lineAgainAgain).split("\\|");
							if(empTimes[0].equals(empLines[0])){
								hours = hours + Double.parseDouble(empTimes[4]);
								totalPay = totalPay + Double.parseDouble(empLines[3]);
							}
						}
						writer.write("Employee|" + empLines[1] + "|" + hourF.format(hours) + "|" + payF.format(Double.parseDouble(empLines[3])));
						hoursTotal = hoursTotal + hours;
						writer.newLine();
						writer.flush();
						hours = 0.00;
					}
				}
				writer.write("DeptEnd|" + hourF.format(hoursTotal) + "|" + payF.format(totalPay));
				deptTotalHours = deptTotalHours + hoursTotal;
				deptTotalPay = deptTotalPay + totalPay;
				writer.newLine();
				writer.flush();
				hoursTotal = 0.00;
				totalPay = 0.00;
			}
			writer.write("End|"+ hourF.format(deptTotalHours) + "|" + payF.format(deptTotalPay));
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String FILE = "C:/temp/Dept-FullReport.pdf";
			Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		    Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
		    Font largeBold = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
		    Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            document.addTitle("Dept-Brief Report");
            PdfPTable table = new PdfPTable(4);
            table.getDefaultCell().setBorder(0);
            
            BufferedReader getFull = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/deptFull.txt")));
            String fullLine;
            PdfPCell c1 = new PdfPCell(new Phrase("Department", smallBold));
            c1 = new PdfPCell(new Phrase("Dept Name", largeBold));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(0);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Emp Name", largeBold));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(0);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Hours", largeBold));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(0);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Pay", largeBold));
            c1.setBorder(0);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.setHeaderRows(1);
            while((fullLine = getFull.readLine()) != null){
				String[] deptFullLine = ((String) fullLine).split("\\|");
				if(deptFullLine[0].equals("Department")){
					c1 = new PdfPCell(new Phrase(deptFullLine[1], smallBold));
		            c1.setBorder(0);
					table.addCell(c1);
					table.addCell(" ");
					table.addCell(" ");
					table.addCell(" ");
				}
				else if(deptFullLine[0].equals("Employee")){
					table.addCell(" ");
					table.addCell(deptFullLine[1]);
					table.addCell(deptFullLine[2]);
					table.addCell(deptFullLine[3]);
				}
				else if(deptFullLine[0].equals("DeptEnd")){
					table.addCell(" ");
					table.addCell(" ");
					c1 = new PdfPCell(new Phrase(deptFullLine[1], smallBold));
		            c1.setBorder(0);
					table.addCell(c1);
					c1 = new PdfPCell(new Phrase(deptFullLine[2], smallBold));
		            c1.setBorder(0);
					table.addCell(c1);
				}
				else if(deptFullLine[0].equals("End")){
					table.addCell(" ");
					table.addCell(" ");
					table.addCell(deptFullLine[1]);
					table.addCell(deptFullLine[2]);
				}
            }

            
            Anchor anchor = new Anchor("Dept-Full Report", catFont);
            anchor.setName("Dept-Brief Full");
            table.setSpacingBefore(10);  
            document.add(table);
            
            document.close();
			
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
	
	public void deptBreif(){
		try {
			cnh = new ClientNetHandler(serverIPA, port);
			//cnh.sendToServer("RequestFullTimes");
			cnh.sendToServer("deptBreif");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//PDF START
			String FILE = "C:/temp/Dept-BriefReport.pdf";
			Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		    Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
		    Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            document.addTitle("Dept-Brief Report");
            PdfPTable table = new PdfPTable(3);
            table.getDefaultCell().setBorder(0);

            PdfPCell c1 = new PdfPCell(new Phrase("Department", smallBold));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(0);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Hours", smallBold));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(0);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Pay", smallBold));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(0);
            table.addCell(c1);
            table.setHeaderRows(1);
            
            BufferedReader reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/deptBrief.txt")));
            String line;
            while((line = reader.readLine()) != null){
				String[] deptLines = ((String) line).split("\\|");
				if(deptLines[0].equals("End")){
					table.addCell(" ");
					c1 = new PdfPCell(new Phrase(deptLines[1], smallBold));
		            c1.setBorder(0);
					table.addCell(c1);
					c1 = new PdfPCell(new Phrase(deptLines[2], smallBold));
		            c1.setBorder(0);
					table.addCell(c1);
				}
				else{
					table.addCell(deptLines[0]);
					table.addCell(deptLines[1]);
					table.addCell(deptLines[2]);
				}
            }

            
            Anchor anchor = new Anchor("Dept-Brief Report", catFont);
            anchor.setName("Dept-Brief Report");
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
		            c1.setBorder(0);
					table.addCell(c1);
					c1 = new PdfPCell(new Phrase(payLines[2], smallBold));
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
