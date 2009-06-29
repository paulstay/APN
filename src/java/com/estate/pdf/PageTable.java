package com.estate.pdf;

/**
 * @author Paul Stay
 */

import java.awt.Color;
import java.util.HashMap;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPTableEvent;
import com.lowagie.text.pdf.PdfWriter;

public class PageTable implements PdfPTableEvent {
	private PdfWriter writer;

	private PdfPTable table;

	private Rectangle tblRect;

	private int align[];

	private BaseFont baseFont;

	private BaseFont baseFontBold;

	private BaseFont baseFontItalic;

	private BaseFont baseFontBoldItalic;

	private float fontSize = 11;

	private float paddingTop;

	private float paddingBottom = 1.5f;

	// private Font normal;

	private int colCount;

	private Rectangle resultRect;

	private float cellLeading = 1;

	public PageTable(PdfWriter writer) {
		this.writer = writer;
		table = null;
		paddingTop = 0;
	}

	public void buildTable(String data[][], HashMap<String,Object> formats) {
		if (table == null)
			return;
		CellInfoFactory cif = new CellInfoFactory();
		cif.setDefaultFont(new Font(baseFont, 11, Font.NORMAL));

		for (int row = 0; row < data.length; row++) {
			for (int col = 0; col < data[row].length; col++) {
				CellInfo ci = cif.getCellInfo(data[row][col]);
				String format = (String) formats.get("" + row + "," + col);
				if (align != null) {
					ci.horizAlignment = align[col];
				} else {
					ci.horizAlignment = Element.ALIGN_LEFT;
				}
				if (format != null) {
					this.format(format, ci);
				}

				PdfPCell cell = new PdfPCell(new Paragraph(ci.text, ci.font));
				cell.setBorder(ci.border);
				cell.setHorizontalAlignment(ci.horizAlignment);
				cell.setVerticalAlignment(ci.vertAlignment);
				if (ci.colSpan > 0) {
					cell.setColspan(ci.colSpan);
					col += ci.colSpan;
				}

				table.addCell(cell);
			}
		}

	}

