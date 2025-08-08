import SwiftUI
import Shared

struct ContentView: View {
    @State private var currentRoute: AppRoute = AppRoute.Splash()

    var body: some View {
        NavigationStack {
            VStack {
                if currentRoute is AppRoute.Splash {
                    SplashView(onNavigate: { route in
                        currentRoute = route
                    })
                } else if currentRoute is AppRoute.Onboarding {
                    OnboardingView(onNavigate: { route in
                        currentRoute = route
                    })
                } else if currentRoute is AppRoute.Login {
                    LoginView(onNavigate: { route in
                        currentRoute = route
                    })
                } else if currentRoute is AppRoute.Signup {
                    SignupView(onNavigate: { route in
                        currentRoute = route
                    })
                } else if currentRoute is AppRoute.Home {
                    Text("Home Screen") // Placeholder for Home Screen
                }
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}