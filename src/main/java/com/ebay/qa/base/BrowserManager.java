package com.ebay.qa.base;

import com.microsoft.playwright.*;
import java.util.List;

public class BrowserManager {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    public void setUp() {
        playwright = Playwright.create();

        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(500)
                .setArgs(List.of("--start-maximized"))
        );

        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(null)
                .setLocale("en-US")
                .setTimezoneId("America/New_York")
        );

        page = context.newPage();
    }

    public void tearDown() {
        page.close();
        context.close();
        browser.close();
        playwright.close();
    }
}