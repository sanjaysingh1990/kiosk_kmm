import SwiftUI
import Shared

struct OnboardingPage: Identifiable {
    let id = UUID()
    let imageName: String
    let title: String
    let subtitle: String
}

struct OnboardingView: View {
    let onNavigate: (AppRoute) -> Void
    @State private var currentPage = 0
    private let authUseCases = SharedModuleKt.provideAuthUseCases()

    let pages = [
        OnboardingPage(imageName: "photo", title: "Welcome", subtitle: "Discover amazing features with our app."),
        OnboardingPage(imageName: "sparkles", title: "Explore", subtitle: "Find what you need easily and efficiently."),
        OnboardingPage(imageName: "hand.thumbsup", title: "Get Started", subtitle: "Join us today and start your journey!")
    ]

    var body: some View {
        VStack {
            HStack {
                Spacer()
                Button("Skip") {
                    authUseCases.setFirstInstall(flag: false)
                    onNavigate(AppRoute.Login())
                }
                .padding()
            }

            TabView(selection: $currentPage) {
                ForEach(0..<pages.count, id: \.self) { index in
                    VStack {
                        Image(systemName: pages[index].imageName)
                            .resizable()
                            .scaledToFit()
                            .frame(height: UIScreen.main.bounds.height * 0.4)
                            .padding(.bottom, 20)

                        Text(pages[index].title)
                            .font(.largeTitle)
                            .padding(.bottom, 10)

                        Text(pages[index].subtitle)
                            .font(.body)
                            .multilineTextAlignment(.center)
                            .padding(.horizontal)
                    }
                    .tag(index)
                }
            }
            .tabViewStyle(PageTabViewStyle(indexDisplayMode: .never))

            PageIndicator(numberOfPages: pages.count, currentPage: currentPage)
                .padding(.bottom, 20)

            Button(action: {
                if currentPage < pages.count - 1 {
                    currentPage += 1
                } else {
                    authUseCases.setFirstInstall(flag: false)
                    onNavigate(AppRoute.Login())
                }
            }) {
                Text(currentPage == pages.count - 1 ? "Get Started" : "Next")
                    .font(.headline)
                    .padding()
                    .frame(maxWidth: .infinity)
                    .background(Color.blue)
                    .foregroundColor(.white)
                    .cornerRadius(10)
            }
            .padding(.horizontal)
            .padding(.bottom, 20)
        }
    }
}

struct OnboardingView_Previews: PreviewProvider {
    static var previews: some View {
        OnboardingView(onNavigate: { _ in })
    }
}
