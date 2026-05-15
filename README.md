# Kaushalya Karnataka (Self Employment)

**Kaushalya Karnataka** is a "Skill Showcase" application designed for local entrepreneurs and skilled laborers (electricians, plumbers, carpenters) in small towns. Unlike massive job portals, this is a hyper-local "Blue-Collar Portfolio" that allows workers to list their services, showcase verified work through photos, and build a "Trust Economy" within their community.

## 🚀 Features

- **Worker Portfolio**: Workers can upload photos of their completed projects (e.g., a finished cabinet or house wiring) to document their expertise.
- **Service List**: Every worker can create a "Service Card" with fixed or "Starting at" price lists (e.g., "Fan Repair: ₹200").
- **Hire Me**: A one-tap button for customers to request a call or service from a professional.
- **Review Wall**: A community feedback system where local residents post short text reviews and ratings.
- **Category Filtering**: A robust search and filter system to find experts by category (Electrician, Plumber, Carpenter).
- **Dynamic Ratings**: Automatic calculation of average star ratings based on neighbor feedback.

## 🛠️ Tech Stack

- **UI**: Jetpack Compose (Modern Android Toolkit)
- **Architecture**: MVVM (Model-View-ViewModel)
- **Language**: Kotlin
- **Database**: Firebase Firestore (Real-time updates)
- **Storage**: Firebase Storage (Hosting portfolio images)
- **Image Loading**: Coil (Compose-first image loading)
- **Navigation**: Navigation Compose with a synchronized Bottom Navigation Bar.

## 📁 Project Structure
## 📁 Project Structure
KaushalyaKarnataka/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/kaushalyakarnataka/
│   │   │   │   ├── components/
│   │   │   │   │   ├── WorkerCard.kt
│   │   │   │   │   └── ServiceCard.kt
│   │   │   │   ├── data/
│   │   │   │   │   └── FirestoreRepository.kt
│   │   │   │   ├── model/
│   │   │   │   │   └── Worker.kt
│   │   │   │   ├── navigation/
│   │   │   │   │   ├── BottomNav.kt
│   │   │   │   │   └── NavGraph.kt
│   │   │   │   ├── screens/
│   │   │   │   │   ├── HomeScreen.kt
│   │   │   │   │   ├── ServicesScreen.kt
│   │   │   │   │   ├── WorkersScreen.kt
│   │   │   │   │   ├── ProfileScreen.kt
│   │   │   │   │   └── WorkerDetailsScreen.kt
│   │   │   │   ├── ui/theme/
│   │   │   │   │   ├── Color.kt
│   │   │   │   │   ├── Theme.kt
│   │   │   │   │   └── Type.kt
│   │   │   │   ├── viewmodel/
│   │   │   │   │   └── WorkerViewModel.kt
│   │   │   │   └── MainActivity.kt
│   │   │   ├── res/
│   │   │   └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── google-services.json
├── build.gradle.kts
└── README.md

# Project documentation

## 🏗️ Setup Instructions

1.  **Clone the project** to your local machine.
2.**Firebase Configuration**:
    *   Create a project on the [Firebase Console](https://console.firebase.google.com/).
    *   Enable **Firestore Database** and **Firebase Storage**.
    *   Download `google-services.json` and place it in the `app/` folder.
3.  **Build**: Sync the project with Gradle files and build the project in Android Studio.
4.  **Run**: Deploy to an Android device or emulator (API 24+).

## 🌟 Impact Goals

*   **Entrepreneurship**: Turning local laborers into micro-entrepreneurs.
*   **Dignity of Labor**: Professionalizing local services through digital digital profiles.
*   **Local Economy**: Keeping money circulating within the local community.
