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

public class EbayMainTest extends BrowserManager {

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

    // Extract price number from price text e.g "US $2,000.00" -> 2000.0
    public double extractPrice(String priceText) {
        if (priceText == null || priceText.isEmpty()) return 0.0;
        String cleaned = priceText.replaceAll("[^0-9.]", "");
        System.out.println("Cleaned price text = " + cleaned);
        if (cleaned.isEmpty()) return 0.0;
        return Double.parseDouble(cleaned);
    }

    @Test
    public void verifyRelatedProductsOnWalletPage() {

        // Step 1 - Search for wallet
        searchPage.closePopupIfPresent();
        searchPage.searchForProduct("leather wallet for men");

        // Step 2 - Navigate to first product
        String productUrl = searchPage.getFirstProductUrl();
        System.out.println("Product URL: " + productUrl);
        page.navigate(productUrl);
        Waits.waitForPageLoad(page);

        // Step 3 - Verify main product price exists
        String mainPriceText = productPage.getMainPrice();
        System.out.println("Main Price Text = " + mainPriceText);
        double mainPrice = extractPrice(mainPriceText);
        System.out.println("Main Product Price = $" + mainPrice);
        Assert.assertTrue(mainPrice > 0, "Main product price should be valid!");
        System.out.println("Main product price is valid!");

        // Step 4 - Scroll to similar items section
        productPage.scrollToSimilarItems();
        Waits.waitForPageLoad(page);

        // Step 5 - Verify similar items section is visible
        Assert.assertTrue(productPage.isSimilarItemsSectionVisible(),
                "Similar items section should be visible!");
        System.out.println("Similar items section is visible!");

        // Step 6 - Verify similar items count is between 1 and 6
        Locator similarItems = productPage.getSimilarItems();
        int count = similarItems.count();
        System.out.println("Similar items count: " + count);
        Assert.assertTrue(count >= 1 && count <= ConfigReader.MAX_RELATED_PRODUCTS,
                "Similar items count should be between 1 and 6 but was: " + count);
        System.out.println("Similar items count is valid!");

        // Step 7 - Verify each similar item has a valid price
        for (int i = 0; i < count; i++) {
            try {
                String itemPriceText = productPage.getSimilarItemPrice(similarItems, i);
                System.out.println("Item " + (i + 1) + " raw price: " + itemPriceText);
                double itemPrice = extractPrice(itemPriceText);
                Assert.assertTrue(itemPrice > 0,
                        "Item " + (i + 1) + " should have a valid price!");
                System.out.println("Item " + (i + 1) + " price: $" + itemPrice);
            } catch (Exception e) {
                System.out.println("Could not get price for item " + (i + 1) + ": " + e.getMessage());
            }
        }
    }

    @AfterMethod
    public void teardown() {
        tearDown();
    }
}
