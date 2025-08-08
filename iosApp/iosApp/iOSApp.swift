import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        SharedModuleKt.doInit(settingsFactory: SettingsFactory()) // Initialize SharedModule
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
