
# planTUM - Academic Study Planner for TUM Informatics

planTUM is a comprehensive study planning application developed during HackaTUM 2025. It helps students at the Technical University of Munich (TUM) plan their Master's degree in Computer Science by visualizing course schedules, tracking ECTS credits, and managing study requirements across different semesters and specializations.

## Project Overview

This application addresses the complexity of planning a Master's degree in Computer Science at TUM, where students must navigate various course categories (Profile, Electives, Soft Skills, Practical Courses, etc.) and specialization requirements (Schwerpunkt and Nebenschwerpunkte).

### Key Features

- **Interactive Semester Planning**: Visual drag-and-drop interface to organize courses across multiple semesters
- **Credit Tracking**: Real-time ECTS credit calculation with detailed breakdowns by category and specialization
- **Specialization Management**: Track progress towards main and minor specializations with visual progress indicators
- **Multi-language Support**: Available in German and English
- **TUM Integration**: Direct links to TUMonline, Moodle, TUM Live, and other university platforms
- **User Authentication**: Secure login system with user profiles
- **Responsive Dashboard**: Modern, user-friendly interface with TUM branding

## Technical Architecture

This is a Kotlin Multiplatform project targeting Web, Desktop (JVM), Server.

## HackaTUM 2024 - Hackathon Details

### Inspiration

As Computer Science Master's students at TUM, we experienced firsthand the complexity and confusion of planning our studies. With numerous course categories, specialization requirements, and ECTS credit constraints, it's easy to lose track of what courses to take and when. Many students use spreadsheets or paper notes, but these methods are error-prone and don't provide real-time feedback. We wanted to create a tool that makes study planning intuitive, visual, and stress-free.

### What it does

planTUM is an intelligent study planning assistant that:
- Allows students to visually organize courses across semesters using an interactive interface
- Automatically calculates and tracks ECTS credits across all required categories (Profile, Electives, Soft Skills, Practical Courses, Thesis, etc.)
- Monitors progress towards specialization requirements (Schwerpunkt: 18 ECTS, Nebenschwerpunkte: 8 ECTS each)
- Provides visual feedback with color-coded progress bars showing completion status
- Supports both German and English interfaces for international students
- Integrates with TUM's ecosystem through quick links to TUMonline, Moodle, and TUM Live

### How we built it

We built planTUM using modern technologies optimized for cross-platform development:
- **Kotlin Multiplatform**: Enables code sharing across Web, Desktop, and Server platforms
- **Compose Multiplatform**: Provides a modern, reactive UI framework with declarative components
- **Ktor**: Powers our backend server for user authentication and data persistence
- **MongoDB**: Stores user profiles, study plans, and course data
- **Kotlin Serialization**: Handles data serialization across client-server communication
- **WebAssembly (Wasm)**: Delivers fast, native-like performance in the browser

The application follows a clean architecture with separated concerns:
- `shared/` module contains common data models (User, Module, Semester, etc.)
- `composeApp/` contains the UI layer with reusable Compose components
- `server/` provides RESTful APIs for authentication and data management

### Challenges we ran into

- **Multi-language support**: Implementing a robust localization system that dynamically updates all UI components when the language changes required careful state management and the creation of custom localization utilities.
- **Complex credit calculation logic**: Tracking ECTS credits across multiple categories and specializations with various rules (e.g., certain courses count towards both categories and specializations) required meticulous algorithms and extensive testing.
- **State synchronization**: Ensuring that changes in one part of the UI (e.g., adding a course) immediately update all dependent views (credit summaries, progress bars) required careful state management with Kotlin Flows.
- **TUM branding and UX**: Creating a professional, polished interface that aligns with TUM's visual identity while maintaining usability took multiple design iterations.

### Accomplishments that we're proud of

- Created an application in just 36 hours
- Implemented a complex domain model that accurately represents TUM's Master's program structure
- Built an intuitive, visually appealing interface that makes study planning enjoyable
- Achieved cross-platform support - the same codebase runs on Web and Desktop
- Developed a sophisticated credit calculation system
- Implemented comprehensive internationalization supporting German and English
- Integrated authentication and persistent storage for user data

### What we learned

- **Kotlin Multiplatform ecosystem**: Gained deep expertise in sharing code across platforms and managing platform-specific implementations
- **Compose Multiplatform**: Mastered building complex, interactive UIs with Jetpack Compose for multiple targets
- **Domain modeling**: Learned to translate complex academic requirements into clean, maintainable code structures
- **Real-time state management**: Improved skills in managing reactive state with Kotlin Flows and Compose State
- **Localization best practices**: Discovered effective patterns for implementing multi-language support in Compose applications
- **Team collaboration**: Enhanced our ability to work effectively under tight deadlines, dividing tasks and integrating components seamlessly

### What's next for planTUM

- **Course recommendations**: Implement an AI-powered recommendation system that suggests courses based on interests, previous courses, and career goals
- **Prerequisite checking**: Automatically warn students if they try to add a course without completing its prerequisites
- **Import from TUMonline**: Allow students to import their current course selections and grades directly from TUMonline
- **Collaborative planning**: Enable students to share plans with peers and study advisors for feedback
- **Calendar integration**: Export study plans to calendar applications with course times and locations
- **Mobile app**: Develop native iOS and Android apps for on-the-go planning
- **Analytics dashboard**: Provide insights on study progress, time distribution, and workload balance
- **Export functionality**: Generate PDF reports of study plans for administrative purposes
- **Bachelor's program support**: Extend the application to support Bachelor's degree planning as well
- **Other universities**: Adapt the platform for use at other German universities with similar credit systems

## Running the Project

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
    folder is the appropriate location.

* [/server](./server/src/main/kotlin) is for the Ktor server application.

* [/shared](./shared/src) is for the code that will be shared between all targets in the project.
  The most important subfolder is [commonMain](./shared/src/commonMain/kotlin). If preferred, you
  can add code to the platform-specific folders here too.

### Build and Run Desktop (JVM) Application

To build and run the development version of the desktop app, use the run configuration from the run widget
in your IDE’s toolbar or run it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:run
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:run
  ```

### Build and Run Server

To build and run the development version of the server, use the run configuration from the run widget
in your IDE’s toolbar or run it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :server:run
  ```
- on Windows
  ```shell
  .\gradlew.bat :server:run
  ```

### Build and Run Web Application

To build and run the development version of the web app, use the run configuration from the run widget
in your IDE's toolbar or run it directly from the terminal:
- for the Wasm target (faster, modern browsers):
  - on macOS/Linux
    ```shell
    ./gradlew :composeApp:wasmJsBrowserDevelopmentRun
    ```
  - on Windows
    ```shell
    .\gradlew.bat :composeApp:wasmJsBrowserDevelopmentRun
    ```
- for the JS target (slower, supports older browsers):
  - on macOS/Linux
    ```shell
    ./gradlew :composeApp:jsBrowserDevelopmentRun
    ```
  - on Windows
    ```shell
    .\gradlew.bat :composeApp:jsBrowserDevelopmentRun
    ```

---