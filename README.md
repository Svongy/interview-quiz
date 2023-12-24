# Interview Quiz
Have You recently experienced a layoff and are actively seeking a new job?\
Are You considering a transition from manual to automation QA but lack confidence in your current knowledge?\
Do You wish to enhance your skills and become proficient in this field?

If you've answered 'Yes' to any of these questions, this service is designed for you. 
It contains an extensive array of commonly asked interview questions, each accompanied by detailed answers, specifically
curated to support your journey.

## Short preview
[![INTERVIEW QUIZ: PREVIEW](https://i.ytimg.com/vi/d-tAG5Unyw0/hqdefault.jpg)](https://youtu.be/d-tAG5Unyw0)

## Technologies stack 
[![Java-version](https://img.shields.io/badge/Java-17-green.svg)](https://shields.io/)
[![Maven](https://img.shields.io/badge/Maven-blue.svg)](https://shields.io/)
[![Spring](https://img.shields.io/badge/Spring-green.svg)](https://shields.io/)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-HTML-purple.svg)](https://shields.io/)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-CSS-purple.svg)](https://shields.io/)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-JavaScript-purple.svg)](https://shields.io/)

## QuickStart guide
Select the option that suits you best and follow the steps below:

### Use code
1. Go to: [Interview-Quiz page](https://github.com/Svongy/interview-quiz) and clone the code;
2. Open cloned project in your IDE;
   3. **OPTION#1**: find and run `rest-controller/src/main/java/org/iq/Main.java`
   4. **OPTION#2**: execute `mvn clean package` and perform `java -jar rest-controller/target/InterviewQuiz.jar`
5. Open your browser and navigate to: `http://localhost:8080`
6. Follow the instructions in your browser and enjoy an enriching learning experience!
---
### Use assembled for you package
1. Go to: [Releases page](https://github.com/Svongy/interview-quiz/releases) and download the release you prefer, although I recommend using the latest one;
2. Using your Command-line interface (CLI) execute: `java -jar InterviewQuiz.jar`
3. Open your browser and navigate to: `http://localhost:8080`
4. Follow the instructions in your browser and enjoy an enriching learning experience!

> **Note:** If port 8080 is unavailable, you can modify it anytime in\
> `rest-controller/src/main/resources/application.properties` or via CLI command: `java -jar InterviewQuiz.jar --server.port=XXXX`.

## Nearest plans
- Expand the size of the questions bank.
- Introduce service containerization to streamline service launch and enable Docker support.
- Redesign the webpage for a more sophisticated appearance.

## Service description
Please review the following details before commencing work with service.

### Source Questions list
[Questions](https://docs.google.com/spreadsheets/d/1tTxdiZmLXSZoEafm-QHPNIcVAW11ahjw-JMwGvaTPPE/edit?usp=sharing)
here is the link to a Google Sheets questionnaire. It's set to Read-only for everyone. 
While it's not advisable, if you find it convenient, you can directly use it without using the service.
You can change the link to Google Sheets in properties file: `data-processor/src/main/resources/iq_config.properties`
Please note the specific symbols used for proper text formatting:

| In text symbols | HTML representation     | Description                  |
|:---------------:|-------------------------|------------------------------|
|      `[*`       | `<span class='bold'>`   | Begin of **bold** text block |
|      `*]`       | `</span>`               | End of **bold** text block   |
|      `/*`       | `<span class='italic'>` | Begin of *italic* text block |
|      `*/`       | `</span>`               | End of *italic* text block   |
|      `//`       | `<br>`                  | End of line                  |


### Questions structure
Each question should have the following structure in order to be successfully red and displayed:

1. **Column name:** type\
**Description:** Identifies question type as either simple TEXT, CODE, SINGLE answer (radio), or MULTI (checkbox)\
**Possible values:** *TEXT (default) | CODE | SINGLE | MULTI*\
**Importance:** Required
2. **Column name:** question\
**Description:** Represents the actual question being asked\
**Possible values:** *Any text*\
**Importance:** Required
3. **Column name:** topic\
**Description:** High-level category for the question, often reflecting a knowledge area (e.g., Databases, Testing Theory, etc.)\
**Possible values:** *Any text*\
**Importance:** Required
   > **Note:** It's advisable to maintain a concise list of topics, as more precise filtering can be achieved using tags and competencies.
4. **Column name:** tags\
**Description:** List up to three tags for precise question identification. Each tag should be in a separate column\
**Possible values:** *Any text*\
**Importance:** Optional
   > **Note:** As tags are not mandatory, you may leave one, two, or even all three columns empty.
5. **Column name:** competency\
**Description:** The level of expertise or knowledge needed to respond to the question\
**Possible values:** *Junior (default) | Middle | Senior*\
**Importance:** Optional
   > **Note:** Even though competency is optional, it defaults to Junior. It's strongly advised to set it accurately for better categorization.
6. **Column name:** a | b | c | d\
**Description:** Possible answer options for questions with types SINGLE or MULTI.\
**Possible values:** *Any text*\
**Importance:** Required (for SINGLE, MULTI question type)
   > **Note:** Exclude option letters in the answer, as they will be automatically added. A minimum of two options is necessary for functionality, though including all four is recommended for an enhanced user experience. 
7. **Column name:** answer\
**Description:** Contains plain text for TEXT questions or letters: a); b); c); d) (separated by semicolons) for SINGLE or MULTI.\
**Possible values:** *Any text or a); b) c); d)*\
**Importance:** Optional
   > **Note:** Although the answer field is optional, it's highly recommended to provide it in most cases. Ensure that for SINGLE and MULTI options, the format for answers follows: a); b); c); d). Currently, CODE questions do not require answers.