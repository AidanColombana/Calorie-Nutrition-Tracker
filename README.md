# Calorie and Nutrition Tracker (Android)

## Concept
This Android app was developed as a final project for a mobile app course at Western Washington University. The app was made in a group of three completely from scratch, with each member taking on a specific 
development role. The goal was to make a full-stack Android mobile app in Kotlin using Jetpack Compose. The app measures calories and micronutrients at a per-calorie level, or at a glance, thanks to the bar graphs of each fraction - no user math required! Food items are separated into different meal categories, each of which can have a manually entered meal, or if the meal, snack, or ingredients come with a barcode, it can be scanned for quick entry! 
  
An SQL database stores the user's meals, micronutrients, and profile preferences, such as a username, email, weight, height, gender, and whether the user prefers the light or dark color theme. This information is used to calculate the recommended micronutrients based on the Mifflin-St Jeor formula, which finds a person's resting metabolic rate (RMR). This is then used to calculate the recommended protein, carbs, and fat based on a 40% carbs, 30% protein, and 30% fat diet.  
  
These calculations, along with the target weight, can be used to graph your progress on your weight loss, weight gain, or micronutrient journey! This is done through the app detecting the day ending, saving that information to the SQL database, and then having it added to the user's graph data.

## My Contribution
I worked on the foundation and planning during the start of development, meaning I developed the UI, UX, and calculations on the homepage, along with how a user's profile and build will affect what their caloric goal should be. On the UI and UX side, I developed everything seen on the Homepage and Profile sections. I was also responsible for all the SQL database information besides the actual meals themselves, which are saved inside each meal category. I instead developed a separate database for micronutrients of the meals when they are added to save space and have better calculation efficiency. This SQL db was then used to reflect meals on the homepage

### Micronutrient Calculation & Math
Since the calculated micronutrients and calories are for a non-active person, this means that hitting these goals while also staying active will make you maintain your weight and put on muscle mass. Want to lose weight? Eat less than the full amounts, and the opposite is true if you want to bulk for personal or workout reasons. Since the micronutrient split of 40-30-30 is also protein-heavy (0.75 grams of protein per 1 gram of carb), this means that a workout routine with this split would also show results! This is by no means a full solution for everyone's diet, but for the average person, this diet would do wonders since it has been used by nutritionists for 35 years.

### Automated Testing
Each page has unit testing, including my profile page, which tests 'user' input, database saving and retrieving, and making sure only correct variables are allowed for profile inputs. The homepage has dedicated unit tests for the dropdown menu and food buttons. The homepage's information is calculated from the food pages' micronutrient information, meaning it is tested from the food page, which one of my group mates made extensive tests for. As long as the Profile unit test and food unit tests work, then the homepage's information will be reflected correctly. 

### Concepts
UI & UX design and implementation for the landing page and the profile page, database management for profile information along with the micronutrients for each day, and the color theme implementation and associated memory. Lastly, I did all the math involved with turning a user's profile into a daily recommendation for protein, carbs, and fat, which is represented both as a fraction and a visual bar.

## Demonstration
