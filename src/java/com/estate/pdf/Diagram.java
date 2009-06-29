package com.estate.pdf;

import java.io.IOException;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;

/**
 * @author Paul Stay
 * 
 */
public class Diagram {

	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int TOP = 4;
	public static final int BOTTOM = 8;
	public static final int CNTR_LR = LEFT + RIGHT;
	public static final int CNTR_TB = TOP + BOTTOM;
	private Image diagram = null;

	public Diagram(Image img) {
		diagram = img;
	}

	public Diagram(String ImageName) {
		try {

			// Load the Diagram Image
			diagram = Image.getInstance(Locations.getImageLocation()
					+ ImageName);
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} catch (BadElementException bee) {
			System.out.println(bee.getMessage());
		}
	}

	private float _getScale() {
		return _getScale(300);
	}

	private float _getScale(int DPI) {
		float width1 = diagram.getWidth() / DPI;
		float width2 = diagram.getWidth() / 72;
		float percent = (width1 / width2);

		return percent;
	}

	public float getHeight() {
		float hgt = 0;
		float pct = _getScale();
		hgt = diagram.getHeight() * pct;

		return hgt;
	}

	public float getWidth() {
		float wdt = 0;
		float pct = _getScale();
		wdt = diagram.getWidth() * pct;

		return wdt;
	}

	public void placeDiagram(float xInch, float yInch, Document doc) {
		if (null == diagram) {

		} else {
			try {
				float percent = this._getScale();

				diagram.setAbsolutePosition(xInch * 72, yInch * 72);
				diagram.scalePercent(percent * 100);
				doc.add(diagram);
			} catch (DocumentException de) {
				System.out.println(de.getMessage());
			}
		}
	}

	public void placeDiagram(float xInch, float yInch, Document doc, int DPI) {
		if (null == diagram) {

		} else {
			try {
				float percent = this._getScale(DPI);

				diagram.setAbsolutePosition(xInch * 72, yInch * 72);
				diagram.scalePercent(percent * 100);
				doc.add(diagram);
			} catch (DocumentException de) {
				System.out.println(de.getMessage());
			}
		}

	}

	public void placeDiagram(Rectangle rct, Document doc, int align) {
		if (null == diagram) {

		} else {
			try {
				float percent = this._getScale();
				float xInch = 0, yInch = 0;
				int alg;
				diagram.scalePercent(percent * 100);

				// Now we figure out alignment.
				alg = align & CNTR_LR;
				switch (alg) {
				case LEFT:
					xInch = rct.getLeft();
					break;
				case RIGHT:
					xInch = rct.getRight() - (diagram.getWidth() * percent);
					break;
				default: // This will center it.
					xInch = rct.getLeft()
							+ (rct.getWidth() - (diagram.getWidth() * percent)) / 2;
					break;
				}

				alg = align & CNTR_TB;
				switch (alg) {
				case TOP:
					yInch = rct.getTop() - (diagram.getHeight() * percent);
					break;
				case BOTTOM:
					// yInch = rct.getTop() - (diagram.getHeight() * percent);
					yInch = rct.getBottom();
					break;
				default: // This will center it.
					yInch = rct.getBottom()
							+ (rct.getHeight() - (diagram.getHeight() * percent)) / 2;
					break;
				}

				diagram.setAbsolutePosition(xInch, yInch);

				doc.add(diagram);
			} catch (DocumentException de) {
				System.out.println(de.getMessage());
			}
		}

	}

	public void placeDiagram(Rectangle rct, Document doc, int align, int DPI) {
		if (null == diagram) {

		} else {
			try {
				float percent = this._getScale(DPI);
				float xInch = 0, yInch = 0;
				int alg;
				diagram.scalePercent(percent * 100);

				// Now we figure out alignment.
				alg = align & CNTR_LR;
				switch (alg) {
				case LEFT:
					xInch = rct.getLeft();
					break;
				case RIGHT:
					xInch = rct.getRight() - (diagram.getWidth() * percent);
					break;
				default: // This will center it.
					xInch = rct.getLeft()
							+ (rct.getWidth() - (diagram.getWidth() * percent)) / 2;
					break;
				}

				alg = align & CNTR_TB;
				switch (alg) {
				case TOP:
					yInch = rct.getTop();
					break;
				case BOTTOM:
					// yInch = rct.getTop() - (diagram.getHeight() * percent);
					yInch = rct.getBottom();
					break;
				default: // This will center it.
					yInch = rct.getBottom()
							+ (rct.getHeight() - (diagram.getHeight() * percent)) / 2;
					break;
				}

				diagram.setAbsolutePosition(xInch, yInch);

				doc.add(diagram);
			} catch (DocumentException de) {
				System.out.println(de.getMessage());
			}
		}

	}
}
