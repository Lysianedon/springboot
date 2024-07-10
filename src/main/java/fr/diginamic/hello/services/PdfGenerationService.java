package fr.diginamic.hello.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import fr.diginamic.hello.dto.VilleDto;
import fr.diginamic.hello.entities.Ville;
import jakarta.servlet.ServletOutputStream;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class PdfGenerationService {

	public void generateTopCitiesReport(List<VilleDto> cities, OutputStream outputStream, int nbVilles)
			throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, outputStream);
		document.open();

		BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
		Font titleFont = new Font(baseFont, 18, Font.BOLD);
		Font headerFont = new Font(baseFont, 12, Font.BOLD);
		Font cellFont = new Font(baseFont, 12, Font.NORMAL);

		// Title
		Paragraph title = new Paragraph("Top " + nbVilles + " Cities", titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);

		// Space
		document.add(new Paragraph(" "));

		// Table with 4 columns
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setSpacingBefore(10f);
		table.setSpacingAfter(10f);

		// Set Column widths
		float[] columnWidths = { 2f, 2f, 2f, 2f };
		table.setWidths(columnWidths);

		// Table headers
		String[] headers = { "NOM VILLE", "POPULATION", "CODE DEPARTEMENT", "NOM DEPARTEMENT" };
		for (String header : headers) {
			PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
			headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerCell.setPadding(5);
			table.addCell(headerCell);
		}

		for (VilleDto city : cities) {
			table.addCell(new Phrase(city.getNomVille(), cellFont));
			PdfPCell populationCell = new PdfPCell(new Phrase(String.valueOf(city.getNombreHabitants()), cellFont));
			populationCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(populationCell);
			PdfPCell codeDeptCell = new PdfPCell(new Phrase(city.getCodeDepartement(), cellFont));
			codeDeptCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(codeDeptCell);
			table.addCell(new Phrase(city.getNomDepartement(), cellFont));
		}
		document.add(table);
		document.close();
	}

	public void generatePDFReport(String titlePDF, String[] headers, List<Ville> cities,
			ServletOutputStream outputStream) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, outputStream);
		document.open();

		BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
		Font titleFont = new Font(baseFont, 18, Font.BOLD);
		Font headerFont = new Font(baseFont, 12, Font.BOLD);
		Font cellFont = new Font(baseFont, 12, Font.NORMAL);

		// Title
		Paragraph title = new Paragraph(titlePDF, titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);

		// Space
		document.add(new Paragraph(" "));

		// Table with 2 columns
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.setSpacingBefore(10f);
		table.setSpacingAfter(10f);

		// Set Column widths
		float[] columnWidths = {2f, 2f};
		table.setWidths(columnWidths);

		// Table headers
		for (String header : headers) {
			PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
			headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerCell.setPadding(5);
			table.addCell(headerCell);
		}

		for (Ville city : cities) {
			table.addCell(new Phrase(city.getNom(), cellFont));
			PdfPCell populationCell = new PdfPCell(new Phrase(String.valueOf(city.getNbHabitants()), cellFont));
			populationCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(populationCell);
		}
		document.add(table);
		document.close();		
	}
}
