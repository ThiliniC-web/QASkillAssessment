package com.ebay.qa.pages;

import com.ebay.qa.utils.Waits;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProductPage {

    private final Page page;

    // Locators
    private static final String MAIN_PRICE            = ".x-price-primary .ux-textspans";
    private static final String RELATED_ITEMS_SECTION = "//div[contains(@class,'MHN-')]";
    private static final String RELATED_ITEM          = "//div[contains(@class,'uV_m')]";
    private static final String RELATED_ITEM_PRICE    = ".//span[contains(@class,'s-item__price')]";

    public ProductPage(Page page) {
        this.page = page;
    }

    // Get main product price
    public String getMainPrice() {
        return page.locator(MAIN_PRICE).first().textContent().trim();
    }

    // Scroll to similar items section
    public void scrollToSimilarItems() {
        Waits.scrollToBottom(page);
    }

    // Check if similar items section is visible
    public boolean isSimilarItemsSectionVisible() {
        return page.locator(RELATED_ITEMS_SECTION).first().isVisible();
    }

    // Get all similar items
    public Locator getSimilarItems() {
        return page.locator(RELATED_ITEMS_SECTION).first().locator(RELATED_ITEM);
    }

    // Get count of similar items
    public int getSimilarItemsCount() {
        return getSimilarItems().count();
    }

    // Get price of a similar item by index
    public String getSimilarItemPrice(Locator similarItems, int index) {
        return similarItems.nth(index)
                .locator(RELATED_ITEM_PRICE)
                .first()
                .textContent().trim();
    }
}
