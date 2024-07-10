package fr.diginamic.hello.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import fr.diginamic.hello.dto.VilleDto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
public class CSVHelper {

	public ByteArrayInputStream villesToCSV(List<VilleDto> villes) {
		String[] headers = { "NOM VILLE", "POPULATION", "CODE DEPARTEMENT", "NOM DEPARTEMENT" };
		CSVFormat format = CSVFormat.DEFAULT.builder().setHeader(headers).setSkipHeaderRecord(false).build();

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
				CSVPrinter csvPrinter = new CSVPrinter(writer, format)) {
			for (VilleDto ville : villes) {
				List<String> data = Arrays.asList(ville.getNomVille(), String.valueOf(ville.getNombreHabitants()),
						ville.getCodeDepartement(), ville.getNomDepartement());
				csvPrinter.printRecord(data);
			}

			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("Failed to import data to CSV file: " + e.getMessage());
		}
	}
}
