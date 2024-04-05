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
V 0.25_


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
V 0.50_


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
V 0.75_

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

**SPRINT 4:**

_5-April-2024
V 1.0_

_Features/Tickets Completed (more details in development journal):_

- **Consistency:**
   - [Convert create offer page into a Composable](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/38)
   - [Convert search offers screen from Activity to Composable](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/41)
   - [Add navbar to each page - ensure it is persistent](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/43)
- Worked on offer search and offer details screens to finalize design for images and functionality
   - [Display correct image on the search offers page](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/39)
   - [Add offer image to each food offer](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/40)
- Instead of creating a messaging tool within the app, (which would distract from the primary goals of the app), we use email as a tool for handling communications regarding offers/requests
   - [Implement email service for accepted offers](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/42)
- Made some stylistic improvements that needed to be made to the MapsActivity and UserProfile and implemented a scrollbar
   - [Sprint 4 UI fixes, and implement a scrollbar](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/50)
- **Quality/Style:**
   - [Redirect back to search offers page after offer is accepted](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/44)
   - [Add back button to offer details page](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/45)
   - [add input validation to the create offer screen](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/46)
   - [User should not need to log in each time they open the app](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/48)
   - [Fix photo picker crashing issue](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/47)
   - [Unfocus textbox when tapping outside](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/27)
   - [Text box highlights and other accents don't match green theme](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/49)
- **Testing:**
   - [set up testing dependencies and first few unit tests](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/51)
   - [Core Functionality Unit Tests](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/52)
- [Ability to open up locations in Google Maps](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/6)
- [Ability to include images in offering/requesting posts](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/10)
- **Final Submission:**
   - [Completed the class diagram for the Design Documentation](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/54)
   - [Completed the component diagram for the Design Documentation](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/55)
  - [Updated README for final project submission](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/53)
  - Created wikis for [Design Documentation](https://git.uwaterloo.ca/g24kang/team-101-10/-/wikis/Design-Documentation) and [User Documentation](https://git.uwaterloo.ca/g24kang/team-101-10/-/wikis/User-Documentation)
  - [Started working on User Documentation](https://git.uwaterloo.ca/g24kang/team-101-10/-/issues/56)


[Link to Installer](https://git.uwaterloo.ca/g24kang/team-101-10/-/blob/main/Releases/v0.4/app-debug.apk)