	public void buildTableEx(String data[][]) {
		if (table == null)
			return;
		try {
			CellInfoFactory cif = new CellInfoFactory();
			cif.setDefaultFont(new Font(baseFont, fontSize, Font.NORMAL,
					new Color(0, 0, 0)));

			for (int row = 0; row < data.length; row++) {
				if ((colCount + 1) != data[row].length) {
					return;
				}
				String format = data[row][colCount];

				for (int col = 0; col < colCount; col++) {
					CellInfo ci = cif.getCellInfo(data[row][col]);

					if (align != null) {
						ci.horizAlignment = align[col];
					} else {
						ci.horizAlignment = Element.ALIGN_LEFT;
					}
					if (format != null) {
						format = this.formatEx(format, ci);
					}
					Paragraph p = null;
					try {
						p = new Paragraph(ci.text, ci.font);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					PdfPCell cell = new PdfPCell(p);
					cell.setBorder(ci.border);
					cell.setHorizontalAlignment(ci.horizAlignment);
					cell.setVerticalAlignment(ci.vertAlignment);
					cell.setLeading(1, cellLeading);
					cell.setPaddingTop(paddingTop);
					cell.setPaddingBottom(paddingBottom);

					if (ci.colSpan > 0) {
						cell.setColspan(ci.colSpan);
						col += ci.colSpan;
						col--;
						for (int i = 0; i < ci.colSpan - 1; i++) {
							format = this.formatEx(format, null);
						}
					}

					table.addCell(cell);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void drawTable() {
		if (table == null)
			return;
		table.setTableEvent(this);
		table.writeSelectedRows(0, -1, tblRect.getLeft(), tblRect.getTop(), writer
				.getDirectContent());
	}

	private void format(String format, CellInfo ci) {
		int idx;
		String formatInfo;
		String tag;
		int iVal = 0;
		String val;
		float ptSize = fontSize;
		boolean underline = false;
		boolean bold = false;
		boolean italic = false;
		BaseFont cellFont = baseFont;
		int color = 0;
		boolean fontChange = false;

		if (ci == null)
			return;

		while (format.length() > 0) {
			// Get the first format info
			idx = format.indexOf(",");
			if (idx > -1) {
				formatInfo = format.substring(0, idx);
				format = format.substring(idx + 1);
			} else {
				formatInfo = format;
				format = "";
			}

			idx = formatInfo.indexOf("=");
			tag = formatInfo.substring(0, idx);
			val = formatInfo.substring(idx + 1);

			if (tag.equalsIgnoreCase("colspan")) {
				iVal = Integer.valueOf(val).intValue();
				ci.colSpan = iVal;
			} else if (tag.equalsIgnoreCase("align")) {

				int align = 0;
				if (val.equalsIgnoreCase("left")) {
					align = Element.ALIGN_LEFT;
				} else if (val.equalsIgnoreCase("center")) {
					align = Element.ALIGN_CENTER;
				} else if (val.equalsIgnoreCase("right")) {
					align = Element.ALIGN_RIGHT;
				} else {
					// We assume it's a number
					align = Integer.valueOf(val).intValue();
				}

				ci.horizAlignment = align;
			} else if (tag.equalsIgnoreCase("valign")) {

				int align = 0;
				if (val.equalsIgnoreCase("top")) {
					align = Element.ALIGN_TOP;
				} else if (val.equalsIgnoreCase("center")) {
					align = Element.ALIGN_CENTER;
				} else if (val.equalsIgnoreCase("bottom")) {
					align = Element.ALIGN_BOTTOM;
				} else {
					// We assume it's a number
					align = Integer.valueOf(val).intValue();
				}

				ci.vertAlignment = align;
			}

			else if (tag.equalsIgnoreCase("border")) {
				int brdr = 0;
				int idxx;
				String border;
				int borders[] = { Rectangle.LEFT, Rectangle.TOP, Rectangle.RIGHT,
						Rectangle.BOTTOM, };
				String borderTypes = "ltrb";
				// iVal = Integer.valueOf(val).intValue();
				while (val.length() > 0) {
					idxx = val.indexOf("+");
					if (idxx == -1) {
						border = val;
						val = "";
					} else {
						border = val.substring(0, idxx);
						val = val.substring(idxx + 1);
					}
					border.toLowerCase();
					idxx = borderTypes.indexOf(border);
					brdr += borders[idxx];

				}
				ci.border |= brdr;
			} else if (tag.equalsIgnoreCase("color")) {
				iVal = Integer.valueOf(val).intValue();
				color = iVal;
				fontChange = true;
				// Color c = new Color(iVal);
				// ci.font = new Font(baseFont, ptSize, Font.NORMAL, c);
			} else if (tag.equalsIgnoreCase("underline")) {
				// ci.font = new Font(baseFont, ptSize, Font.UNDERLINE);
				underline = true;
				fontChange = true;
			} else if (tag.equalsIgnoreCase("italic")) {

				if (baseFontItalic != null) {
					// ci.font = new Font(baseFont, ptSize, Font.BOLD);
					cellFont = baseFontItalic;

				} else {
					// ci.font = new Font(baseFontBold, ptSize, Font.NORMAL);
					italic = true;

				}
				fontChange = true;
			} else if (tag.equalsIgnoreCase("bolditalic")) {

				if (baseFontBoldItalic != null) {
					// ci.font = new Font(baseFont, ptSize, Font.BOLD);
					cellFont = baseFontBoldItalic;

				} else {
					// ci.font = new Font(baseFontBold, ptSize, Font.NORMAL);
					bold = true;
					italic = true;

				}
				fontChange = true;
			}

			else if (tag.equalsIgnoreCase("bold")) {

				if (baseFontBold != null) {
					// ci.font = new Font(baseFont, ptSize, Font.BOLD);
					cellFont = baseFontBold;

				} else {
					// ci.font = new Font(baseFontBold, ptSize, Font.NORMAL);
					bold = true;

				}
				fontChange = true;
			} else if (tag.equalsIgnoreCase("ptsize")) {
				ptSize = Float.valueOf(val).floatValue();
				fontChange = true;
			} else if (tag.equalsIgnoreCase("font")) {
				if (val.length() == 0) {
					// ci.font = new Font(baseFont, ptSize, Font.NORMAL);
				} else {
					fontChange = true;
					cellFont = PageUtils.LoadFont(val);
					// ci.font = new Font(bFont, ptSize, Font.NORMAL);
				}
			}

		}

		if (fontChange == true) {
			ci.font = new Font(cellFont, ptSize, (bold == true ? Font.BOLD : 0)
					+ (underline == true ? Font.UNDERLINE : 0)
					+ (italic == true ? Font.ITALIC : 0), new Color(color));
		}

	}

	private String formatEx(String formats, CellInfo ci) {
		// We want to extract the next format
		String format;
		int idx;

		idx = formats.indexOf("]");
		if (idx > -1) {
			format = formats.substring(1, idx);
			formats = formats.substring(idx + 1);
			if (format.length() > 0) {
				this.format(format, ci);
			}
		}

		return (formats);
	}

	/**
	 * @return Returns the cellLeading.
	 */
	public float getCellLeading() {
		return cellLeading;
	}

	/**
	 * @return Returns the fontSize.
	 */
	public float getFontSize() {
		return fontSize;
	}

	/**
	 * @return Returns the paddingBottom.
	 */
	public float getPaddingBottom() {
		return paddingBottom;
	}

	/**
	 * @return Returns the paddingTop.
	 */
	public float getPaddingTop() {
		return paddingTop;
	}

	/**
	 * @return Returns the resultRect.
	 */
	public Rectangle getResultRect() {
		return resultRect;
	}

	/**
	 * @param cellLeading
	 *            The cellLeading to set.
	 */
	public void setCellLeading(float cellLeading) {
		this.cellLeading = cellLeading;
	}

	public void setColumnAlignments(int alignments[]) {
		align = new int[alignments.length];
		for (int i = 0; i < align.length; i++) {
			align[i] = alignments[i];
		}
	}

	public void setColumnWidths(float wdts[]) {
		if (table == null)
			return;

		try {
			table.setWidths(wdts);
		} catch (Exception de) {
			de.printStackTrace();
		}

	}

	/**
	 * @param fontSize
	 *            The fontSize to set.
	 */
	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * @param paddingBottom
	 *            The paddingBottom to set.
	 */
	public void setPaddingBottom(float paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

	/**
	 * @param paddingTop
	 *            The paddingTop to set.
	 */
	public void setPaddingTop(float paddingTop) {
		this.paddingTop = paddingTop;
	}

	/**
	 * @param resultRect
	 *            The resultRect to set.
	 */
	public void setResultRect(Rectangle resultRect) {
		this.resultRect = resultRect;
	}

	public void setTableData(CellInfo data[][]) {
		if (table == null)
			return;

		for (int row = 0; row < data.length; row++) {
			for (int col = 0; col < data[row].length; col++) {
				CellInfo ci = data[row][col];
				PdfPCell cell = new PdfPCell(new Paragraph(ci.text, ci.font));
				cell.setBorder(ci.border);
				cell.setHorizontalAlignment(ci.horizAlignment);
				cell.setVerticalAlignment(ci.vertAlignment);

				table.addCell(cell);
			}
		}
	}

	/***************************************************************************
	 * ;setTableData
	 * 
	 * @param data
	 */
	public void setTableData(String data[][]) {
		if (table == null)
			return;

		for (int row = 0; row < data.length; row++) {
			for (int col = 0; col < data[row].length; col++) {
				if (align == null) {
					// table.addCell(data[row][col]);
					PdfPCell cell = new PdfPCell(new Paragraph(data[row][col],
							new Font(baseFont, fontSize)));
					cell.setBorder(0);
					table.addCell(cell);

				} else {
					PdfPCell cell = new PdfPCell(new Paragraph(data[row][col],
							new Font(baseFont, fontSize)));
					cell.setBorder(0);
					cell.setHorizontalAlignment(align[col]);
					table.addCell(cell);
				}
			}
		}
	}

	/***************************************************************************
	 * ;setTableFormat Desc: This method creates the table with the number of
	 * colums specified Each column is the same width.
	 * 
	 * @param tblRect
	 * @param numCols\
	 * 
	 */

	public void setTableFont(String fontName) {
		baseFont = fontName == null ? null : PageUtils.LoadFont(fontName);

	}

	public void setTableFontBold(String fontName) {
		baseFontBold = fontName == null ? null : PageUtils.LoadFont(fontName);

	}

	public void setTableFontBoldItalic(String fontName) {
		baseFontBoldItalic = fontName == null ? null : PageUtils
				.LoadFont(fontName);

	}

	public void setTableFontItalic(String fontName) {
		baseFontItalic = fontName == null ? null : PageUtils.LoadFont(fontName);

	}

	public void setTableFormat(Rectangle tblRect, int numCols) {
		float columns[] = new float[numCols];
		// Create an array of even columns
		colCount = numCols;
		for (int i = 0; i < numCols; i++) {
			columns[i] = 1f;
		}
		// create the table
		if (numCols > 0) {
			table = new PdfPTable(columns);
			// make a copy of the rectangle the table occupies
			this.tblRect = new Rectangle(tblRect);

			// Set the width of the table.
			table.setTotalWidth(tblRect.getRight() - tblRect.getLeft());
			table.getDefaultCell().setBorderWidth(0);
			table.getDefaultCell().setPadding(0);
		} else {
			table = null;
		}

	}

	public void tableLayout(PdfPTable table, float[][] width, float[] heights,
			int headerRows, int rowStart, PdfContentByte[] canvases) {
		// widths of the different cells of the first row
		float widths[] = width[0];
		// System.out.println(widths[0] + " " + heights[heights.length - 1] + "
		// " + (widths[widths.length - 1] - widths[0]) + " " + (heights[0] -
		// heights[heights.length - 1]));

		resultRect = new Rectangle(widths[0], heights[heights.length - 1], 0f,
				0f);
		resultRect.setRight(resultRect.getLeft()
				+ (widths[widths.length - 1] - widths[0]));
		resultRect.setTop(resultRect.getBottom()
				+ (heights[0] - heights[heights.length - 1]));

	}
}
