# AI Test Failure Intelligence Platform

An intelligent QA automation platform that executes Selenium tests, automatically analyzes failed test cases, identifies the possible reasons for failure, and presents results through visual reports and a live dashboard - reducing manual debugging effort for testers.

## What This Project Does

Instead of manually digging through screenshots, logs, and reports after every test run, this platform automatically:

- Executes automated Selenium test cases
- Captures a screenshot the moment any test fails
- Saves a detailed error log (exception type, message, stack trace)
- Classifies why the test failed (Timeout, Element Not Found, Assertion Failed, etc.)
- Generates a polished HTML report (Extent Reports) with embedded failure screenshots
- Logs every test run to a CSV file for historical tracking
- Visualizes pass/fail rates and failure trends in a live Python/Streamlit dashboard

## Tech Stack

- Java - core language
- Selenium WebDriver - browser automation
- TestNG - test execution framework and listeners
- Maven - build and dependency management
- Extent Reports - HTML test reporting
- Python + Streamlit + pandas - analytics dashboard

## How It Works

1. Selenium tests run under TestNG.
2. A custom TestNG listener (FailureListener) watches every test.
3. When a test fails, the listener automatically captures a screenshot, writes an error log, and classifies the failure reason.
4. Every result (pass or fail) is also appended to a CSV file for history tracking.
5. An Extent Report (HTML) is generated showing all results with embedded screenshots.
6. A separate Python/Streamlit dashboard reads the CSV and displays pass/fail stats and failure trend charts.

## How Failure Classification Works

The FailureAnalyzer class reads the exception type thrown by Selenium/TestNG and maps it to a human-readable category, for example:

- NoSuchElementException -> Element Not Found
- TimeoutException -> Timeout
- AssertionError -> Assertion Failed (Data Mismatch)
- StaleElementReferenceException -> Page Changed / Stale Element

## How to Run

1. Clone this repository.
2. Open in Eclipse as an existing Maven project.
3. Run FirstTest.java as a TestNG Test.
4. View the generated report at reports/TestReport.html
5. To view the dashboard, navigate to the Dashboard folder and run:

python -m streamlit run dashboard.py

## Screenshots

### Streamlit Dashboard
<img width="1912" height="968" alt="Screenshot 2026-07-27 220629" src="https://github.com/user-attachments/assets/63df2581-e0c7-41ba-9248-14630e3f5a62" />
<img width="1908" height="962" alt="Screenshot 2026-07-27 220641" src="https://github.com/user-attachments/assets/070841b8-373b-449d-ba53-624125c755a3" />

### Extent Report (Failure with Screenshot)
<img width="1905" height="897" alt="Screenshot 2026-07-27 220824" src="https://github.com/user-attachments/assets/38eeff89-4b83-4186-8323-b3fd236df95b" />
<img width="1917" height="912" alt="Screenshot 2026-07-27 220831" src="https://github.com/user-attachments/assets/a901be2c-b610-4553-a418-f95f3bf31544" />


## Future Improvements

- Store results in MySQL instead of CSV for larger-scale history
- Add a true ML-based failure classifier trained on historical failure data
- Add Slack/email notifications on test failure

## Author

Shubhavi M
[GitHub](https://github.com/Shubhavi17) | [LinkedIn](https://www.linkedin.com/in/shubhavi/)
