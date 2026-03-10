package com.ebay.qa.utils;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public class Waits {

    // Wait for page to load
    public static void waitForPageLoad(Page page) {
        page.waitForLoadState(LoadState.LOAD);
    }

    // Scroll to bottom gradually to trigger lazy loading
    public static void scrollToBottom(Page page) {
        for (int i = 1; i <= 5; i++) {
            page.evaluate("window.scrollTo(0, document.body.scrollHeight * " + (i * 0.2) + ")");
            page.waitForTimeout(500);
        }
    }

}
