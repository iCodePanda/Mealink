**MEALINK**


**_Description:_**

MeaLink is a platform that can connect Food Donors and Food Receivers with ease. Who are these 2 user groups?

Many communities struggle with food insecurity and organizations set up to help them often lack funding to create a quality experience (Food Receivers). On the other hand, the food industry struggles with excess food, and in turn food wastage on a mass scale (Food Donors). These two industries fit together like a puzzle piece for mutual benefit, and overall societal good. Restaurants with excess food or ingredients can connect with organizations that can make use of those resources to provide a quality experience for their population. This way, the restaurants find a convenient and simple way to practice corporate social responsibility (CSR) within their own communities, build trust within their customer base, and develop brand loyalty. On the other hand, the homeless shelters or welfare facilities benefit by being able to provide their population with diversified and quality meals and snacks, going beyond just the necessities, which in turn empowers the populations in their rehabilitation.

Our app connects these 2 users, and makes the coordination of food simple, quick, and easy to understand.


**_Screenshot and/or introduction video_**

**_Meet the Team_**
- Daniel Lu
- Kanwal Jamal
- Gursimran Kang
- Teng Ma


[**_User documentation Link_**](https://git.uwaterloo.ca/g24kang/team-101-10/-/wikis/User-Documentation)

[**_Design Documentation Link_**](https://git.uwaterloo.ca/g24kang/team-101-10/-/wikis/Design-Documentation)

**_List of Releases_**
- See below

[**_Meeting Minutes_**](https://git.uwaterloo.ca/g24kang/team-101-10/-/wikis/Development-Journal)

**_Individual Developer Journals_**

- [Daniel Lu](https://git.uwaterloo.ca/g24kang/team-101-10/-/wikis/Daniel's-Journal)
- [Kanwal Jamal](https://git.uwaterloo.ca/g24kang/team-101-10/-/wikis/Kanwal's-Journal)
- [Gursimran Kang](https://git.uwaterloo.ca/g24kang/team-101-10/-/wikis/Gursimran's-Journal)
- [Teng Ma](https://git.uwaterloo.ca/g24kang/team-101-10/-/wikis/Teng's-Journal)


**_Project Proposal Wiki Page:_** https://git.uwaterloo.ca/g24kang/team-101-10/-/wikis/Project-Proposal

**SPRINT 1:**

_15-February-2024
V 0.1_


_Tickets Completed (more details in development journal):_

- [Initial DB setup](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/11)
- [Set up DB table for Food-Items](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/12)
- [Issue Set up DB table for Users](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/13)
- [Account Creation - Email](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/14)
- [Update Sign-up Page to Collect Required Fields for Users Collection in Firebase DB](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/19)
- [Implement Sign-in Page (UI and Functionality)](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/21)
- [Make the database document ID for a user the same as the UID when they sign up](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/22)
- [Create Home Page](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/23)

_Summary:_

This version contains:
- Firebase cloud computing service setup
- Home page complete with buttons for Sign-up, Sign-In, and placeholder for Google Sign-in (Will be done in next release)
- Firebase Database set up with tables for Users and Food-Items
- User sign up with email and additional required fields which persist to the database collection (Users)
- User sign in with authentication
- Firebase Authentication Completed
- Authenticated user is directly mapped to user record in Users collection in DB according to Primary Key: User UID 
- Error handling for incomplete sign-up form, or incorrect credentials upon sign-in


[Link to Installer](https://git.uwaterloo.ca/g24kang/team-101-10/-/blob/a00974858b9f8ce5d77211fa2313f23bfbac6189/Releases/v0.1/app-debug.apk)


**SPRINT 2:**

_08-March-2024
V 0.2_


_Features/Tickets Completed (more details in development journal):_

Finish Integrating Google Maps API:
- [Integrate Google Maps API](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/17)

Sign-up page refactors:
- [Success Validation Messages](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/32)
- [Sign-up page refactors](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/28)


Profile Picture Uploading:
- [Profile Picture Uploads](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/29)

User Profile Building:
- [User Profile Back End](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/26)
- [User Profile Front End](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/25)
- [Populate User Profile Screen](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/31)



_Summary:_

This version contains:
- Integrated Google Maps API ready to be used throughout the application
- Refactors made to the sign-up page to provide validation and success messages for better user experience
- Base user profile where users can make changes to their account such as name and location, and have those changes persist to the database
- Ability to change profile picture within user profile

[Link to Installer](https://git.uwaterloo.ca/g24kang/team-101-10/-/blob/bb595f2be547a1ab595df110ffdbd34e16aec338/Releases/v0.2/app-debug.apk)


**SPRINT 3:**

_22-March-2024
V 0.3_

_Features/Tickets Completed (more details in development journal):_

- [Implement navigation bar](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/34)
  - Refactor the app and put each UI page into a function with a class that can act as a navigator between them. This would follow the MVVM model -> Navigation bar works as expected

  - Should migrate app to single-activity design before bottom nav-bar is fully integrated to app. Started migration with https://git.uwaterloo.ca/g24kang/team-101-10/-/commit/f1268e7874f0a74b330cec2a3568fecdd24282bc

- [Set up database schema/relations for an offerEntity](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/7)

- Adding a "Create an Offer" page functionality, both front-end and back-end
  - [Set up buttons/routing for ability to create a food offering](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/8)
  - [Front-end and functionality for creating a food offering](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/35)

- Adding a "Search Offers" page functionality, both front-end and back-end
  - [Set up buttons/routing for ability search for offerings](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/4)
  - [Functionality for searching through food offerings](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/36)

- [Implement secure Sign-out and rerouting](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/37)

_Summary:_

This version contains:
- Functioning navigation bar and refactored application
- Routing to "Create and Offer" page for foodDonors
- Routing to "Search Offers" page for foodReceivers
  - Ability to search offers in a list and map format 
- Secure sign-out and redirection implemented


[Link to Installer](https://git.uwaterloo.ca/g24kang/team-101-10/-/blob/main/Releases/v0.3/app-debug.apk)
