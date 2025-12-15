# 👑 Peaceful Queens

An elegant Android puzzle game based on the classic N-Queens problem. Place N queens on an N×N chessboard so that no two queens threaten each other—no shared rows, columns, or diagonals.

![Platform](https://img.shields.io/badge/Platform-Android-brightgreen)
![Language](https://img.shields.io/badge/Language-Kotlin-blue)
![MinSDK](https://img.shields.io/badge/MinSDK-26-orange)
![Tests](https://img.shields.io/badge/Tests-64%20passing-success)
![Coverage](https://img.shields.io/badge/Coverage-90%25%2B-brightgreen)

---

## 📱 Features

### Core Gameplay
- ✅ **Dynamic Board Sizes**: Play on 4×4 up to 15×15+ boards
- ✅ **Interactive Chessboard**: Tap to place or remove queens
- ✅ **Real-time Validation**: Instant conflict detection and visual feedback
- ✅ **Hint Mode**: Toggle to see attacked cells highlighted
- ✅ **Queens Counter**: Track remaining queens to place
- ✅ **Timer**: Elapsed time tracking for each puzzle
- ✅ **Win Detection**: Automatic victory celebration when puzzle is solved

### User Experience
- ✅ **Beautiful UI**: Modern Material Design 3 with neon glow effects
- ✅ **Responsive Design**: Portrait and landscape layouts, tablet-optimized
- ✅ **Best Scores**: Persistent leaderboard for each board size
- ✅ **Reset/New Game**: Start fresh anytime with confirmation dialogs
- ✅ **Sound Effects**: Audio feedback for queen placement
- ✅ **Animations**: Lottie loading animations with smooth transitions
- ✅ **Settings Menu**: Change board size without losing progress confirmation

### Technical Excellence
- ✅ **Clean Architecture**: MVI pattern with clear separation of concerns
- ✅ **Comprehensive Testing**: 64 unit tests with 90%+ coverage
- ✅ **Dependency Injection**: Hilt for clean dependency management
- ✅ **Immutable State**: Thread-safe state management
- ✅ **Encrypted Storage**: Secure persistence for best scores

---

## 🎥 Demo Video

> [📹 Watch the Demo Video](https://youtube.com/shorts/nXuf3eWDILQ?si=uavxY9UgUMS3L5JV)

https://github.com/user-attachments/assets/bbe3dfcf-8831-4dca-8358-e9425b4f621e



---

## 🏗️ Architecture

### Pattern: MVI (Model-View-Intent)

This project uses the **MVI (Model-View-Intent)** architectural pattern, providing unidirectional data flow and predictable state management.

### Layer Responsibilities

#### 1. **Presentation Layer** (`ui/`, `viewModel/`)
- **Fragments**: Host Composable UI, consume events, provide callbacks
- **Composables**: Pure presentation functions, receive state and callbacks
- **ViewModels**: Manage UI state, handle user actions, coordinate domain logic
- **State**: Immutable data classes representing UI state

#### 2. **Domain Layer** (`domain/`)
- **GameEngine**: Core game logic (board initialization, state transitions, win detection)
- **ConflictDetector**: Queen attack validation algorithm
- **Pure Kotlin**: No Android dependencies for easy testing

#### 3. **Data Layer** (`data/`)
- **Repositories**: Data access abstraction
- **PreferencesManager**: Encrypted SharedPreferences wrapper
- **Mappers**: Transform data between layers

---

## 🛠️ Technology Stack

### Core Technologies
- **Language**: Kotlin 2.0.21
- **UI Framework**: Jetpack Compose (BOM 2024.09.00)
- **Architecture**: MVI with unidirectional data flow
- **Dependency Injection**: Hilt 2.50
- **State Management**: Compose State + Immutable Collections

### Android Jetpack
- **Lifecycle**: ViewModel, LiveData, Runtime KTX
- **Navigation**: Navigation Component with Fragment support
- **Security**: Encrypted SharedPreferences

### Libraries
- **kotlinx-collections-immutable**: Thread-safe immutable collections
- **Lottie**: JSON-based animations
- **Timber**: Enhanced logging

### Testing
- **JUnit 4.13.2**: Unit testing framework
- **MockK 1.13.9**: Mocking library for Kotlin
- **Kotlinx Coroutines Test 1.8.0**: Async testing support
- **64 tests**: Comprehensive test coverage

### Development Tools
- **Gradle**: Build system with Kotlin DSL
- **Kapt**: Annotation processing for Hilt
- **Android Studio**: IDE

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio**: Ladybug | 2024.3.1+ (or latest stable)
- **JDK**: 11 or higher
- **Android SDK**: API 26+ (Android 8.0)
- **Gradle**: 8.13.1+ (auto-downloaded by wrapper)

### Clone the Repository

```bash
git clone https://github.com/brazhenko-alex/peaceful_queens.git
cd peaceful_queens
```

### Build the Project

#### Using Android Studio
1. Open Android Studio
2. Select **File → Open**
3. Navigate to the cloned repository and select it
4. Wait for Gradle sync to complete (may take a few minutes first time)
5. Build the project: **Build → Make Project** or `Ctrl+F9` / `Cmd+F9`

#### Using Command Line
```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Install debug APK on connected device
./gradlew installDebug
```

### Run the Application

#### Using Android Studio
1. Connect an Android device or start an emulator
2. Select the device from the device dropdown
3. Click the **Run** button (▶️) or press `Shift+F10` / `Ctrl+R`

#### Using Command Line
```bash
# Install and launch on connected device
./gradlew installDebug
adb shell am start -n com.brazole.peacefulqueens/.MainActivity
```

---

## 🧪 Testing

### Run All Tests

```bash
# Run all unit tests
./gradlew testDebugUnitTest

# Run instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest
```

### Run Specific Test Classes

```bash
# Run ConflictDetector tests only
./gradlew testDebugUnitTest --tests "*.ConflictDetectorTest"

# Run GameEngine tests only
./gradlew testDebugUnitTest --tests "*.GameEngineTest"

# Run BestScoresRepository tests only
./gradlew testDebugUnitTest --tests "*.BestScoresRepositoryTest"
```

### Test Coverage

The project includes **64 comprehensive unit tests** with **90%+ coverage** for domain and data layers:

| Component | Tests | Coverage | Status |
|-----------|-------|----------|--------|
| ConflictDetector | 21 | 100% | ✅ Passing |
| GameEngine | 30 | 95%+ | ✅ Passing |
| BestScoresRepository | 13 | 90%+ | ✅ Passing |

### UI Testing with Compose Previews

All Composable functions are thoroughly tested with **@Preview functions** covering:
- **Multiple board sizes**: 4×4, 8×8, 12×12, 15×15
- **Various game states**: Empty board, in progress, conflicts, hint mode, almost complete
- **Device configurations**: Portrait, landscape, tablet (portrait & landscape)
- **Edge cases**: Minimum board (1×1), maximum queens, all conflicts
- **Component variations**: Individual cells (light/dark, empty/queen, conflict/attacked)

These previews enable:
- **Visual regression testing** without running the app
- **Instant UI verification** during development
- **Design consistency** across different states
- **Accessibility validation** for various screen sizes

---

## 🎯 How to Play

1. **Choose Board Size**: Tap the settings icon (⚙️) to select your board size (4×4 to 15×15)
2. **Place Queens**: Tap any cell to place a queen (👑)
3. **Remove Queens**: Tap a queen to remove it
4. **Use Hint Mode**: Tap the hint button (💡) to see which cells are under attack
5. **Watch for Conflicts**: Queens turn red (🔴) when they threaten each other
6. **Solve the Puzzle**: Place all N queens so none threaten each other
7. **Win!**: A victory dialog appears with your time—try to beat your best score!

### Tips
- Start from the corners or edges
- Use hint mode to visualize attack zones
- Each board size has multiple valid solutions
- Smaller boards (4×4, 5×5) are great for learning
- Larger boards (10×10+) provide a real challenge

---

## 🏛️ Architecture Decisions

### Why MVI?

**Model-View-Intent** was chosen over MVVM for several key reasons:

1. **Unidirectional Data Flow**: Actions flow one way (View → ViewModel → State → View), making debugging easier
2. **Predictable State**: Single source of truth for UI state reduces bugs
3. **Testability**: Pure functions and clear state transitions are easy to test
4. **Time-Travel Debugging**: State history can be tracked for debugging
5. **Thread Safety**: Immutable state prevents race conditions

### Why Jetpack Compose?

**Jetpack Compose** was selected over XML layouts because:

1. **Declarative UI**: "What" not "how" - easier to reason about
2. **Less Code**: ~40% less code than XML + ViewModel + LiveData
3. **Type Safety**: Compile-time safety for UI code
4. **Reusability**: Composable functions are naturally composable
5. **Live Preview**: Instant UI previews without running the app
6. **Modern**: Google's recommended approach for new Android apps

### Why Immutable Collections?

**kotlinx-collections-immutable** provides:

1. **Thread Safety**: Safe concurrent access without synchronization
2. **Performance**: Structural sharing reduces memory overhead
3. **Predictability**: State can't be accidentally modified
4. **Debugging**: Easy to track state changes
5. **MVI Alignment**: Perfect fit for immutable state management

### Why Hilt?

**Hilt** (over Koin or manual DI) because:

1. **Compile-Time Safety**: Catches DI errors at compile time
2. **Android Integration**: Built specifically for Android
3. **ViewModel Support**: Seamless ViewModel injection
4. **Scoping**: Automatic lifecycle-aware scopes
5. **Performance**: No reflection overhead

### State Management: `mutableStateOf` vs `StateFlow`

**`mutableStateOf`** was chosen for state management because:

1. **Compose Native**: Direct integration with Compose recomposition
2. **Simpler API**: Less boilerplate than StateFlow
3. **Automatic Recomposition**: Compose subscribes automatically
4. **Performance**: Optimized for Compose's snapshot system
5. **Cleaner Code**: No need for `.collectAsState()` in Composables

### Encrypted SharedPreferences

**Security** was prioritized:

1. **Sensitive Data**: Best scores are user achievements worth protecting
2. **Android Recommendation**: Google's best practice for local storage
3. **Minimal Overhead**: Encryption adds negligible performance cost
4. **Future-Proof**: Easy to add user profiles or cloud sync later

---

## 🎨 Design Decisions

### Visual Design
- **Neon Aesthetic**: Cyberpunk-inspired glow effects for modern feel
- **Color Coding**: Magenta (valid) vs Red (conflict) queens for instant feedback
- **Minimalism**: Clean design focuses attention on the puzzle
- **Responsive Layout**: Adapts to portrait/landscape/tablet

### UX Patterns
- **Confirmation Dialogs**: Prevent accidental progress loss
- **Hint Mode Toggle**: Optional help without hand-holding
- **Persistent Board Size**: Remembers user preference
- **Immediate Feedback**: Real-time visual and audio feedback

---

## 🧠 Algorithm: N-Queens Conflict Detection

The core algorithm efficiently detects queen attacks using coordinate arithmetic:

```kotlin
fun isAttacking(
    queenRow: Int, queenColumn: Int,
    targetRow: Int, targetColumn: Int
): Boolean {
    if (queenRow == targetRow && queenColumn == targetColumn) {
        return false  // Same position
    }
    
    return queenRow == targetRow                              // Same row
        || queenColumn == targetColumn                        // Same column
        || abs(queenRow - targetRow) == abs(queenColumn - targetColumn)  // Diagonal
}
```

**Time Complexity**: O(1) per check  
**Space Complexity**: O(1)

For an N×N board with M queens, checking all attacks is **O(N² × M)**, which is efficient even for large boards (15×15).

---

### Optimizations
- Immutable collections with structural sharing
- Compose smart recomposition
- Efficient attack calculation (O(1) per cell pair)
- No memory leaks (ViewModel lifecycle-aware)

---

## 🤖 AI Assistance Disclosure

This project was developed with assistance from AI coding tools, following the project requirements for full understanding and ownership of all code.

### AI Tools Used
- **Cursor AI (Claude Sonnet 4.5)**: Primary development assistant

### How AI Was Used
1. **Boilerplate Generation**: ViewModels, data classes, test templates
2. **Code Refactoring**: Extracting composables, organizing structure
3. **Test Cases**: Generating comprehensive test scenarios
4. **Documentation**: README, test summaries, code comments
5. **Best Practices**: Kotlin idioms, Compose patterns

### Human Contributions
- ✅ **Architecture decisions**: MVI, Hilt, Compose choices
- ✅ **Algorithm design**: N-Queens conflict detection logic
- ✅ **Testing strategy**: What to test and coverage goals
- ✅ **UI/UX design**: Neon theme, layouts, user flows
- ✅ **Code review**: All AI-generated code reviewed and understood
- ✅ **Problem-solving**: Debugging, edge cases, optimizations

### Code Ownership
**I fully understand and own 100% of the code in this project.** All code was:
- Reviewed line-by-line
- Tested thoroughly
- Refined based on my requirements
- Integrated into a cohesive architecture

---

### Future Enhancements
- [ ] Add remaining sound effects (remove, conflict, victory)
- [ ] Add victory celebration particle effects
- [ ] Implement undo/redo stack
- [ ] Implement achievement system
- [ ] Add cloud sync for best scores

---

## 📄 License

This project is provided for educational and interview purposes.

**Copyright © 2025 Oleksandr Brazhenko**

---

## 👤 Author

**Oleksandr Brazhenko**

- GitHub: [@brazhenko-alex](https://github.com/brazhenko-alex)
- LinkedIn: [Oleksandr Brazhenko](https://www.linkedin.com/in/a-brazhenko/)
- Email: brazhenko.p@gmail.com

---

**Built with ❤️ and ☕☕☕ in 2025**

