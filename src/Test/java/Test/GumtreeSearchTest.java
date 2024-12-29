package Test;

import org.testng.annotations.Test;
import Page.OpenPage;

public class GumtreeSearchTest extends BaseTest {
    @Test(testName = "Gumtree Link")
    public void testGumtreeLink() {
        OpenPage openPage = new OpenPage(BaseTest.driver, BaseTest.explicitWait);
    }
}