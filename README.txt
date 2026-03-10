# QASkillAssessment — eBay Main Products and Related Products Automation Framework
---

## Project Structure

```
QASkillAssessment/
├── src/main/java/com/ebay/qa/
│   ├── base/
│   │   └── BrowserManager.java        ← Initializes and manages Playwright browser, context and page
│   ├── pages/
│   │   ├── SearchPage.java            ← Page Object for eBay search — search, popup, get first product URL
│   │   └── ProductPage.java           ← Page Object for product detail page — price, similar items, scroll
│   └── utils/
│       ├── ConfigReader.java          ← Stores constants — base URL, timeout, max/min product count
│       └── WaitUtils.java             ← Reusable wait and scroll methods used across all tests
├── src/test/java/com/ebay/qa/tests/
│   ├── EbayMainTest.java              ← Positive test cases — verifies related products section, count and prices
│   └── EbayNegativeTest.java          ← Negative test cases — empty search, duplicate products, broken links
├── testng.xml                         ← TestNG suite configuration — defines which tests to run
├── pom.xml                            ← Maven dependencies — Playwright, TestNG, Java version
├── README.md                          ← Project documentation — structure, tech stack, how to run tests
└── QA_Skills_Assessment.pdf          ← Manual testing document — test strategy, test cases, bug reports

```

---

## Prerequisites

| Tool | Version |
|------|---------|
| Java JDK       | 25.0.2 |
| Maven          | 3.8+ |
| IntelliJ IDEA  | Latest |
| Playwright     | 1.58.0 |
| TestNG         | 7.12.0 |
| OS             | Windows 11 |

---
## Test Cases

### Positive Tests — EbayMainTest.java

| Method | Description |
|--------|-------------|
| `verifyRelatedProductsOnWalletPage` | Verifies related products section is visible, count is 1-6, each item has valid price |

### Negative Tests — EbayNegativeTest.java

| Method | Description |
|--------|-------------|
| `searchWithEmptyString`            | Verifies empty search shows all categories page |
| `verifyNoDuplicateRelatedProducts` | Verifies no duplicate items in related products list |
| `verifyAllRelatedProductLinksWork` | Verifies all related product links navigate without 404 |

---

## Key Design Decisions

| Decision | Reason |
|----------|--------|
| Page Object Model (POM) | Separates test logic from page interactions |
| ConfigReader            | Single place for all config values — easy to update |
| WaitUtils               | Reusable wait methods — no hardcoded Thread.sleep |

## Test Groups

| Group        | Tests Included                      | Purpose |
|------------- |-------------------------------------|---------|
| `smoke`      | `verifyRelatedProductsOnWalletPage` | Quick sanity check — run after every deployment |
| `regression` | All 4 tests                         | Full regression — run before release |


## Run Tests

# Run full suite
mvn test

# Run only positive tests
mvn test -Dtest="EbayMainTest"

# Run only negative tests
mvn test -Dtest="EbayNegativeTest"

# Run regression tests only
mvn test -Dgroups="regression"

---

## Notes
setHeadless(false) — browser is visible during test execution


## Author
Thilini Chandrasena — eBay Main and Related Products Skills Assessment


