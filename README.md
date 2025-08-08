This is a Kotlin Multiplatform project targeting Android, iOS.

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
    folder is the appropriate location.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* [/shared](./shared/src) is for the code that will be shared between all targets in the project.
  The most important subfolder is [commonMain](./shared/src/commonMain/kotlin). If preferred, you can add code to the platform-specific folders here too.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

prompt for onboarding flow generation

Project: KMM Onboarding/Login/Signup Demo
Goal: Create a Kotlin Multiplatform Mobile (KMM) project that shares business logic and local prefs across Android and iOS. Android UI uses Jetpack Compose; iOS UI uses SwiftUI. Provide clean architecture: shared module for domain/data/business, platform modules for UI. Implement Splash -> Onboarding -> Auth flow with validations and social login buttons (icons only, no provider wiring). Use multiplatform settings for storing "firstTimeInstall" flag and simple user/session info.

Requirements:
1. Tech stack
   - Kotlin Multiplatform Mobile (shared module)
   - Android app: Kotlin + Jetpack Compose (single-activity)
   - iOS app: SwiftUI (iOS 15+)
   - Shared preferences: use kotlin-multiplatform-settings (or an equivalent multiplatform preferences lib). Expose Settings APIs from shared module.
   - Shared business logic: validation, auth UI state, navigation logic (simple sealed classes), and preference handling.
   - No real backend integration — mock repositories in shared module to simulate login/signup success/failure.

2. Project layout (create these modules/files)
   - /shared (Kotlin Multiplatform)
     - src/commonMain/kotlin/com/example/shared/
       - di/SharedModule.kt  (simple factory functions — platform-specific wiring left minimal)
       - data/UserRepository.kt (interface)
       - data/MockUserRepository.kt (in-memory implementation)
       - prefs/Preferences.kt (interface)
       - prefs/MppSettingsPreferences.kt (implementation using multiplatform-settings)
       - domain/AuthUseCases.kt (login, signup, isFirstInstall, setFirstInstall)
       - model/User.kt
       - validator/Validators.kt (email/password/fullname validations)
       - navigation/AppRoute.kt (sealed classes: Splash, Onboarding, Login, Signup, Home)
       - state/AuthState.kt (data classes for UI state)
   - /androidApp
     - MainActivity.kt (Compose host & NavHost)
     - ui/splash/SplashScreen.kt
     - ui/onboarding/OnboardingScreen.kt
     - ui/auth/LoginScreen.kt
     - ui/auth/SignupScreen.kt
     - ui/components/ReusableComponents.kt (text fields, buttons, dot indicator)
     - theme/Theme.kt
     - platform/AndroidPreferencesAdapter.kt (adapter that provides Preferences implementation to shared module)
   - /iosApp (Xcode project)
     - AppDelegate / Scene / SwiftUI App
     - ContentView.swift (navigation wrapper)
     - SplashView.swift
     - OnboardingView.swift
     - LoginView.swift
     - SignupView.swift
     - Components.swift (reusable SwiftUI UI pieces)
     - Platform bridging code to call shared module API (Kotlin/Native interop)

3. Flow & Behavior
   - Splash
     - Show system logo (use platform system icon / app icon placeholder).
     - Show app name below logo.
     - Delay 2s, then check shared preference `isFirstInstall`:
       - If true / not set → navigate to Onboarding.
       - If false → navigate to Login.
   - Onboarding
     - 3 pages. Each page layout: top 50% image placeholder, bottom 50% heading + subheading (use dummy text).
     - Top-right "Skip" button → immediately go to Login and set `isFirstInstall=false`.
     - Bottom-right button labeled "Next" for page 1 & 2; last page shows "Get Started" → pressing sets `isFirstInstall=false` and navigates to Login.
     - 3-dot page indicator showing current page.
   - Login
     - Email & password inputs.
     - Validation: email format; password length >= 6.
     - Login button disabled until valid.
     - Link: "Don't have an account? Signup" → navigates to Signup.
     - Social login buttons: Apple, Google, Facebook (system default icons only; no actual provider integration).
   - Signup
     - Inputs: full name, email, password, confirm password.
     - Validations:
       - full name non-empty, longer than 2 chars.
       - email valid format.
       - password length >= 6.
       - confirmPassword == password.
     - Signup button disabled until valid.
     - Link: "Already have an account? Login" → go back to Login.
     - Social login buttons same as Login.

4. Shared logic details (implement in shared module)
   - Validators.kt: functions `isValidEmail(email)`, `isValidPassword(pw)`, `isValidFullName(name)`.
   - AuthUseCases:
     - suspend fun login(email, password): Result<User>
     - suspend fun signup(fullName, email, password): Result<User>
     - fun isFirstInstall(): Boolean
     - fun setFirstInstall(flag: Boolean)
   - Preferences interface: getBoolean(key, default), putBoolean(key, value), etc.
   - MockUserRepository: accept any valid input and return success after small delay (simulate network).
   - Expose simple coroutine dispatcher expectation (or default) so platform code can call suspend functions.

5. Android-specific details
   - Use `Navigation Compose` NavHost with composable destinations matching shared AppRoute.
   - Use `LaunchedEffect` for splash delay and navigation decisions.
   - Compose UI:
     - Inputs as `OutlinedTextField`.
     - Buttons consistent with Material3 defaults.
     - Dot indicator: simple Row of circles with animation on selected.
   - Preference adapter: wrap Android SharedPreferences or DataStore via the multiplatform-settings implementation.
   - Provide ViewModel per screen that calls shared module usecases (use KMM compatibility: create `ViewModel` convenience wrappers in android module that call `AuthUseCases`).

6. iOS-specific details
   - SwiftUI navigation (use `@State` / `NavigationStack`).
   - Call shared module functions via Kotlin/Native API (import Shared module).
   - For delays use `DispatchQueue.main.asyncAfter` or call suspend functions through the K/N bridging helpers.
   - Preferences: ensure multiplatform-settings has iOS implementation; otherwise write a small iOS adapter that calls NSUserDefaults and expose via Kotlin expect/actual or provide platform-specific Preferences implementation at startup.

7. UX details
   - All input fields show validation message inline (e.g., "Invalid email" or "Password must be 6+ characters").
   - Buttons visually disabled when inputs invalid.
   - Social icons placed horizontally below main buttons.
   - Keep UI layout responsive for phones and small tablets.

8. Output expectations
   - Generate complete KMM shared module code for the core files listed above.
   - Generate Android Compose implementation for all screens, wired to shared usecases (ViewModels or simple coroutine handlers).
   - Generate SwiftUI screens with calls to shared module (stubs acceptable where bridging complexity arises); include comments that indicate where to wire K/N suspend calls.
   - Provide README with:
     - How to build Android (./gradlew :androidApp:installDebug)
     - How to open and run iOS in Xcode (open iosApp/iosApp.xcworkspace)
     - How to run unit tests in shared module
     - Notes about using `kotlin-multiplatform-settings` library and how to configure it per platform.

9. Extras (nice-to-have)
   - Simple navigation state manager class in shared module to hold current route (optional).
   - Unit tests for validators (commonTest).
   - Small sample screenshot assets or placeholder Compose/SwiftUI Image usage.

Please generate code only (Kotlin + Swift + Android XML if needed) with comments explaining platform-bridge points. Mark generated files and their relative paths. Keep third-party libraries minimal and list them in generated Gradle/Maven files. If any platform binding requires manual steps, add concise steps in README.

End of prompt.
