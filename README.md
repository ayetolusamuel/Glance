# Glance - Link Preview App 🔍

A modern Android application built with Jetpack Compose that generates beautiful preview cards for any URL. Simply paste a link and get an instant preview with title, description, and image.

## ✨ Features

- **🔗 Smart Link Detection**: Automatically detects and validates URLs as you type
- **🎨 Beautiful Preview Cards**: TechCrunch-inspired design with smooth animations
- **⚡ Real-time Processing**: Debounced URL fetching for optimal performance
- **🌐 Web Scraping**: Extracts meta data (Open Graph, Twitter Cards) from web pages
- **📱 Modern UI**: Built with Jetpack Compose and Material Design 3
- **💫 Smooth Animations**: Elegant transitions and interactive elements

## 🚀 Quick Start

### Prerequisites

- Android Studio Hedgehog or later
- Android SDK 34+
- Java 11

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/glance.git
   cd glance
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory

3. **Build and Run**
   - Connect an Android device or start an emulator
   - Click "Run" ▶️ or press `Shift + F10`

## 🏗️ Architecture

Glance follows modern Android development best practices:

```
app/
├── data/
│   ├── api/           # Retrofit networking layer
│   ├── model/         # Data classes
│   └── repository/    # Data repository pattern
├── di/               # Dependency injection with Hilt
├── ui/
│   ├── components/    # Reusable Compose components
│   ├── screen/       # Main screens
│   └── theme/        # Design system
└── util/             # Utility functions
```

### Key Technologies

- **🏗️ MVVM Architecture**: Clean separation of concerns
- **💉 Dagger Hilt**: Dependency injection
- **🎯 Jetpack Compose**: Modern declarative UI
- **🌐 Retrofit + OkHttp**: Network requests
- **🔍 JSoup**: HTML parsing
- **🖼️ Coil**: Image loading
- **📊 Kotlin Coroutines**: Asynchronous programming
- **🔄 KSP**: Kotlin Symbol Processing

## 🎨 UI Components

### LinkInputField
A stylish text input field with:
- Floating placeholder text
- Rounded corners with shadow
- Smart URL validation
- Keyboard optimization

### TechCrunchPreviewCard
Beautiful preview card featuring:
- Browser mockup with navigation dots
- Circular image container
- Typography hierarchy
- Smooth elevation transitions

### BrowserMockup
Realistic browser interface:
- Authentic browser header with navigation dots
- Responsive image container
- Teal background theme

### ShareActionButton
Interactive share button:
- Press animation effects
- Circular design with shadow
- Icon-based visual feedback

## 🔧 Configuration

### Network Configuration
The app uses custom OkHttp interceptors for optimal web scraping:

```kotlin
// Custom headers for better compatibility
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
```

### Supported Meta Tags
- Open Graph tags (`og:title`, `og:description`, `og:image`)
- Twitter Card tags (`twitter:title`, `twitter:description`, `twitter:image`)
- Standard HTML meta tags

## 🚦 Usage

1. **Launch the app**
2. **Paste a URL** into the input field
3. **Wait for automatic processing** (750ms debounce)
4. **View the beautiful preview card** with extracted content
5. **Use the share button** to share the preview

### Example URLs to Try
- https://techcrunch.com
- https://github.com
- https://twitter.com
- Any website with Open Graph tags

## 🛠️ Development

### Building from Source

1. **Ensure proper Java version**
   ```bash
   java -version  # Should be Java 11 or later
   ```

2. **Sync project with Gradle files**
   - In Android Studio: File → Sync Project with Gradle Files

3. **Run tests**
   ```bash
   ./gradlew test
   ./gradlew connectedAndroidTest
   ```

### Code Style
This project follows Kotlin coding conventions and Android best practices:
- Use `camelCase` for variables and functions
- Follow Jetpack Compose naming conventions
- Maintain 80%+ test coverage
- Use meaningful variable names

## 📁 Project Structure

```
app/src/main/java/com/pedektech/glance/
├── MainActivity.kt                 # App entry point
├── di/
│   └── AppModule.kt               # Dependency injection setup
├── data/
│   ├── api/
│   │   ├── HtmlService.kt         # Retrofit service interface
│   │   └── RetrofitClient.kt      # HTTP client configuration
│   ├── model/
│   │   └── LinkPreviewData.kt     # Data model for link previews
│   └── repository/
│       └── LinkPreviewRepository.kt # Data access layer
├── ui/
│   ├── components/
│   │   ├── BrowserMockup.kt       # Browser UI component
│   │   ├── LinkInputField.kt      # URL input component
│   │   ├── ShareActionButton.kt   # Share functionality
│   │   └── TechCrunchPreviewCard.kt # Main preview card
│   ├── screen/
│   │   ├── ImageScreen.kt         # Main screen composable
│   │   └── ImageScreenViewModel.kt # Screen state management
│   └── theme/
│       ├── AppTheme.kt            # Theme configuration
│       ├── Color.kt               # Color palette
│       └── Type.kt                # Typography system
└── util/
    ├── Constants.kt               # App constants
    └── UrlUtils.kt                # URL validation utilities
```

## 🔍 Technical Details

### Network Layer
- **Timeout**: 15 seconds for connection, read, and write
- **Retrofit**: Type-safe HTTP client
- **OkHttp Interceptors**: Custom headers for web scraping
- **Error Handling**: Comprehensive network error management

### Performance Optimizations
- **Debouncing**: 750ms delay before URL processing
- **Lazy Initialization**: Services initialized on first use
- **Coroutine Scopes**: Proper lifecycle-aware async operations
- **Image Caching**: Coil with crossfade animations

### Supported Android Versions
- **Minimum SDK**: 24 (Android 7.0 Nougat)
- **Target SDK**: 34 (Android 14)
- **Compile SDK**: 36

## 🐛 Troubleshooting

### Common Issues

1. **Preview not loading**
   - Check internet connection
   - Verify URL format
   - Ensure website has meta tags

2. **Build errors**
   - Clean project: Build → Clean Project
   - Invalidate caches: File → Invalidate Caches / Restart
   - Check Java version compatibility

3. **Network issues**
   - Verify network permissions in AndroidManifest.xml
   - Check if website blocks scraping
   - Test with different URLs

### Debugging
Enable debug logging by setting `BuildConfig.DEBUG = true` to see detailed network requests and parsing results.

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit changes: `git commit -m 'Add amazing feature'`
4. Push to branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

### Contribution Guidelines
- Follow the existing code style
- Write unit tests for new features
- Update documentation accordingly
- Ensure all tests pass before submitting

## 📄 License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## 🙏 Acknowledgments

- Jetpack Compose team for the amazing UI framework
- TechCrunch for design inspiration
- Open-source libraries that made this possible:
  - Retrofit for networking
  - JSoup for HTML parsing
  - Coil for image loading
  - Timber for logging

## 📞 Support

If you encounter any issues or have questions:

- **Open an issue** on GitHub
- **Check existing issues** for solutions
- **Review the code** for implementation details

---

**Built with ❤️ using modern Android development practices**

---

<div align="center">

### ⭐ Star this repo if you find it helpful!

</div>
