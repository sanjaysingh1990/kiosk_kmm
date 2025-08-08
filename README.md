# KMM Onboarding/Login/Signup Demo

This project demonstrates a Kotlin Multiplatform Mobile (KMM) application with shared business logic and local preferences across Android and iOS.

## Features

- **Shared Module:** Contains domain, data, and business logic, including authentication use cases, validators, and preferences handling.
- **Android App:** Implemented using Jetpack Compose with a single-activity architecture.
- **iOS App:** Implemented using SwiftUI (iOS 15+).
- **Authentication Flow:** Splash screen -> Onboarding -> Login/Signup with input validations and social login buttons (icons only).
- **Local Preferences:** Uses `kotlin-multiplatform-settings` for storing `firstTimeInstall` flag and simple user/session info.
- **Mock Backend:** Simulates login/signup success/failure with in-memory mock repositories.

## Project Structure

- `/shared`: Kotlin Multiplatform module containing common code.
- `/composeApp`: Android application module.
- `/iosApp`: iOS application module.

## How to Build and Run

### Android

1.  **Open in Android Studio:** Open the `Kiosk_kmm` project in Android Studio.
2.  **Sync Gradle:** Let Gradle sync the project dependencies.
3.  **Run:** Select the `composeApp` run configuration and click the 'Run' button or execute the following command in the terminal:

    ```bash
    ./gradlew :composeApp:installDebug
    ```

### iOS

1.  **Open in Xcode:** Navigate to the `iosApp` directory and open the `iosApp.xcworkspace` file:

    ```bash
    open iosApp/iosApp.xcworkspace
    ```

2.  **Build and Run:** Select a simulator or a connected device and click the 'Run' button in Xcode.

### Running Unit Tests (Shared Module)

To run unit tests for the shared module, execute the following command in the project root directory:

```bash
./gradlew :shared:test
```

## Notes on `kotlin-multiplatform-settings`

The `kotlin-multiplatform-settings` library is used for multiplatform preferences. It provides `expect`/`actual` implementations for different platforms.

-   **Android:** Uses `SharedPreferences` under the hood.
-   **iOS:** Uses `NSUserDefaults` under the hood.

The `SettingsFactory` in the `shared` module provides the platform-specific `Settings` instance.