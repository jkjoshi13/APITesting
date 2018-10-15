/**
 * 
 */
package com.veryemp.listners;

import com.relevantcodes.extentreports.ExtentReports;

/**
 * @author Transorg(sachin)
 *
 */
public class ExtentManager {
	 
    static ExtentReports extent;
    final static String filePath = "./test-output/html/Extent.html";
 
    public synchronized static ExtentReports getReporter(){
        if(extent == null){
            //Set HTML reporting file location
            
            extent = new ExtentReports (filePath, true);
        }
        return extent;
    }
}