import SwiftUI
import Shared

struct SignupView: View {
    let onNavigate: (AppRoute) -> Void
    let onPop: () -> Void
    @State private var fullName = ""
    @State private var email = ""
    @State private var password = ""
    @State private var confirmPassword = ""

    @State private var fullNameError: String? = nil
    @State private var emailError: String? = nil
    @State private var passwordError: String? = nil
    @State private var confirmPasswordError: String? = nil
    @State private var isLoading = false

    private let authUseCases = SharedModule.shared.provideAuthUseCases()

    var body: some View {
        VStack(spacing: 20) {
            Text("Sign Up")
                .font(.largeTitle)
                .padding(.bottom, 30)

            CustomTextField(
                text: $fullName,
                placeholder: "Full Name",
                leadingIcon: "person",
                error: fullNameError
            )
            .onChange(of: fullName) { newValue in
                fullNameError = Validators.shared.isValidFullName(fullName: newValue) ? nil : "Full name must be longer than 2 characters"
            }

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

            CustomTextField(
                text: $confirmPassword,
                placeholder: "Confirm Password",
                leadingIcon: "lock.fill",
                isSecure: true,
                error: confirmPasswordError
            )
            .onChange(of: confirmPassword) { newValue in
                confirmPasswordError = (newValue == password) ? nil : "Passwords do not match"
            }

            PrimaryButton(
                text: isLoading ? "Signing up..." : "Sign Up",
                action: performSignup,
                isEnabled: !isLoading && fullNameError == nil && emailError == nil && passwordError == nil && confirmPasswordError == nil && !fullName.isEmpty && !email.isEmpty && !password.isEmpty && !confirmPassword.isEmpty
            )

            HStack {
                Text("Already have an account?")
                Button("Login") {
                    onPop()
                }
            }
            .padding(.top, 10)

            Text("Or sign up with:")
                .font(.headline)
                .padding(.top, 20)

            HStack(spacing: 20) {
                SocialLoginButton(iconName: "apple.logo") { /* Apple Signup */ }
                SocialLoginButton(iconName: "google.logo") { /* Google Signup */ }
                SocialLoginButton(iconName: "facebook") { /* Facebook Signup */ }
            }

            Spacer()
        }
        .padding()
        .navigationBarBackButtonHidden(true) // Hide default back button
        .toolbar {
            ToolbarItem(placement: .navigationBarLeading) {
                Button(action: {
                    onPop()
                }) {
                    HStack {
                        Image(systemName: "chevron.left")
                        Text("Back")
                    }
                }
            }
        }
    }

    private func performSignup() {
        isLoading = true
        Task {
            do {
                let user = try await authUseCases.signup(fullName: fullName, email: email, password: password)
                print("Signup successful: \(user ?? "Unknown")")
                onNavigate(AppRoute.Home())
            } catch {
                print("Signup failed: \(error.localizedDescription)")
                // Handle error, e.g., show an alert
            }
            isLoading = false
        }
    }
}

struct SignupView_Previews: PreviewProvider {
    static var previews: some View {
        SignupView(onNavigate: { _ in }, onPop: {})
    }
}
