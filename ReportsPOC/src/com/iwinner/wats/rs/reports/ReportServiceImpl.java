package com.iwinner.wats.rs.reports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.json.JSONArray;
import org.json.JSONObject;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ReportServiceImpl {
	static List<NdmnsDTO> listOfUser = new ArrayList<NdmnsDTO>();
	static {
		try {
			String response = RestUtils.listOfUsers();
			JSONObject jsonObject = new JSONObject(response);
			System.out.println(jsonObject);
			JSONArray jsonArray = jsonObject.getJSONArray("ndmnsDTO");
			for (int i = 0; i < jsonArray.length(); i++) {
				NdmnsDTO ndmnDTO = new NdmnsDTO();
				ndmnDTO.setClientId(jsonArray.getJSONObject(i).getString(
						"clientId"));
				ndmnDTO.setEntireLine(jsonArray.getJSONObject(i).getString(
						"entireLine"));
				ndmnDTO.setMethodId(jsonArray.getJSONObject(i).getString(
						"methodId"));
				ndmnDTO.setReturnMessage(jsonArray.getJSONObject(i).getString(
						"returnMessage"));
				ndmnDTO.setSpotifyID(jsonArray.getJSONObject(i).getInt(
						"spotifyID"));
				ndmnDTO.setMsisdn(jsonArray.getJSONObject(i)
						.getString("msisdn"));
				listOfUser.add(ndmnDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void reportGenerationCSV(HttpServletResponse response) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			String fileName="D:/anji/spotifyUser.csv";
			File file = new File(fileName);
			file.createNewFile();
			int firstIndex=fileName.lastIndexOf("/");
			String fileNameForDownload=fileName.substring(firstIndex+1, fileName.length());
			response.setContentType("APPLICATION/OCTET-STREAM");   
			response.setHeader("Content-Disposition","attachment; filename=\"" + fileNameForDownload + "\"");   
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.newLine();
			for (int i = 0; i < listOfUser.size(); i++) {
				NdmnsDTO nDTO = (NdmnsDTO) listOfUser.get(i);
				bw.write(nDTO.getClientId() + "|" + nDTO.getEntireLine() + "|"
						+ nDTO.getMethodId() + " " + nDTO.getReturnMessage()
						+ " " + nDTO.getSpotifyID() + "|" + nDTO.getMsisdn());
				bw.newLine();
				bw.flush();
			}
			OutputStream out = response.getOutputStream();
	         FileInputStream in = new FileInputStream(file);
	         byte[] buffer = new byte[4096];
	         int length;
	         while ((length = in.read(buffer)) > 0){
	            out.write(buffer, 0, length);
	         }
	         in.close();
	         out.flush();
	     
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				fw.close();
				bw.close();

			} catch (Exception e) {

			}
		}
	}

	public static void reportGenerationPDF(HttpServletResponse response) {
		String filePath="D:/anji/";
		try{
		response.setContentType("application/pdf");  
		response.setHeader("Content-Disposition","attachment; filename=\"" + "spotifyUser" + ".pdf" + "\"");   
		Document document = new Document(PageSize.A4.rotate());
		PdfWriter.getInstance(document, response.getOutputStream());//new FileOutputStream(filePath+ "spotifyUser" + ".pdf"));
		document.open();
		Paragraph p = new Paragraph();
		Font f = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD,
				BaseColor.DARK_GRAY);
		p.setFont(f);
		p.setSpacingBefore(10.0f);
		p.add("Spotify Report\n");
		p.add("\n\n");
		p.setAlignment(1);
		document.add(p);
		PdfPTable table = new PdfPTable(4);
		float[] width = { 10.0f, 10.0f, 4.0f, 8.0f };
		table.setTotalWidth(width);
		int i = 1;
		table.setHeaderRows(1);
		document.setMargins(2.0f, 2.0f, 15.0f, 15.0f);
		table.addCell("MSISDN");
		table.addCell("SPOTIFYID");
		table.addCell("RETURNMESSAGE");
		table.addCell("METHODID");
		for (i = 1; i <= 4; i++) {
			for(NdmnsDTO emp:listOfUser){
				table.addCell(emp.getMsisdn());
				table.addCell(emp.getSpotifyID().toString());
				table.addCell(emp.getReturnMessage());
				table.addCell(emp.getMethodId());
			}
			document.add(table);
			document.addAuthor("Vengicx");
			document.close();
		}
		OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(filePath+ "spotifyUser" + ".pdf");
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0){
           out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
		}catch(Exception e){
			
		}
	}

	public static void reportGenerationEXcel(HttpServletResponse response)throws Exception {
		List<String> rowOfLogsInfo=Arrays.asList("MSISDN","SPOTIFYID","RETURNMESSAGE","METHODID");
		String fileName="D:/anji/spotifyExcelReport";
			HSSFWorkbook wb = new HSSFWorkbook();
			try {
				String reportName= fileName.substring(fileName.lastIndexOf("/")+1, fileName.length());
				response.setContentType("APPLICATION/OCTET-STREAM");   
				response.setHeader("Content-Disposition","attachment; filename=\"" + reportName+".xls" + "\"");   
				
				File file=new File(fileName+".xls");
				file.createNewFile();
				
				HSSFSheet sheet = wb.createSheet("spotifyUserReport");
				HSSFCellStyle headstyle = wb.createCellStyle();
				HSSFCellStyle rowsstyle = wb.createCellStyle();
				HSSFFont fhead = wb.createFont();
				HSSFFont frows = wb.createFont();
				fhead.setFontHeightInPoints((short) 10);
				fhead.setColor((short) HSSFColor.BROWN.index);
				fhead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				fhead.setFontName("Verdana");
				headstyle.setFont(fhead);
				headstyle.setFillBackgroundColor(HSSFColor.GREY_50_PERCENT.index);
				frows.setFontHeightInPoints((short) 8);
				frows.setFontName("Verdana");
				rowsstyle.setFont(frows);
				HSSFRow row = sheet.createRow(0);
				if (rowOfLogsInfo != null) {
					for (int i = 0; i < rowOfLogsInfo.size(); i++) {
						HSSFCell cell = row.createCell((short) i);
						cell.setCellStyle(headstyle);
						HSSFRichTextString str = new HSSFRichTextString(
								(String) rowOfLogsInfo.get(i));
						cell.setCellValue(str);
					}
				}

	
				if (listOfUser != null) {
					NdmnsDTO ndmnDTO = null;
					for (int i = 0; i < listOfUser.size(); i++) {
						row = sheet.createRow(i + 1);
						ndmnDTO = (NdmnsDTO) listOfUser.get(i);

						HSSFCell cell0 = row.createCell((short) 0);
						cell0.setCellStyle(rowsstyle);
						HSSFRichTextString str0 = new HSSFRichTextString(
								(String) ndmnDTO.getMsisdn());
						cell0.setCellValue(str0);

						HSSFCell cell1 = row.createCell((short) 1);
						cell1.setCellStyle(rowsstyle);
						HSSFRichTextString str1 = new HSSFRichTextString(
								(String) ndmnDTO.getSpotifyID().toString());
						cell1.setCellValue(str1);

						HSSFCell cell2 = row.createCell((short) 2);
						cell2.setCellStyle(rowsstyle);
						HSSFRichTextString str2 = new HSSFRichTextString(
								(String) ndmnDTO.getReturnMessage());
						cell2.setCellValue(str2);

						HSSFCell cell3 = row.createCell((short) 3);
						cell3.setCellStyle(rowsstyle);
						HSSFRichTextString str3 = new HSSFRichTextString(
								(String) ndmnDTO.getMethodId());
						cell3.setCellValue(str3);

				}
					Date date =new Date();
					 
					//String currentTime=new SimpleDateFormat("ddmmyyyy_hh_mm_ss").format(date);
					
					OutputStream out = response.getOutputStream();
			         FileInputStream in = new FileInputStream(file);
			         byte[] buffer = new byte[4096];
			         int length;
			         while ((length = in.read(buffer)) > 0){
			            out.write(buffer, 0, length);
			         }
			         wb.write(out);
			         in.close();
			         out.flush();

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

}
