# QASkillAssessment — eBay Main Products and Related Products Automation Framework
---

## Project Structure

```
QASkillAssessment/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/ebay/qa/
│   │           ├── base/
│   │           │   └── BrowserManager.java      ← Browser setup and teardown
│   │           ├── pages/
│   │           │   ├── SearchPage.java           ← Page Object for eBay search
│   │           │   └── ProductPage.java          ← Page Object for product detail page
│   │           └── utils/
│   │               ├── ConfigReader.java         ← Base URL, timeouts, constants
│   │               └── WaitUtils.java            ← Shared wait and scroll methods
│   └── test/
│       └── java/
│           └── com/ebay/qa/tests/
│               ├── EbayMainTest.java             ← Positive test cases
│               └── EbayNegativeTest.java         ← Negative test cases
├── src/test/resources/
│   └── testng.xml                               ← TestNG suite configuration
├── pom.xml                                      ← Maven dependencies
└── README.md
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

## Run Tests

# Run full suite
mvn test

# Run only positive tests
mvn test -Dtest="EbayMainTest"

# Run only negative tests
mvn test -Dtest="EbayNegativeTest"

---

## Notes
setHeadless(false) — browser is visible during test execution


## Author
QA Engineer — eBay Related Products Skills Assessment