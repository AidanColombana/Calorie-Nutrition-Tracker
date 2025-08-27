package com.example.finalproject

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ProfilePageUITest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.finalproject", appContext.packageName)
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    // Tests the update information button off of empty text field values
    @Test
    fun testEmptyProfilePageUpdate() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            val myColorThemeViewModel = ColorThemeViewModel()
            val dbman = DatabaseManager(LocalContext.current)

            NavHost(navController, startDestination = Screens.MAINSCREEN.name) {
                composable(Screens.MAINSCREEN.name) {
                    MainScreenUI(navController, myColorThemeViewModel, dbman)
                }
                composable(Screens.PROFILEPAGE.name) {
                    ProfilePageUI(navController, myColorThemeViewModel, dbman)
                }
            }
        }

        // click on the icon to open dropdownMenu
        composeTestRule.onNodeWithTag("NavhostDropdownMenuIcon").performClick()

        // check that it is displayed
        composeTestRule.onNodeWithTag("NavhostDropdownMenu").assertIsDisplayed()

        // click on the profile node of DropdownMenu
        composeTestRule.onNodeWithText("Profile").performClick()

        // test if the button is shown on the screen (Meaning we're on profile page)
        composeTestRule.onNodeWithTag("updateProfileButton").assertIsDisplayed()

        // click on button with empty values to ensure no crash
        composeTestRule.onNodeWithTag("updateProfileButton").performClick()
    }

    // Tests the update information button with non-number weight, age, and height
    @Test
    fun testIncorrectProfilePageData() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            val myColorThemeViewModel = ColorThemeViewModel()
            val dbman = DatabaseManager(LocalContext.current)

            NavHost(navController, startDestination = Screens.MAINSCREEN.name) {
                composable(Screens.MAINSCREEN.name) {
                    MainScreenUI(navController, myColorThemeViewModel, dbman)
                }
                composable(Screens.PROFILEPAGE.name) {
                    ProfilePageUI(navController, myColorThemeViewModel, dbman)
                }
            }
        }

        // click on the icon to open dropdownMenu
        composeTestRule.onNodeWithTag("NavhostDropdownMenuIcon").performClick()

        // check that it is displayed
        composeTestRule.onNodeWithTag("NavhostDropdownMenu").assertIsDisplayed()

        // click on the profile node of DropdownMenu
        composeTestRule.onNodeWithText("Profile").performClick()

        // test if the button is shown on the screen (Meaning we're on profile page)
        composeTestRule.onNodeWithTag("updateProfileButton").assertIsDisplayed()

        composeTestRule.onNodeWithTag("Name-input").performTextInput("testFailName")
        composeTestRule.onNodeWithTag("Email-input").performTextInput("testFailEmail")

        composeTestRule.onNodeWithTag("Weight (lbs)-input").performTextInput("Non-Doublables...")
        composeTestRule.onNodeWithTag("Age-input").performTextInput("your moms weight hehehe")
        composeTestRule.onNodeWithTag("Height (Inches)-input").performTextInput("goliath")


        // click on button with non integer or double values to ensure error handling
        composeTestRule.onNodeWithTag("updateProfileButton").performClick()
    }

    @Test
    fun testCorrectProfilePageDataAndDatabase() {

        composeTestRule.setContent {
            val navController = rememberNavController()
            val myColorThemeViewModel = ColorThemeViewModel()
            val dbman = DatabaseManager(LocalContext.current)

            NavHost(navController, startDestination = Screens.MAINSCREEN.name) {
                composable(Screens.MAINSCREEN.name) {
                    MainScreenUI(navController, myColorThemeViewModel, dbman)
                }
                composable(Screens.PROFILEPAGE.name) {
                    ProfilePageUI(navController, myColorThemeViewModel, dbman)
                }
            }

            // use cursor to get USERPROFILE information from database

            // get written over in cursor
            var name = "testName"
            var email = "testEmail"
            var age = 25
            var weight = 160.0
            var inchHeight = 62.0

            // add data to server
            server.setUserData(name, email, age, weight, inchHeight, 1)

            val addedString = server.userDataForSQL()

            // add data to database
            dbman.insertUserProfileData(addedString)

            // iterate over table (1 entry) and if data was successfully added it will be asserted
            val cursor = dbman.readableDatabase.rawQuery("SELECT * FROM USERPROFILE", null)
            // get all the information from the database
            while (cursor.moveToNext()) { // if the single row is in the database it will grab in here
                name = cursor.getString(0)
                email = cursor.getString(1)
                age = cursor.getInt(2)
                weight = cursor.getDouble(3)
                inchHeight = cursor.getDouble(4)
            }
            cursor.close()


            // check database for correctly stored values
            assertEquals("testName", name)
            assertEquals("testEmail", email)
            assertEquals(25, age)

            val delta = 0.01
            assertEquals(160.0, weight, delta)
            assertEquals(62.0, inchHeight, delta)

        }
    }

    @Test
    fun testEmptyTargetWeights() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            val myColorThemeViewModel = ColorThemeViewModel()
            val dbman = DatabaseManager(LocalContext.current)

            NavHost(navController, startDestination = Screens.MAINSCREEN.name) {
                composable(Screens.MAINSCREEN.name) {
                    MainScreenUI(navController, myColorThemeViewModel, dbman)
                }
                composable(Screens.PROFILEPAGE.name) {
                    ProfilePageUI(navController, myColorThemeViewModel, dbman)
                }
            }
        }

        // click on the icon to open dropdownMenu
        composeTestRule.onNodeWithTag("NavhostDropdownMenuIcon").performClick()

        // check that it is displayed
        composeTestRule.onNodeWithTag("NavhostDropdownMenu").assertIsDisplayed()

        // click on the profile node of DropdownMenu
        composeTestRule.onNodeWithText("Profile").performClick()

        // test if the button is shown on the screen (Meaning we're on profile page)
        composeTestRule.onNodeWithTag("updateProfileButton").assertIsDisplayed()


        // click on button with non integer or double values to ensure error handling
        composeTestRule.onNodeWithTag("updateTargetWeightButton").performClick()
    }

    // test for a string in the targetWeight textfield
    @Test
    fun testStringedTargetWeights() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            val myColorThemeViewModel = ColorThemeViewModel()
            val dbman = DatabaseManager(LocalContext.current)

            NavHost(navController, startDestination = Screens.MAINSCREEN.name) {
                composable(Screens.MAINSCREEN.name) {
                    MainScreenUI(navController, myColorThemeViewModel, dbman)
                }
                composable(Screens.PROFILEPAGE.name) {
                    ProfilePageUI(navController, myColorThemeViewModel, dbman)
                }
            }
        }

        // click on the icon to open dropdownMenu
        composeTestRule.onNodeWithTag("NavhostDropdownMenuIcon").performClick()

        // check that it is displayed
        composeTestRule.onNodeWithTag("NavhostDropdownMenu").assertIsDisplayed()

        // click on the profile node of DropdownMenu
        composeTestRule.onNodeWithText("Profile").performClick()

        // test if the button is shown on the screen (Meaning we're on profile page)
        composeTestRule.onNodeWithTag("updateProfileButton").assertIsDisplayed()

        composeTestRule.onNodeWithTag("Target Weight-input").performTextInput("testFailWeight")

        // click on button with string value to ensure error handling
        composeTestRule.onNodeWithTag("updateTargetWeightButton").performClick()
    }

    // test the database for the targetWeight
    @Test
    fun testCorrectWeightDataInDatabase() {

        composeTestRule.setContent {
            val navController = rememberNavController()
            val myColorThemeViewModel = ColorThemeViewModel()
            val dbman = DatabaseManager(LocalContext.current)

            NavHost(navController, startDestination = Screens.MAINSCREEN.name) {
                composable(Screens.MAINSCREEN.name) {
                    MainScreenUI(navController, myColorThemeViewModel, dbman)
                }
                composable(Screens.PROFILEPAGE.name) {
                    ProfilePageUI(navController, myColorThemeViewModel, dbman)
                }
            }

            // use cursor to get USERPROFILE information from database

            val targetWeight = 180
            var checkTargetWeight = 0

            // add data to server

            // add data to database
            dbman.insertUpdatedWeight(targetWeight, ColorTheme.DARK)

            // iterate over table (1 entry) and if data was successfully added it will be asserted
            val cursor = dbman.readableDatabase.rawQuery("SELECT * FROM STOREDAPPINFORMATION", null)
            // get all the information from the database
            while (cursor.moveToNext()) { // if the single row is in the database it will grab in here
                checkTargetWeight = cursor.getInt(0)
            }
            cursor.close()

            // check database for correctly stored value
            assertEquals(targetWeight, checkTargetWeight)

        }
    }

}