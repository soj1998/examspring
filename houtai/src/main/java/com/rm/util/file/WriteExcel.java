package com.rm.util.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcel {
	public final static int XLS = 97;
	public final static int XLSX = 2007;
	
	
	/**
	 * 创建Workbook
	 * @param type			Excel类型, 97-2003或2007
	 * @return
	 * @throws IOException
	 */
	public static Workbook createWorkBook(int type) throws IOException {
		Workbook wb = null;
		if(type == XLSX) {
			wb = new XSSFWorkbook();
		} else {
			wb = new HSSFWorkbook();
		}
		return wb;
	}

	/**
	 * 将数据写入到文件中
	 * @param wb
	 * @param sheetName
	 * @param fileName
	 * @param sheetResult
	 * @throws IOException
	 */
	public static void writeDataToExcel(Workbook wb, String sheetName,
						String fileName, SheetResult sheetResult) throws IOException {
		
		Sheet sheet = createSheet(wb, sheetName);
		int rownum = 0;
		int column = 0;
		
		CellStyle cellStyle = createHeadCellStyle(wb);
		
		
		//写头部信息
		for(int i = 0, len = sheetResult.getHeadRowNum(); i < len; i++) {
			Row row = createRow(sheet, rownum);
			
			column = 0;
			List<String> tempValueList = sheetResult.getDataList().get(i);
			for(String title : tempValueList) {
				Cell cell = createCell(row, column);
				cell.setCellValue(title);
				cell.setCellStyle(cellStyle);
				column++;
			}
			
			rownum++;

		}
		
		
		CellStyle defaultStyle = createDefaultCellStyle(wb);
		//写数据部分
		for(int i = rownum, len = sheetResult.getDataList().size(); i < len; i++) {
			Row row = createRow(sheet, i);
			
			column = 0;
			for(String colData : sheetResult.getDataList().get(i)) {
				Cell cell = createCell(row, column);
				cell.setCellValue(colData);
				cell.setCellStyle(defaultStyle);
				column++;
			}
		}
		
		Map<String, MergingCell> mergeMap = getMerginCellMap(sheetResult);
		
		//合并行列
		for(Entry<String, MergingCell> entry: mergeMap.entrySet()) {
			MergingCell mergingCell = entry.getValue();
			MergingCells(sheet, mergingCell.getFirstRow(), mergingCell.getLastRow(),
					mergingCell.getFirstColumn(), mergingCell.getLastColumn());
		}
		
		File dir = new File(fileName.substring(0, fileName.lastIndexOf(File.separatorChar)));
		if(!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(fileName);
		if(!file.exists()) {
			file.createNewFile();
		}
		
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			wb.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 获取头部列的行列合并信息
	 * @param sheetResult
	 * @return
	 */
	private static Map<String, MergingCell> getMerginCellMap(SheetResult sheetResult) {
		Map<String, MergingCell> mergeMap = new HashMap<String, MergingCell>();
		
		//记录已合并的列索引集合
		Set<Integer> colIndexSet = new HashSet<Integer>();
		
		String tempValue = null;
		int colSum = 0;
		int rowSum = 0;
		
		for(int i = 0, len = sheetResult.getHeadRowNum() ; i < len; i++) {
			
			for(int j = 0, jLen = sheetResult.getDataList().get(i).size(); j < jLen; j++) {
				tempValue = sheetResult.getDataList().get(i).get(j);
				colSum = 0;
				rowSum = 0;
				if(!"".equals(tempValue)) {
					
					//列合并搜索
					for(int k = j + 1; k < jLen; k++) {
						if("".equals(sheetResult.getDataList().get(i).get(k)) && !colIndexSet.contains(k)) {
							colSum++;
						} else {
							break;
						}
					}
					
					//行处理
					for(int m = i + 1; m < sheetResult.getHeadRowNum(); m++) {
						if("".equals(sheetResult.getDataList().get(m).get(j))) {
							rowSum++;
						} else {
							break;
						}
					}
					
					if(colSum != 0 || rowSum != 0) {
						if(mergeMap.get(tempValue) == null) {
							MergingCell mergingCell = new MergingCell();
							mergingCell.setFirstColumn(j);
							mergingCell.setLastColumn(j + colSum);
							mergingCell.setFirstRow(i);
							mergingCell.setLastRow(i + rowSum);
							mergeMap.put(tempValue, mergingCell);
							colIndexSet.add(j);
						}
						j += colSum;
					}
				}
			}
		}
		return mergeMap;

	}
//原文链接：https://blog.csdn.net/u012009613/article/details/52270283
	/**
	 * 合并单元格，可以根据设置的值来合并行和列
	 * @param sheet
	 * @param firstRow
	 * @param lastRow
	 * @param firstColumn
	 * @param lastColumn
	 */
	private static void MergingCells(Sheet sheet, int firstRow, int lastRow,
											int firstColumn, int lastColumn) {
		sheet.addMergedRegion(new CellRangeAddress(
				firstRow, //first row (0-based)
				lastRow, //last row  (0-based)
				firstColumn, //first column (0-based)
				lastColumn  //last column  (0-based)
	    ));
	}
	
	/**
	 * 创建头部样式
	 * @param wb
	 * @return
	 */
	private static CellStyle createHeadCellStyle(Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		addAlignStyle(cellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
		addBorderStyle(cellStyle, BorderStyle.MEDIUM, IndexedColors.BLACK.getIndex());
		addColor(cellStyle, IndexedColors.GREY_25_PERCENT.getIndex(), IndexedColors.WHITE.getIndex());
		return cellStyle;
	}
	
	/**
	 * 创建普通单元格样式
	 * @param wb
	 * @return
	 */
	private static CellStyle createDefaultCellStyle(Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		addAlignStyle(cellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
		addBorderStyle(cellStyle, BorderStyle.MEDIUM, IndexedColors.BLACK.getIndex());
		return cellStyle;
	}
	
	/**
	 * cell文本位置样式
	 * @param cellStyle
	 * @param halign
	 * @param valign
	 * @return
	 */
	private static CellStyle addAlignStyle(CellStyle cellStyle, 
			HorizontalAlignment halign, VerticalAlignment valign) {
		cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        return cellStyle;
	}
	
	/**
	 * cell边框样式
	 * @param cellStyle
	 * @return
	 */
	private static CellStyle addBorderStyle(CellStyle cellStyle, BorderStyle borderSize, short colorIndex) {
		cellStyle.setBorderBottom(borderSize);
		cellStyle.setBottomBorderColor(colorIndex);
		cellStyle.setBorderLeft(borderSize);
		cellStyle.setLeftBorderColor(colorIndex);
		cellStyle.setBorderRight(borderSize);
		cellStyle.setRightBorderColor(colorIndex);
		cellStyle.setBorderTop(borderSize);
		cellStyle.setTopBorderColor(colorIndex);
	    return cellStyle;
	}
	


	
	/**
	 * 给cell设置颜色
	 * @param cellStyle
	 * @param backgroundColor
	 * @param fillPattern
	 * @return
	 */
	private static CellStyle addColor(CellStyle cellStyle, 
								short backgroundColor, short fillPattern ) {
		cellStyle.setFillForegroundColor(backgroundColor);
		cellStyle.setFillPattern(FillPatternType.forInt(fillPattern));
		return cellStyle;
	}
	
	private static Sheet createSheet(Workbook wb, String sheetName) {
		return wb.createSheet(sheetName);
	}
	
	private static Row createRow(Sheet sheet, int rownum) {
		return sheet.createRow(rownum);
	}
	
	private static Cell createCell(Row row, int column) {
		return row.createCell(column);
	}



}
