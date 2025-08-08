import SwiftUI
import Shared

struct ContentView: View {
    @State private var path = NavigationPath()

    var body: some View {
        NavigationStack(path: $path) {
            SplashView(onNavigate: { route in
                path.append(route)
            })
            .navigationDestination(for: AppRoute.self) { route in
                switch route {
                case is AppRoute.Onboarding:
                    OnboardingView(onNavigate: { newRoute in
                        path.append(newRoute)
                    })
                case is AppRoute.Login:
                    LoginView(onNavigate: { newRoute in
                        path.append(newRoute)
                    })
                case is AppRoute.Signup:
                    SignupView(onNavigate: { newRoute in
                        path.append(newRoute)
                    }, onPop: { 
                        path.removeLast()
                    })
                case is AppRoute.Home:
                    Text("Home Screen") // Placeholder for Home Screen
                default:
                    EmptyView()
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
