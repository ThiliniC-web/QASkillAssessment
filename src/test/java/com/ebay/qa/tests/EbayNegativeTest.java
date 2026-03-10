package com.ebay.qa.tests;

import com.ebay.qa.base.BrowserManager;
import com.ebay.qa.pages.ProductPage;
import com.ebay.qa.pages.SearchPage;
import com.ebay.qa.utils.ConfigReader;
import com.ebay.qa.utils.Waits;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class EbayNegativeTest extends BrowserManager {

    private SearchPage searchPage;
    private ProductPage productPage;

    @BeforeMethod
    public void setup() {
        setUp();
        page.navigate(ConfigReader.BASE_URL);
        Waits.waitForPageLoad(page);
        searchPage  = new SearchPage(page);
        productPage = new ProductPage(page);
    }

    @Test
    public void searchWithEmptyString() {
        // Close popup if appears
        searchPage.closePopupIfPresent();

        // Search with empty string
        page.locator("#gh-ac").fill("");
        page.locator("#gh-search-btn").click();
        Waits.waitForPageLoad(page);

        // Verify eBay shows all categories page
        String currentUrl = page.url();
        System.out.println("Current URL: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("all-categories"),
                "Empty search should show all categories page!");
        System.out.println("Empty search assertion passed!");
    }

    @Test
    public void verifyNoDuplicateRelatedProducts() {
        // Search and navigate to product page
        searchPage.closePopupIfPresent();
        searchPage.searchForProduct("leather wallet for men");

        String productUrl = searchPage.getFirstProductUrl();
        page.navigate(productUrl);
        Waits.waitForPageLoad(page);

        // Scroll to similar items and wait for them to load
        productPage.scrollToSimilarItems();
        productPage.getSimilarItems()
                .first()
                .waitFor(new Locator.WaitForOptions().setTimeout(ConfigReader.TIMEOUT));

        // Get all similar item titles
        Locator similarItems = productPage.getSimilarItems();
        int count = similarItems.count();
        System.out.println("Similar items count: " + count);

        List<String> titles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String title = similarItems.nth(i)
                    .locator("xpath=.//h3")
                    .first()
                    .textContent().trim();
            System.out.println("Item " + (i + 1) + " title: " + title);
            titles.add(title);
        }

        // Verify no duplicates
        long uniqueCount = titles.stream().distinct().count();
        Assert.assertEquals(uniqueCount, titles.size(),
                "Duplicate related products found!");
        System.out.println("No duplicate related products found!");
    }

    @Test
    public void verifyAllRelatedProductLinksWork() {
        // Search and navigate to product page
        searchPage.closePopupIfPresent();
        searchPage.searchForProduct("leather wallet for men");

        String productUrl = searchPage.getFirstProductUrl();
        page.navigate(productUrl);
        Waits.waitForPageLoad(page);

        // Scroll to similar items and wait for them to load
        productPage.scrollToSimilarItems();
        productPage.getSimilarItems()
                .first()
                .waitFor(new Locator.WaitForOptions().setTimeout(ConfigReader.TIMEOUT));

        // Collect all URLs first before navigating
        Locator similarItems = productPage.getSimilarItems();
        int count = similarItems.count();
        System.out.println("Similar items count: " + count);

        List<String> itemUrls = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String itemUrl = similarItems.nth(i)
                    .locator("xpath=.//a")
                    .first()
                    .getAttribute("href");
            itemUrls.add(itemUrl);
            System.out.println("Collected URL " + (i + 1) + ": " + itemUrl);
        }

        // Navigate to each URL and verify no 404
        for (int i = 0; i < itemUrls.size(); i++) {
            page.navigate(itemUrls.get(i));
            Waits.waitForPageLoad(page);

            String pageTitle = page.title();
            System.out.println("Item " + (i + 1) + " page title: " + pageTitle);

            Assert.assertFalse(pageTitle.contains("404") || pageTitle.contains("Error"),
                    "Item " + (i + 1) + " link is broken!");
            System.out.println("Item " + (i + 1) + " link is working!");
        }
    }

    @AfterMethod
    public void teardown() {
        tearDown();
    }
}
