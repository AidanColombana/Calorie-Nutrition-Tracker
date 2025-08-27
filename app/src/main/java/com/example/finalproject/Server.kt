package com.example.finalproject

import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt

data class UserData(
    val name: String,
    val emailAddress: String,
    val age: Int,
    val weight: Double,
    val inchHeight: Double, // user inputs height in inches
    val gender: Int,

    var recommendedCalories: Int = 2000
)

// Unneeded legacy code from develop ent but shows project production process
data class foodMealInformation(
    val mealType: MealTypes,
    val Calories: Double,
    val Protein: Double,
    val Carbs: Double,
    val Fat: Double,
    val foodName: String
)

data class dateAndWieght(
    var mealType: MealTypes,
    var weight: Double
)


class Server {

    // current user data used inside app
    var currentUserData: UserData? = null

    // set user data after it has been entered by the user
    fun setUserData(name: String, email: String, age: Int, weight: Double, inchHeight: Double, gender: Int) {
        currentUserData = UserData(
            name = name,
            emailAddress = email,
            age = age,
            weight = weight,
            inchHeight = inchHeight,

            gender = gender,
            recommendedCalories = 0
        )

        //  Calorie intake values are based on the Mifflin-St Jeor equation
        if (gender == 1) {// female
            currentUserData!!.recommendedCalories = ((655 + (4.35 * weight) + (4.7 * inchHeight) - (4.67 * age)) * 1.55).roundToInt()
        } else { // male or other
            currentUserData!!.recommendedCalories = ((66 + (6.23 * weight) + (12.7 * inchHeight) - (6.75 * age)) * 1.55).roundToInt()
        }

    }

    // accessor for user data
    fun getUserData(): UserData? {
        return currentUserData
    }

    // function used for writing to sql database in ProfilePage
    fun userDataForSQL(): String {
        return "'${currentUserData?.name}', '${currentUserData?.emailAddress}', ${currentUserData?.age}, ${currentUserData?.weight}, ${currentUserData?.inchHeight}, ${currentUserData?.gender}, ${currentUserData?.recommendedCalories}"
    }

    // Used for testing purposes
    fun userDataToString(): String {
        return "Name $currentUserData.name, Email: $currentUserData.email, Weight: $currentUserData.weight, Age: $currentUserData.age, Height: $currentUserData.height"
    }

}

// Different pages available in app, including dropped workout page
enum class Screens {
    MAINSCREEN,
    FOODPAGE,
    GRAPHPAGE,
    WORKOUTPAGE,
    PROFILEPAGE
}

enum class MealTypes {
    BREAKFAST,
    LUNCH,
    DINNER,
    DESERT,
    SNACKS,
}

enum class Gender {
    MALE,
    FEMALE,
    OTHER
}