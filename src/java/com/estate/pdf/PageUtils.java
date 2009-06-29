package com.estate.pdf;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;

/**
 * @author Paul Stay
 * 
 */
public class PageUtils {

	public static String[] formatToWidth(float width, String line,
			BaseFont font, float size) {
		String tmpList[] = new String[60];
		String formatted[];
		String temp;

		int c = 0;
		int idx = 0;
		int delIdx;
		if (line.length() == 0) {
			return new String[0];
		}
		do {
			idx = 0;
			temp = "";
			delIdx = -1;
			// Loop as long as the the width of the line we are building is less
			// than the requested width
			while (font.getWidthPoint(temp, size) < width) {
				// Check for whitespace and new lines.
				if (isDelimiter(line.charAt(idx))) {
					// Mark this as a place we can backup to to break the line.
					delIdx = idx;
					// If it's a line feed we force a line break.
					if (isLineFeed(line.charAt(idx))) {
						break;
					}
				}
				// Add the current charater to our temp string.
				temp = line.substring(0, idx + 1);

				// Move to the next character
				idx++;

				// Have we exceeded the number of characters in the string?
				if (idx >= line.length()) {
					// Set our marker, this will return the entire line when we
					// "break the line"
					delIdx = line.length();
					break;
				}

			}
			// We are pointing at the character that exceeded the width.
			// Now backup to the previous word
			if (delIdx > -1) {
				// Add the line to our array.
				tmpList[c++] = line.substring(0, delIdx);
				if (c >= tmpList.length) {
					// we need more space!

				}
				// If we have more text to go remove the processed portion
				if (delIdx < line.length()) {
					line = line.substring(delIdx + 1);
				} else {
					break;
				}
			} else {
				// I don't think we should get here.
			}

		} while (line.length() > 0);

		// Allocate the return array
		formatted = new String[c];

		// Copy the strings
		for (idx = 0; idx < c; idx++) {
			formatted[idx] = tmpList[idx];
		}

		return formatted;
	}

	public static boolean isDelimiter(char c) {
		boolean b;
		String delimiters = " \t\n";
		String _char = "" + c;
		b = delimiters.indexOf(_char) > -1 ? true : false;
		return (b);

	}

	public static boolean isLineFeed(char c) {
		boolean b;
		String delimiters = "\n";
		String _char = "" + c;
		b = delimiters.indexOf(_char) > -1 ? true : false;
		return (b);

	}

	// Convenience function for loading a font.
	public static BaseFont LoadFont(String FontName) {
		BaseFont font = null;
		Font f;
		/*
		*/
		/*
		try {
			font = BaseFont.createFont(Locations.getFontLocation() + FontName,
				BaseFont.CP1252, BaseFont.EMBEDDED);
			f = FontFactory.getFont(FontName, BaseFont.CP1252, BaseFont.EMBEDDED);
			font = f.getCalculatedBaseFont(false);
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			System.out.println(Locations.getFontLocation());
		} catch (DocumentException de) {
			System.out.println(de.getMessage());
			System.out.println(Locations.getFontLocation());
		}
		*/
		
		f = FontFactory.getFont(Locations.fontLocation + FontName, BaseFont.CP1252, BaseFont.EMBEDDED);
		font = f.getCalculatedBaseFont(false);
		return (font);

	}

	public static void setWriter() {

	}

}
