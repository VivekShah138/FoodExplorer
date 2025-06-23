# Food Explorer

Food Explorer is an Android app that demonstrates the use of **MVVM** and **Clean Architecture** principles. It showcases modern Android development practices including Jetpack Compose UI, Kotlin, Room Database, Retrofit, and more.

---

## What does it do?

- Loads a list of food items from a REST API (a fake API is used for demonstration).
- Displays all food items in a scrollable list with cards showing:
  - Image
  - Item name
  - Description
  - Rate and rating
- Allows users to add or remove items from their favorites via:
  - A favorite icon on each item in the list
  - A toggle switch on the item detail screen
- Users can navigate to a **Favorites** screen to see only their favorite food items.
- Clicking on a food item opens a **Detail** screen showing full details, description, and images.
- Data is persisted locally using **Room Database** with reactive flows to update the UI automatically.

---

## How does it work?

- On first launch, the app fetches food items from the API and inserts them into the local Room database.
- The UI observes a Flow from the database to display the list of items.
- If the API call fails (e.g., no internet), the app shows an error message with a button to load the initial data from a local JSON asset (`food_items.json`).
- Users can refresh the list manually by pulling down on the All Items screen, which triggers a new API call to fetch the latest data and update the database.
- The favorite state is saved in the database, enabling offline access to favorite items.


---

## Screens

- **All Items Screen:** Displays all food items with image, name, description, rate, and rating. Each item has a favorite icon to add/remove from favorites.
- **Favorite Items Screen:** Shows only the user's favorite items in the same card format.
- **Detail Item Screen:** Shows detailed information about the selected food item with a back button and toggle to add/remove from favorites.

---

## Tech Stack

- **Architecture:** MVVM, Clean Architecture
- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Database:** Room Database with Kotlin Flows
- **Networking:** Retrofit (with fake REST API)
- **Dependency Injection:** Dagger Hilt
- **Image Loading:** Coil
- **Testing:** JUnit

---

## How to Run

1. Clone the repository.
2. Build and run on an Android device or emulator.
3. On first launch, the app will fetch data from the API and populate the local database.
4. Use the navigation drawer or bottom navigation to explore all items and favorites.
5. Toggle favorites on the list or detail screens.

---

## Demo
![Demo video](Media/Demo_Video-ezgif.com-video-to-gif-converter.gif)


Feel free to explore the code to see MVVM and Clean Architecture in action!
