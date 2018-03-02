![CapTech](https://techchallenge.captechlab.com/tech-challenge-general/tech-challenge-creation/raw/d8435683d964afadb54685f35d7d69ad0cbeac70/images/Logo.png)

# Assignment 2: HTTP Request and JSON Parsing
This assignment, the second in the series, will familiarize the student with HTTP based network communications and XML parsing on the target platform.

## Table of Contents

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->


- [Overview](#overview)
- [Learning Objectives](#learning-objectives)
- [Prerequisites](#prerequisites)
- [Grading Criteria](#grading-criteria)
- [Helpful Resources](#helpful-resources)
- [Submission Instructions](#submission-instructions)
  - [Git Workflow](#git-workflow)
- [Help and Discussion](#help-and-discussion)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Overview
In this tech challenge, you will be creating an app that displays a list of earthquakes retrieved by the U.S. Geological Survey. Selecting an item in the list will allow the user to folow a link to further infromation on the USGS website in their browser.

## Learning Objectives
When completed the student should have an understanding of the following concepts:

* HTTP request and response handling
* JSON parsing
* String manipulation
* Display theming and modifications including custom table rows
* Table row manipulation

## Prerequisites
Before starting this tech challenge, a student should be familiar with the follow concepts and have the following environment

* All prior Android Assignments are completed
* The Android SDK installed on the development device
* Android Studio (the latest stable release) installed on the development device

## Grading Criteria
Here are some specific requirements the graders will be looking for in your submission in order for your submission to be accepted:

The application must do the following:

* Retrieve historical earthquake data from the U.S. Geological Survey (USGS) and produce a table displaying information about earthquakes that have occurred in the past 30 days. The recommended approach section has a URL for the data.
* The view of earthquake events must be sorted in descending date order.
* Each item must display the following information in a pleasing and easy to read format:
    * The USGS title for the quake
    * The latitude and longitude of the quake
    * The time the quake occurred in local time
* The user must be able to force a reload of the view.
* When the user reloads the view, the server should be re-queried and the view reloaded with all quake data received from the server
* The user must be able to selectively delete table items using a gesture familiar to uses of that platform.
* If the user selects a row the app should open an internal web browser control (native only) with a link to the full quake information at the USGS site.
* The items with an earthquake of magnitude 5 or higher must be shaded in light red.
* The items with a quake of magnitude 7 or higher must be shaded in red.
* The application must cleanly and correctly handle network failures or the absence of a network.
* The application must display some indicator that the application is busy during data loads.
* The application UI must remain responsive to user input during the data load process.
* The application must not crash or leak memory.
* The structure of the application must be well structured and understandable.
* If you’re completing this task using the mobile web technology, there may be no server side logic other than delivery of HTML/JS/CSS content to the phone. All other logic, storage, etc. must occur on the phone itself.

## Helpful Resources
These are some helpful documentation links and resources to help you be successful in completing this tech challenge:

* Data feed: 
    * [http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson](http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson)
    * [http://earthquake.usgs.gov/earthquakes/catalogs/](http://earthquake.usgs.gov/earthquakes/catalogs/)
* Web resources
    * [http://developer.android.com/guide/topics/ui/layout/listview.html](http://developer.android.com/guide/topics/ui/layout/listview.html)
    * [https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html)
    * [https://developer.android.com/training/material/lists-cards.html](https://developer.android.com/training/material/lists-cards.html)
    * [http://developer.android.com/training/basics/network-ops/connecting.html](http://developer.android.com/training/basics/network-ops/connecting.html)
    * [http://developer.android.com/reference/android/util/JsonReader.html](http://developer.android.com/reference/android/util/JsonReader.html)

## Submission Instructions
Please provide these specific items when submitting your tech challenge, placing them inside of your repository where your grader can easily find them:

* An application that meets the above requirements running on the target mobile device is required for completion of this assignment. Please place the `.apk` file of the completed app in the base directory of your repository.
* The code for your submission should be at the base directory of the repo, or within a specified child directory.

### Git Workflow
* When you start a tech challenge, the tech challenge admin will create a private repository for you.
* There will be two branches in the repo, `master` and `develop`.
* Do all your work in the `develop` branch.
* As you work, push your changes up to your GitLab repo.
* When you are finished, make a merge request to the `master` version of your private repo.
* At this point, the graders will review your merge request and offer comments where needed.
* You may be asked to push updates, corrections to your develop branch in response to the coments by graders. You `don't` need to create another merge request.
* When the grader is satisfied, he will merge your code into the master branch of your private repo.
* Now celebrate, the Tech Challenge is completed!

## Help and Discussion

If you need help on this Tech Challenge or would like to discuss it / leave feedback, please check out the [Mobile Tech Challenges team](https://teams.microsoft.com/l/team/19:3c6cb4196d9d47b9ad2510126e14ad44@thread.skype/) in Microsoft Teams, especially the [Help and Discussion channel](https://teams.microsoft.com/l/channel/19:bb28957ec53d452787c8c3aeae850127@thread.skype/Mobile%20-%20Android%20-%20Help%20and%20Discussion).

