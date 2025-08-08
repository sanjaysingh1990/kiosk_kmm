import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        SharedModule.shared.doInit(settingsFactory: SettingsFactory()) // Initialize SharedModule
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
