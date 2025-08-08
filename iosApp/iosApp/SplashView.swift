import SwiftUI
import Shared

struct SplashView: View {
    let onNavigate: (AppRoute) -> Void
    private let authUseCases = SharedModuleKt.provideAuthUseCases()

    var body: some View {
        VStack {
            Image(systemName: "app.fill") // Placeholder for system logo
                .resizable()
                .scaledToFit()
                .frame(width: 128, height: 128)
            Text("KMM Onboarding App")
                .font(.largeTitle)
        }
        .onAppear {
            DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
                if authUseCases.isFirstInstall() {
                    onNavigate(AppRoute.Onboarding())
                } else {
                    onNavigate(AppRoute.Login())
                }
            }
        }
    }
}

struct SplashView_Previews: PreviewProvider {
    static var previews: some View {
        SplashView(onNavigate: { _ in })
    }
}
