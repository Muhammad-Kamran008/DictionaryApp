# MyDictionary 📖

MyDictionary is an Android application built with Kotlin that serves as a powerful dictionary tool, allowing users to search for words and their meanings. The app uses Clean Architecture principles and leverages modern Android development tools such as Dagger Hilt for dependency injection, Retrofit for API calls, Room for local storage, and various UI components like Bottom Sheets and ViewPager.

## Features ✨

- **Search Words**: Enter any word to find its meaning, pronunciation, usage, and more.
- **Recent Searches**: Store all the searched words locally in a Room database for easy access to previous searches.
- **API Integration**: Fetch word data dynamically using Retrofit from a reliable dictionary API.
- **Clean Architecture**: Ensures maintainability, scalability, and testability of the codebase.
- **Dependency Injection**: Uses Dagger Hilt for managing dependencies efficiently.
- **User Interface**: Utilizes modern Android UI components like Bottom Sheets for additional word details and ViewPager for seamless navigation.

## Technologies Used 🛠️

- **Kotlin**: The primary programming language for Android development.
- **Clean Architecture**: A robust architecture pattern to ensure separation of concerns.
- **Dagger Hilt**: For dependency injection.
- **Retrofit**: For making HTTP requests to a dictionary API.
- **Room Database**: For storing and managing a local database of searched words.
- **Coroutines**: For asynchronous programming.
- **LiveData & StateFlow**: For managing UI-related data in a lifecycle-conscious way.
- **ViewPager2**: For implementing swipeable views.
- **Bottom Sheets**: For displaying additional information about words.

## Architecture Overview 🏛️

MyDictionary is built using Clean Architecture principles, dividing the app into different layers:

- **Presentation Layer**: Contains UI components and view models.
- **Domain Layer**: Contains business logic and use cases.
- **Data Layer**: Handles data management including API requests and local database access.

## Dependency Injection 🔗

The app uses **Dagger Hilt** for dependency injection, allowing easy management and injection of dependencies throughout the app.

## API Integration 🌐

Retrofit is used to make network requests to a dictionary API to fetch word data. Make sure to set up your API key in the `local.properties` file as described in the **Installation & Setup** section.

## Local Storage 🗂️

The app uses **Room Database** to store all searched words locally, providing offline access to previous searches.

