Project Proposal Wiki Page: https://git.uwaterloo.ca/g24kang/team-101-10/-/wikis/Project-Proposal

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

_08-February-2024
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

Link to Installer


