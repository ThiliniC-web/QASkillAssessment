package com.ebay.qa.pages;

import com.ebay.qa.utils.Waits;
import com.microsoft.playwright.Page;

public class SearchPage {

    private final Page page;

    // Locators
    private static final String SEARCH_TEXTBOX  = "#gh-ac";
    private static final String SEARCH_BUTTON  = "#gh-search-btn";
    private static final String DISMISS_POPUP  = "//button[@aria-label='Dismiss']";
    private static final String SEARCH_RESULTS = "//ul[contains(@class,'srp-results')]";
    private static final String PRODUCT_LINK   = "//a[contains(@class,'s-card__link')]";

    public SearchPage(Page page) {
        this.page = page;
    }

    // Close popup if it appears
    public void closePopupIfPresent() {
        try {
            page.waitForSelector(DISMISS_POPUP,
                    new Page.WaitForSelectorOptions().setTimeout(3000));
            page.locator(DISMISS_POPUP).click();
            System.out.println("Popup closed");
        } catch (Exception e) {
            System.out.println("No popup, continuing...");
        }
    }

    // Search for a product
    public void searchForProduct(String product) {
        page.locator(SEARCH_TEXTBOX).fill(product);
        closePopupIfPresent();
        page.locator(SEARCH_BUTTON).click();
        page.waitForSelector(SEARCH_RESULTS,
                new Page.WaitForSelectorOptions().setTimeout(60000));
        Waits.waitForPageLoad(page);
        System.out.println("Searched for " + product + " successfully");
    }

    // Get first product URL from results
    public String getFirstProductUrl() {
        return page.locator(SEARCH_RESULTS)
                .locator(PRODUCT_LINK)
                .nth(0)
                .getAttribute("href");
    }
}
