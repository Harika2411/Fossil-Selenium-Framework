package GenericUtilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {

	private Workbook workbook;
	private String filePath;

	// Constructor
	public ExcelUtility(String filePath) {

		this.filePath = filePath;

		try (FileInputStream fis = new FileInputStream(filePath)) {

			workbook = WorkbookFactory.create(fis);

		} catch (IOException e) {
			throw new RuntimeException("Unable to read Excel file", e);
		}
	}

	// Get Sheet
	public Sheet getSheet(String sheetName) {

		Sheet sheet = workbook.getSheet(sheetName);

		if (sheet == null) {
			throw new RuntimeException("Sheet not found: " + sheetName);
		}

		return sheet;
	}

	// Get Row Count
	public int getRowCount(String sheetName) {

		Sheet sheet = getSheet(sheetName);

		return sheet.getLastRowNum() + 1;
	}

	// Get Cell Count
	public int getCellCount(String sheetName, int rowNum) {

		Row row = getSheet(sheetName).getRow(rowNum);

		return row == null ? 0 : row.getLastCellNum();
	}

	// Get Cell Data
	public String getCellData(String sheetName, int rowNum, int cellNum) {

		Row row = getSheet(sheetName).getRow(rowNum);

		if (row == null) {
			return "";
		}

		Cell cell = row.getCell(cellNum);

		if (cell == null) {
			return "";
		}

		DataFormatter formatter = new DataFormatter();

		return formatter.formatCellValue(cell);
	}

	public Map<String, String> getTestData(String sheetName, String uniqueData) {

		Map<String, String> testData = new LinkedHashMap<>();

		Sheet sheet = getSheet(sheetName);

		DataFormatter formatter = new DataFormatter();

		// Search for the unique Test Case ID
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {

			Row dataRow = sheet.getRow(i);

			if (dataRow == null) {
				continue;
			}

			String testCaseID = formatter.formatCellValue(dataRow.getCell(0));

			if (testCaseID.equalsIgnoreCase(uniqueData)) {

				// Get the header row immediately above the data row
				if (i == 0) {
					throw new RuntimeException("Header row not found for: " + uniqueData);
				}

				Row headerRow = sheet.getRow(i - 1);

				if (headerRow == null) {
					throw new RuntimeException("Header row not found for: " + uniqueData);
				}

				// Read headers and corresponding values
				for (int j = 0; j < headerRow.getLastCellNum(); j++) {

					String header = formatter.formatCellValue(headerRow.getCell(j));

					if (header.isBlank()) {
						continue;
					}

					String value = formatter.formatCellValue(dataRow.getCell(j));

					testData.put(header, value);
				}

				return testData;
			}
		}

		throw new RuntimeException("Test Case ID not found: " + uniqueData);
	}

	// Write Cell Data
	public void setCellData(String sheetName, int rowNum, int cellNum, String data) {

		Sheet sheet = getSheet(sheetName);

		Row row = sheet.getRow(rowNum);

		if (row == null) {
			row = sheet.createRow(rowNum);
		}

		Cell cell = row.getCell(cellNum);

		if (cell == null) {
			cell = row.createCell(cellNum);
		}

		cell.setCellValue(data);
	}

	// Save Excel
	public void saveExcel() {

		try (FileOutputStream fos = new FileOutputStream(filePath)) {

			workbook.write(fos);

		} catch (IOException e) {
			throw new RuntimeException("Unable to save Excel file", e);
		}
	}

	// Close Excel
	public void closeExcel() {

		try {

			if (workbook != null) {
				workbook.close();
			}

		} catch (IOException e) {
			throw new RuntimeException("Unable to close Excel workbook", e);
		}
	}

	public void writeBackData(String sheetName, String uniqueData, String header, String writeBackData) {

		Sheet sheet = getSheet(sheetName);

		DataFormatter formatter = new DataFormatter();

		int headerColumn = -1;
		int dataRow = -1;

		// Step 1: Find the Test Case ID row
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {

			Row row = sheet.getRow(i);

			if (row == null) {
				continue;
			}

			String testCaseID = formatter.formatCellValue(row.getCell(0)).trim();

			if (testCaseID.equalsIgnoreCase(uniqueData.trim())) {
				dataRow = i;
				break;
			}
		}

		if (dataRow == -1) {
			throw new RuntimeException("Test Case ID not found: " + uniqueData);
		}

		// Step 2: Get the header row immediately above the data row
		if (dataRow == 0) {
			throw new RuntimeException("Header row not found for: " + uniqueData);
		}

		Row headerRow = sheet.getRow(dataRow - 1);

		if (headerRow == null) {
			throw new RuntimeException("Header row not found for: " + uniqueData);
		}

		// Step 3: Find the required header column
		for (int i = 0; i < headerRow.getLastCellNum(); i++) {

			String headerName = formatter.formatCellValue(headerRow.getCell(i)).trim();

			if (headerName.equalsIgnoreCase(header.trim())) {
				headerColumn = i;
				break;
			}
		}

		if (headerColumn == -1) {
			throw new RuntimeException("Header not found: " + header);
		}

		// Step 4: Write data into the identified cell
		Row row = sheet.getRow(dataRow);

		Cell cell = row.getCell(headerColumn);

		if (cell == null) {
			cell = row.createCell(headerColumn);
		}

		cell.setCellValue(writeBackData);

		// Step 5: Save Excel
		saveExcel();

		System.out.println("Successfully updated " + header + " for " + uniqueData + ": " + writeBackData);
	}

}