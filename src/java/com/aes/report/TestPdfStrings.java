/**
 * Author Paul Stay
 * Description: Externalize Strings Test
 * Date December 2008
 */
package com.aes.report;

import java.text.DecimalFormat;


public class TestPdfStrings {

	/**
	 * @param document
	 * @param writer
	 */
	public TestPdfStrings(){
		
	}

	public void page1() {
		double percent = .045;
		DecimalFormat df = new DecimalFormat("##.###%"); //$NON-NLS-1$
		
		String b1 = "TestPdfStrings.1";

		String s1 = Messages.getString(b1);
		String s2 = Messages.getString("TestPdfStrings.2") + df.format(percent) + Messages.getString("TestPdfStrings.3"); //$NON-NLS-1$ //$NON-NLS-2$

		System.out.println(s1);
		System.out.println(s2);
		System.out.println(String.format(s1, df.format(percent)));
	}
	
	public static void main(String args[]){
		TestPdfStrings test = new TestPdfStrings();
		
		test.page1();
		
	}
}
