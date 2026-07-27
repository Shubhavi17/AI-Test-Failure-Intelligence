package com.qa.testintel.analyzer;

public class FailureAnalyzer {

    public static String getFailureReason(Throwable error) {
        if (error == null) {
            return "Unknown";
        }

        String exceptionName = error.getClass().getSimpleName();

        switch (exceptionName) {
            case "NoSuchElementException":
                return "Element Not Found";

            case "TimeoutException":
                return "Timeout";

            case "ElementClickInterceptedException":
                return "Element Blocked / Not Clickable";

            case "StaleElementReferenceException":
                return "Page Changed / Stale Element";

            case "ElementNotInteractableException":
                return "Element Not Interactable";

            case "AssertionError":
                return "Assertion Failed (Data Mismatch)";

            case "NoSuchWindowException":
                return "Browser Window Not Found";

            default:
                return "Other / Unclassified (" + exceptionName + ")";
        }
    }
}