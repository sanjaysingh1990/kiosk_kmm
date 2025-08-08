import SwiftUI

struct CustomTextField: View {
    @Binding var text: String
    let placeholder: String
    let leadingIcon: String
    var isSecure: Bool = false
    var error: String? = nil

    var body: some View {
        VStack(alignment: .leading) {
            HStack {
                Image(systemName: leadingIcon)
                    .foregroundColor(.gray)
                if isSecure {
                    SecureField(placeholder, text: $text)
                } else {
                    TextField(placeholder, text: $text)
                }
            }
            .padding()
            .background(Color(.systemGray6))
            .cornerRadius(8)
            .overlay(
                RoundedRectangle(cornerRadius: 8)
                    .stroke(error != nil ? Color.red : Color.clear, lineWidth: 1)
            )

            if let error = error {
                Text(error)
                    .font(.caption)
                    .foregroundColor(.red)
                    .padding(.leading, 5)
            }
        }
    }
}

struct PrimaryButton: View {
    let text: String
    let action: () -> Void
    let isEnabled: Bool

    var body: some View {
        Button(action: action) {
            Text(text)
                .font(.headline)
                .padding()
                .frame(maxWidth: .infinity)
                .background(isEnabled ? Color.blue : Color.gray)
                .foregroundColor(.white)
                .cornerRadius(10)
        }
        .disabled(!isEnabled)
    }
}

struct SocialLoginButton: View {
    let iconName: String
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            Image(systemName: iconName)
                .resizable()
                .scaledToFit()
                .frame(width: 30, height: 30)
                .padding()
                .background(Color(.systemGray6))
                .cornerRadius(10)
        }
    }
}

struct PageIndicator: View {
    let numberOfPages: Int
    let currentPage: Int

    var body: some View {
        HStack {
            ForEach(0..<numberOfPages, id: \.self) {
                Circle()
                    .fill($0 == currentPage ? Color.blue : Color.gray)
                    .frame(width: 10, height: 10)
            }
        }
    }
}
