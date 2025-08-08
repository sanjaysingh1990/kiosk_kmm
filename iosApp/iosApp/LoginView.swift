import SwiftUI
import Shared

struct LoginView: View {
    let onNavigate: (AppRoute) -> Void
    @State private var email = ""
    @State private var password = ""
    @State private var emailError: String? = nil
    @State private var passwordError: String? = nil
    @State private var isLoading = false

    private let authUseCases = SharedModule.shared.provideAuthUseCases()

    var body: some View {
        VStack(spacing: 20) {
            Text("Login")
                .font(.largeTitle)
                .padding(.bottom, 30)

            CustomTextField(
                text: $email,
                placeholder: "Email",
                leadingIcon: "envelope",
                error: emailError
            )
            .keyboardType(.emailAddress)
            .autocapitalization(.none)
            .onChange(of: email) { newValue in
                emailError = Validators.shared.isValidEmail(email: newValue) ? nil : "Invalid email format"
            }

            CustomTextField(
                text: $password,
                placeholder: "Password",
                leadingIcon: "lock",
                isSecure: true,
                error: passwordError
            )
            .onChange(of: password) { newValue in
                passwordError = Validators.shared.isValidPassword(password: newValue) ? nil : "Password must be 6+ characters"
            }

            PrimaryButton(
                text: isLoading ? "Logging in..." : "Login",
                action: performLogin,
                isEnabled: !isLoading && emailError == nil && passwordError == nil && !email.isEmpty && !password.isEmpty
            )

            HStack {
                Text("Don't have an account?")
                Button("Signup") {
                    onNavigate(AppRoute.Signup())
                }
            }
            .padding(.top, 10)

            Text("Or login with:")
                .font(.headline)
                .padding(.top, 20)

            HStack(spacing: 20) {
                SocialLoginButton(iconName: "apple.logo") { /* Apple Login */ }
                SocialLoginButton(iconName: "google.logo") { /* Google Login */ }
                SocialLoginButton(iconName: "facebook") { /* Facebook Login */ }
            }

            Spacer()
        }
        .padding()
        .navigationBarBackButtonHidden(true)
    }

    private func performLogin() {
        isLoading = true
        Task {
            do {
                let result = try await authUseCases.login(email: email, password: password)
                print("Login successful: \(result)")
                onNavigate(AppRoute.Home())
            } catch {
                print("Login failed: \(error.localizedDescription)")
                // Handle error, e.g., show an alert
            }
            isLoading = false
        }
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView(onNavigate: { _ in })
    }
}
