package com.winter.swallow;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

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
	 * 写入word文档
	 */
	public void writeToWord(List<List<Map<Integer, String>>> tableList) {
		XWPFDocument xwpfDocument = new XWPFDocument();
		OutputStream outputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		try {
			outputStream = new FileOutputStream(new File(CommonConst.FILE_PATH));
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
	public void writeTableToDoc(XWPFDocument xwpfDocument, List<Map<Integer, String>> rowInfoList, int pos) {
		XWPFTable xwpfTable = xwpfDocument.createTable(rowInfoList.size() + 2, 7);
		setTableWidth(xwpfTable);
		for (int i = 0; i < rowInfoList.size(); i++) {
			Map<Integer, String> rowInfo = rowInfoList.get(i);
			XWPFTableRow tableNameRow = xwpfTable.getRow(0);
			XWPFTableRow columnNameRow = xwpfTable.getRow(1);
			XWPFTableRow row = xwpfTable.getRow(i + 2);
			if (i == 0) {
				setTableCell(tableNameRow, tableNameRow.getCell(0), true, CommonConst.COLUMN_NAME_ARRAY[0]);
				mergeCellsHorizontal(xwpfTable, 0, 1, 2);
				setTableCell(tableNameRow, tableNameRow.getCell(1), false, rowInfo.get(0));

				setTableCell(tableNameRow, tableNameRow.getCell(3), true, CommonConst.COLUMN_NAME_ARRAY[1]);
				mergeCellsHorizontal(xwpfTable, 0, 4, 6);
				setTableCell(tableNameRow, tableNameRow.getCell(4), false, rowInfo.get(1));

				for (int j = 2; j < CommonConst.COLUMN_NAME_ARRAY.length; j++) {
					setTableCell(columnNameRow, columnNameRow.getCell(j - 2), true, CommonConst.COLUMN_NAME_ARRAY[j]);
				}
			}
			for (int k = 2; k < CommonConst.COLUMN_NAME_ARRAY.length; k++) {
				setTableCell(row, row.getCell(k - 2), false, rowInfo.get(k));
			}
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
		ctTblPr.getTblW().setW(new BigInteger(CommonConst.tableWidth));
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
			ctshd.setFill(CommonConst.tableCellBgColor);
		}
		// 设置单元格宽度
		CTTcPr tcpr = tableCell.getCTTc().addNewTcPr();
		CTTblWidth cellw = tcpr.addNewTcW();
		cellw.setW(new BigInteger(CommonConst.tableCellWidth));

		// 设置单元格高度
		CTTrPr trPr = tableRow.getCtRow().addNewTrPr();
		CTHeight cellH = trPr.addNewTrHeight();
		cellH.setVal(new BigInteger(CommonConst.tableCellHeight));

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
