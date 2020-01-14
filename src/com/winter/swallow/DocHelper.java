package com.winter.swallow;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

/**
 * @Author winterSwallow
 * @Date 2020-01-09 16:41
 * @Description 文档工具类定义
 */
public class DocHelper {

	/**
	 * 表格宽度
	 */
	public static String tableWidth = "9000";

	/**
	 * 表格单元格背景色
	 */
	public static String tableCellBgColor = "D9EDF7";
	/**
	 * 表格单元格宽度
	 */
	public static String tableCellWidth = "1300";
	/**
	 * 表格单元格高度
	 */
	public static String tableCellHeight = "360";

	/**
	 * 写入word文档
	 */
	public void writeToWord(List<List<RowInfo>> tableList, String filePath) {
		XWPFDocument xwpfDocument = new XWPFDocument();
		OutputStream outputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		try {
			outputStream = new FileOutputStream(new File(filePath));
			bufferedOutputStream = new BufferedOutputStream(outputStream, 1024);
			for (int i = 0; i < tableList.size(); i++) {
				writeTableToDoc(xwpfDocument, tableList.get(i), i);
			}
			xwpfDocument.write(outputStream);
			outputStream.close();
			bufferedOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bufferedOutputStream != null) {
				try {
					bufferedOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 在文档中写入表格
	 *
	 * @param xwpfDocument
	 * @param rowInfoList
	 * @param pos
	 */
	public void writeTableToDoc(XWPFDocument xwpfDocument, List<RowInfo> rowInfoList, int pos) {
		XWPFTable xwpfTable = xwpfDocument.createTable(rowInfoList.size() + 2, 7);
		setTableWidth(xwpfTable);
		for (int i = 0; i < rowInfoList.size(); i++) {
			RowInfo rowInfo = rowInfoList.get(i);
			XWPFTableRow tableNameRow = xwpfTable.getRow(0);
			XWPFTableRow columnNameRow = xwpfTable.getRow(1);
			XWPFTableRow dataRow = xwpfTable.getRow(i + 2);
			if (i == 0) {
				// 第一行表英文名
				setTableCell(tableNameRow, tableNameRow.getCell(0), true, "英文名");
				mergeCellsHorizontal(xwpfTable, 0, 1, 2);
				setTableCell(tableNameRow, tableNameRow.getCell(1), false, rowInfo.getTableName());

				// 第一行表中文注释
				setTableCell(tableNameRow, tableNameRow.getCell(3), true, "中文名");
				mergeCellsHorizontal(xwpfTable, 0, 4, 6);
				setTableCell(tableNameRow, tableNameRow.getCell(4), false, rowInfo.getTableComment());

				// 第二行列标题
				setTableCell(columnNameRow, columnNameRow.getCell(0), true, "列名");
				setTableCell(columnNameRow, columnNameRow.getCell(1), true, "列类型");
				setTableCell(columnNameRow, columnNameRow.getCell(2), true, "可为空");
				setTableCell(columnNameRow, columnNameRow.getCell(3), true, "主键");
				setTableCell(columnNameRow, columnNameRow.getCell(4), true, "默认值");
				setTableCell(columnNameRow, columnNameRow.getCell(5), true, "列注释");
				setTableCell(columnNameRow, columnNameRow.getCell(6), true, "其他");
			}

			setTableCell(dataRow, dataRow.getCell(0), false, rowInfo.getColumnName());
			setTableCell(dataRow, dataRow.getCell(1), false, rowInfo.getColumnType());
			setTableCell(dataRow, dataRow.getCell(2), false, rowInfo.getIsNullEnable());
			setTableCell(dataRow, dataRow.getCell(3), false, rowInfo.getIsPrimaryKey());
			setTableCell(dataRow, dataRow.getCell(4), false, rowInfo.getDefaultValue());
			setTableCell(dataRow, dataRow.getCell(5), false, rowInfo.getDescription());
			setTableCell(dataRow, dataRow.getCell(6), false, rowInfo.getOther());
		}
		xwpfDocument.setTable(pos, xwpfTable);
		xwpfDocument.createParagraph();
	}

	/**
	 * 设置表格宽度
	 *
	 * @param xwpfTable
	 */
	public void setTableWidth(XWPFTable xwpfTable) {
		CTTblPr ctTblPr = xwpfTable.getCTTbl().getTblPr();
		ctTblPr.getTblW().setType(STTblWidth.DXA);
		ctTblPr.getTblW().setW(new BigInteger(tableWidth));
	}

	/**
	 * 设置表格单元格内容
	 *
	 * @param tableRow
	 * @param tableCell
	 * @param isFillCollor
	 * @param text
	 */
	public void setTableCell(XWPFTableRow tableRow, XWPFTableCell tableCell, boolean isFillCollor, String text) {
		if (isFillCollor) {
			CTTcPr tcpr = tableCell.getCTTc().addNewTcPr();
			CTShd ctshd = tcpr.addNewShd();
			ctshd.setFill(tableCellBgColor);
		}
		// 设置单元格宽度
		CTTcPr tcpr = tableCell.getCTTc().addNewTcPr();
		CTTblWidth cellw = tcpr.addNewTcW();
		cellw.setW(new BigInteger(tableCellWidth));

		// 设置单元格高度
		CTTrPr trPr = tableRow.getCtRow().addNewTrPr();
		CTHeight cellH = trPr.addNewTrHeight();
		cellH.setVal(new BigInteger(tableCellHeight));

		// 设置表格字体和内容
		XWPFParagraph pIO = tableCell.getParagraphs().get(0);
		XWPFRun rIO = pIO.createRun();
		rIO.setFontFamily("宋体");
		rIO.setFontSize(10);
		rIO.setText(text);
		tableCell.setParagraph(pIO);
		tableCell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

	}

	/**
	 * 跨列合并单元格
	 *
	 * @param table
	 * @param row
	 * @param fromCell
	 * @param toCell
	 */
	public void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
		for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
			XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
			if (cellIndex == fromCell) {
				cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
			} else {
				cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
			}
		}
	}
}
