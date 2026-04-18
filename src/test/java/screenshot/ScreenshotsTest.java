package screenshot;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ScreenshotsTest {

	WebDriver driver;
	Context context;
	Page page;
	ExtentSparkReporter sparkReporter;
	ExtentReports report;
	ExtentTest test;

	@BeforeSuite
	public void reportInitialization() {
		sparkReporter = new ExtentSparkReporter("./target/Reports/ExtentReport-" + getUniqueDateTime() + ".html");
		sparkReporter.config().setDocumentTitle("Automation Report");
		sparkReporter.config().setReportName("Screenshots - Automation Reports");
		sparkReporter.config().setTheme(Theme.STANDARD);
		report = new ExtentReports();
		report.attachReporter(sparkReporter);
		report.setSystemInfo("User", "DELL");
		report.setSystemInfo("Environment", "TEST");
		report.setSystemInfo("OS", "Window");
		report.setSystemInfo("Browser Name", "DELL");
	}

	@BeforeMethod
	@Parameters({ "browserName" })
	public void setUp(@Optional("CHROME") String browser, Method method) {
		switch (browser) {
		case "CHROME":
			driver = new ChromeDriver();
			break;
		case "FIREFOX":
			driver = new FirefoxDriver();
			break;
		case "EDGE":
			driver = new EdgeDriver();
			break;
		default:
			driver = new ChromeDriver();
		}
	}

	@Test
	public void TC_W3Schools_Navigation_01(Method method) throws Exception {
		test = report.createTest(method.getName());
		test.log(Status.INFO, "Initializing the elements");
		page = new Page(driver);
		context = new Context();
		test.log(Status.INFO, "Navigating to <https://www.w3schools.com/sql/>");
		context.setContext(Constants.BASE_URL, "https://www.w3schools.com/sql/");
		String URL = (String) context.getcontext(Constants.BASE_URL);
		driver.get(URL);
		test.log(Status.INFO, "Maximizing window");
		driver.manage().window().maximize();
		test.log(Status.INFO, "Applying imclicit wait of 10 sec");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement td_Home = driver.findElement(By.xpath("//a[@id='navbtn_exercises']"));
		highlighElement(td_Home);
		test.log(Status.INFO, MediaEntityBuilder
				.createScreenCaptureFromBase64String(getScreenshotUsing_BASE64("Image-3"), "Image - Description")
				.build());
		test.log(Status.INFO, MediaEntityBuilder
				.createScreenCaptureFromPath(getScreenshotUsing_FILE("Image-3"), "Image - Description").build());
		test.log(Status.INFO, MediaEntityBuilder
				.createScreenCaptureFromPath(getScreenshotUsing_BYTES("Image-3"), "Image - Description").build());
		test.log(Status.INFO, "Clicking element");
		test.log(Status.INFO, "Clicking element");
		td_Home.click();
		test.log(Status.INFO, "Taking screenshot of the web element");
	}

	@Test
	public void TC_W3Schools_Navigation_02(Method method) throws Exception {
		test = report.createTest(method.getName());
		test.log(Status.INFO, "Initializing the elements");
		page = new Page(driver);
		context = new Context();
		test.log(Status.INFO, "Navigating to <https://www.w3schools.com/sql/>");
		context.setContext(Constants.BASE_URL, "https://www.w3schools.com/sql/");
		String URL = (String) context.getcontext(Constants.BASE_URL);
		driver.get(URL);
		test.log(Status.INFO, "Maximizing window");
		driver.manage().window().maximize();
		test.log(Status.INFO, "Applying imclicit wait of 10 sec");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement td_Home = driver.findElement(By.xpath("//*[@id=\"subtopnav\"]/a[5]"));
		highlighElement(td_Home);
		test.log(Status.INFO, MediaEntityBuilder
				.createScreenCaptureFromBase64String(getScreenshotUsing_BASE64("Image-3"), "Image - Description")
				.build());
		test.log(Status.INFO, MediaEntityBuilder
				.createScreenCaptureFromPath(getScreenshotUsing_FILE("Image-3"), "Image - Description").build());
		test.log(Status.INFO, MediaEntityBuilder
				.createScreenCaptureFromPath(getScreenshotUsing_BYTES("Image-3"), "Image - Description").build());
		test.log(Status.INFO, "Clicking element");
		td_Home.click();
		test.log(Status.INFO, "Taking screenshot of the web element");
	}

	public String getUniqueDateTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String pattern = localDateTime.format(formatter).replaceAll("\\s+", "-").replaceAll(":", "-");
		return pattern;
	}

	public void highlighElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='8px solid red';", element);
	}

	public String getScreenshotUsing_FILE(String fileName) throws Exception {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File dest = new File("./target/Screenshots/File/" + "/" + fileName + "-" + UUID.randomUUID() + ".png");
		FileUtils.copyFile(src, dest);
		return dest.getAbsolutePath();
	}

	public String getScreenshotUsing_BYTES(String fileName) throws Exception {
		byte[] src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		File file = new File("./target/Screenshots/Bytes/" + fileName + "-" + getUniqueDateTime() + "-"
				+ UUID.randomUUID() + ".jpeg");
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(src);
		fos.close();
		return file.getAbsolutePath();
	}

	public String getScreenshotUsing_BASE64(String fileName) throws Exception {

		String base64Code = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		byte[] img = Base64.getDecoder().decode(base64Code);
		FileOutputStream fos = new FileOutputStream(new File("./target/Screenshots/Base64/" + fileName + "-"
				+ getUniqueDateTime() + "-" + UUID.randomUUID() + ".jpg"));
		fos.write(img);
		fos.close();
		return base64Code;
	}

	@AfterMethod
	public void afterMethod(ITestResult result) throws Exception {

		switch ((result.getStatus())) {
		case ITestResult.SUCCESS:
			test.log(Status.PASS, "Test case passed :" + result.getName());
			test.addScreenCaptureFromBase64String(getScreenshotUsing_BASE64("Image-3"), "Image - Description");
			test.addScreenCaptureFromPath(getScreenshotUsing_FILE("Image-1"), "Image - Description");
			test.addScreenCaptureFromPath(getScreenshotUsing_BYTES("Image-3"), "Image - Description");
			test.pass(MediaEntityBuilder
					.createScreenCaptureFromBase64String(getScreenshotUsing_BASE64("Image-3"), "Image - Description")
					.build());
			test.pass(MediaEntityBuilder
					.createScreenCaptureFromPath(getScreenshotUsing_FILE("Image-3"), "Image - Description").build());
			test.pass(MediaEntityBuilder
					.createScreenCaptureFromPath(getScreenshotUsing_BYTES("Image-3"), "Image - Description").build());
			break;
		case ITestResult.FAILURE:
			test.log(Status.FAIL, "Test case failed :" + result.getName());
			test.addScreenCaptureFromBase64String(getScreenshotUsing_BASE64("Image-3"), "Image - Description");
			test.addScreenCaptureFromPath(getScreenshotUsing_FILE("Image-1"), "Image - Description");
			test.addScreenCaptureFromPath(getScreenshotUsing_BYTES("Image-3"), "Image - Description");
			break;
		case ITestResult.SKIP:
			test.log(Status.SKIP, "Test case skipped :" + result.getName());
			test.addScreenCaptureFromBase64String(getScreenshotUsing_BASE64("Image-3"), "Image - Description");
			test.addScreenCaptureFromPath(getScreenshotUsing_FILE("Image-1"), "Image - Description");
			test.addScreenCaptureFromPath(getScreenshotUsing_BYTES("Image-3"), "Image - Description");
			break;
		default:
			test.log(Status.INFO, "Test case status unknown :" + result.getName());
		}
		driver.quit();
	}

	@AfterSuite
	public void reportEnd() {
		report.flush();
	}
}
