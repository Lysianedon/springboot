package fr.diginamic.hello.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diginamic.hello.dto.VilleDto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CSVGenerationService {

	/**
	 * Generates a CSV report for a list of city DTOs.
	 *
	 * @param cities       the list of cities to generate the report for
	 * @param outputStream the output stream to write the CSV to
	 * @throws IOException if an I/O error occurs writing to the output stream
	 */
	public void generateCitiesCsvReport(List<VilleDto> cities, OutputStream outputStream) throws IOException {
		String[] headers = { "NOM VILLE", "POPULATION", "CODE DEPARTEMENT", "NOM DEPARTEMENT" };

		System.out.println("Received " + cities.size() + " cities.");
		try (OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
				CSVPrinter csvPrinter = new CSVPrinter(streamWriter,
						CSVFormat.DEFAULT.builder().setHeader(headers).setSkipHeaderRecord(false).build())) {
			for (VilleDto city : cities) {
				csvPrinter.printRecord(city.getNomVille(), city.getNombreHabitants(), city.getCodeDepartement(),
						city.getNomDepartement());
			}
			csvPrinter.flush();
		} catch (Exception e) {
			throw new IOException("Failed to generate CSV report", e);
		}
	}

//    @Autowired
//    private CSVHelper csvHelper;
//
//	public ByteArrayInputStream generateCitiesCsvReport(List<VilleDto> cities) {
//        return csvHelper.villesToCSV(cities);
//    }

}
