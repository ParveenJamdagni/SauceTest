package loginOCD;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.io.FileNotFoundException;
//import com.csvreader.CsvReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;





import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.bytecode.opencsv.CSVReader;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;



public class OCD_Login {	
	
	
	static int executionFlag=0;
	public static void main(String[] args) throws FileNotFoundException{
		// boolean artifactFlag=false;
		
		long lastModificationTime=0;
		
		
		do{
			File file = new File("D:\\build_Version\\build_Version.csv");
			System.out.println("time----->"+file.lastModified());
			long currentFileTime=(long) file.lastModified();
			System.out.println("currentFileTime : " + currentFileTime);
			
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

				System.out.println("After Format : " + sdf.format(file.lastModified()));
			if(currentFileTime>lastModificationTime){
				System.out.println("Current File Time--->"+currentFileTime);
				System.out.println("fileModifiedValue---------->"+lastModificationTime);	
				
				
				callOCD();
				lastModificationTime=currentFileTime;
			}
			else{
				System.out.println("******File time is same not calling OCD*****");
				
			}
		}while(executionFlag==0);
		
}
	
private static void callOCD()
{

	 CSVReader reader = null;
	 String artifactValue=null;
	 String driverPath="D:\\Drivers\\IEDriverServer_Win32_3.3.0\\";
	 WebDriver driver;
	 System.setProperty("webdriver.ie.driver", driverPath+"IEDriverServer.exe");
	 driver = new InternetExplorerDriver();
	 driver.manage().window().maximize();
	 driver.navigate().to("https://10.13.111.195/svc/oneclick/index.jsp");
	 String strPageTitle = driver.getTitle();
	 System.out.println("Page title: - "+strPageTitle);
	 // Providing username & password.
	 driver.findElement(By.name("userName")).sendKeys("nrtnoida");
	 driver.findElement(By.name("password")).clear();
	 driver.findElement(By.name("password")).sendKeys("123");
	 //driver.findElement(By.name("password")).submit();
	 // Submitting form
	 driver.findElement(By.xpath("/html/body/form/table[2]/tbody/tr/td[1]/input")).submit();
	// WebDriverWait myWatVar=new WebDriverWait(driver,10);
	// System.out.println("Page title1111: - "+strPageTitle);
	 driver.findElement(By.xpath("/html/body/p[2]/table/tbody/tr[7]/td/input")).click();
	 
	 /* ************  Comenting allllll     Reading values from excel sheet */
	 
	 
	try {
		reader = new CSVReader(new FileReader("D:\\build_Version\\build_Version.csv"));
	} catch (FileNotFoundException e1) {
		e1.printStackTrace();
	}

	 //load content into list
	 List<String[]> li = null;
	try {
		li = reader.readAll();
		System.out.println("values"+li.toString());
		System.out.println("Total rows which we have is"+li.size());
	} catch (IOException e) {
		e.printStackTrace();
	}
	// create Iterator reference
	  Iterator<String[]>i1= li.iterator();
	    
	 // Iterate all values 
	 while(i1.hasNext()){
	     
	 String[] str=i1.next();
	   
	 System.out.print(" Values are ");
	 
	 for(int i=0;i<str.length;i++)
	{
	 
	   System.out.print(" "+str[i]);
	   artifactValue=str[i];
	 
	}
	   System.out.println(" artifactValue---------->  "+artifactValue);
	     
	    
	}
	
	/* Checking latest ISO is already present in available artifacts or not.*/
	 boolean availableISO=driver.getPageSource().contains(artifactValue);
	 System.out.println("availableISO---------->"+availableISO);
	 
	 
	 if(availableISO==true)
	 {
		 System.out.println("availableISO---is present , no need to import------->"+availableISO);
		 // Moving back to main screen.
		 waitAdded();
		 driver.findElement(By.xpath("/html/body/form/input")).submit();
		
	 }
	 
	 else
	 {
		 System.out.println("availableISO----not present, need to import------>"+availableISO);
	
		 // Applying wait time of 2s on screen
		 waitAdded();
			
		 /*    ***************importing latest ISO*****************************  */
		 // Moving back to main screen.
		 waitAdded();
		 driver.findElement(By.xpath("/html/body/form/input")).submit();
		
		 // Applying wait time of 2s on screen
		 waitAdded();
		 
		 // selecting import artifact
		 driver.findElement(By.xpath("/html/body/p[2]/table/tbody/tr[6]/td/input")).click();
		 
		 // import iso url for noida repo
		 String isoURL="http://noidarepo.techmipc.com/builds/int_nightly_unigy_full/"+artifactValue+"/appliance/obj/iso/dunkin_"+artifactValue+".iso";
		 System.out.println("URL------>"+isoURL);
		 waitAdded();
		 // Providing ISO value to URL
		 
		 WebElement uploadElement = driver.findElement(By.name("appISOVersion"));
		 uploadElement.sendKeys(isoURL);
		 
		 //driver.findElement(By.name("appISOVersion")).sendKeys(isoURL);
	 
		driver.findElement(By.xpath("/html/body/form/table[2]/tbody/tr/td[2]/input")).submit();
	 
		 // Applying wait time of 2s on screen
		waitAdded();
	 
		 System.out.println("moving to main menu");
		 driver.findElement(By.xpath("/html/body/form/table[2]/tbody/tr/td[1]/input")).click();
		
									
	 }

/* ****************************Initiating recop of the Enterprise************************************* */
	 
driver.findElement(By.xpath("/html/body/p[2]/table/tbody/tr[1]/td/input")).click();

//Selecting all servers

driver.findElement(By.xpath("/html/body/form/table/tbody/tr[1]/th[6]/input")).click();

// Moving to next page.

driver.findElement(By.xpath("/html/body/form/table/tbody/tr[7]/td[2]/input")).submit();

/* ***********************Configuring Enterprise attributes.******************************************/

waitAdded();
//Configuring Task valueBasicPlanSelectAll
Select taskValue=new Select(driver.findElement(By.xpath("/html/body/form[2]/table[2]/tbody/tr[2]/td[3]/select")));
taskValue.selectByVisibleText("Recop");
waitAdded();
//Configuring COP ISO
Select copISO=new Select(driver.findElement(By.name("RECOPBuildALL")));
copISO.selectByVisibleText("03.00.00.00.1272");
waitAdded();
//Configure install app ISO
driver.findElement(By.name("MDBuildAll")).sendKeys(artifactValue);
driver.findElement(By.xpath("/html/body/form[2]/table[2]/tbody/tr[2]/td[7]/input[1]")).click(); 
waitAdded();
//Configure Zone Deploy & data source.
driver.findElement(By.xpath("/html/body/form[2]/table[2]/tbody/tr[2]/td[8]/input[1]")).click();
waitAdded();
//Configure Prefered Zone.
driver.findElement(By.xpath("/html/body/form[2]/table[2]/tbody/tr[2]/td[10]/input[1]")).click();
waitAdded();
//Configure Enterprise license for Z1
Select ccm1Z1=new Select(driver.findElement(By.id("License_URL_2")));
ccm1Z1.selectByVisibleText("V4_Enterprise_License");
Select ccm2Z1=new Select(driver.findElement(By.id("License_URL_4")));
ccm2Z1.selectByVisibleText("V4_Enterprise_License");

//User making Active Enterprise Plan
driver.findElement(By.name("ActivePlanSelectALL")).click();
waitAdded();

//Submitting Enterprise Plan
driver.findElement(By.xpath("/html/body/form[2]/table[3]/tbody/tr/td[2]/input")).submit();



WebDriverWait wait = new WebDriverWait(driver, 15, 100);
wait.until(ExpectedConditions.alertIsPresent());
// Switching to Alert        
Alert alert=driver.switchTo().alert();	

// Capturing alert message.    
String alertMessage=driver.switchTo().alert().getText();		
		
// Displaying alert message		
System.out.println(alertMessage);	

// Accepting alert		
//alert.accept();
alert.dismiss();

driver.quit();

executionFlag=1;



}
private static void waitAdded()
{
	 try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
}


}
