package screenshot;

import java.io.File;
import java.io.FileOutputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ScreenshotsTest {

	WebDriver driver;
	Context context;
	Page page;

	@BeforeTest
	@Parameters({ "browserName" })
	public void setUp(@Optional("CHROME") String browser) {
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

	@SuppressWarnings("deprecation")
	@Test
	public void TC_01() throws Exception {
		System.out.println("Thread Info : ID - " + Thread.currentThread().getId());
		page = new Page(driver);
		context = new Context();
		context.setContext(Constants.BASE_URL, "https://www.w3schools.com/sql/");
		String URL = (String) context.getcontext(Constants.BASE_URL);
		driver.get(URL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement td_Home = driver.findElement(By.xpath("//a[@id='navbtn_exercises']"));
		td_Home.click();
		getScreenshotUsing_FILE("Image-1");
		getScreenshotUsing_BYTES("Image-2");
		getScreenshotUsing_BASE64("Image-3");
	}

	public String getUniqueDateTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String pattern = localDateTime.format(formatter).replaceAll("\\s+", "-").replaceAll(":", "-");
		return pattern;
	}

	public void getScreenshotUsing_FILE(String fileName) throws Exception {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src,
				new File("./target/Screenshots/File/" + "/" + fileName + "-" + UUID.randomUUID() + ".png"));
	}

	public void getScreenshotUsing_BYTES(String fileName) throws Exception {
		byte[] src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		System.out.println(src);
		FileOutputStream fos = new FileOutputStream(new File("./target/Screenshots/Bytes/" + fileName + "-"
				+ getUniqueDateTime() + "-" + UUID.randomUUID() + ".jpeg"));
		fos.write(src);
		fos.close();
	}

	public void getScreenshotUsing_BASE64(String fileName) throws Exception {

		String src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		byte[] img = Base64.getDecoder().decode(src);
		FileOutputStream fos = new FileOutputStream(new File("./target/Screenshots/Base64/" + fileName + "-"
				+ getUniqueDateTime() + "-" + UUID.randomUUID() + ".jpg"));
		fos.write(img);
		fos.close();
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
