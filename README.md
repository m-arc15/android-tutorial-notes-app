# Notes app built with Android

This tutorial will walk you through the process of building a simple notes app with Android

## Prerequisites

- [Test Desiderata](https://kentbeck.github.io/TestDesiderata/)
- [Don't do E2E testing](https://youtu.be/QFCHSEHgqFE)

## Getting started

Make sure to complete [Android Studio installation](https://developer.android.com/studio/install).
If you need more detailed instructions on the setup process, refer to
the [Download and install Android Studio](https://developer.android.com/codelabs/basic-android-kotlin-training-install-android-studio)
codelab.

## First steps

Create a new project using the template in Android Studio:

1. Double click the Android Studio icon to launch Android Studio.
2. In the **Welcome to Android Studio** dialog, click **New Project**. The **New Project** window
   opens with a list of templates provided by Android Studio.
3. Make sure the **Phone and Tablet** tab is selected.
4. Click the **Empty Compose Activity (Material3)** template to select it as the template for your project. The **Empty Compose Activity template** is the template to create a simple project that you can use to build a Compose app. It has a single screen and displays the text `"Hello Android!"`.
5. Click **Next**. The **New Project** dialog opens. This has some fields to configure your project.
6. Configure your project as follows:
- the **Name** field - is used to enter the name of your project, for this tutorial type "Notes"
- the **Package name** fields - is used for unique identification for your application, for this tutorial type "com.tutorial.notes"
- leave the **Save location** field as is. It contains the location where all the files related to your project are saved.
- **Kotlin** is already selected in the **Language** field. Language defines which programming language you want to use for your project. Since Compose is only compatible with Kotlin, you cannot alter this field.
- select **API 29: Android 10.0 (Q)** from the menu in the **Minimum SDK** field. Minimum SDK indicates the minimum version of Android Operating System that your app can run on.
- the **Use legacy android.support libraries** checkbox is already unchecked.
7. Hit **Finish**. This may take a while. While Android Studio is setting up, a progress bar and message indicates whether Android Studio is still setting up your project.
8. You may see a **What's New** pane which contains updates on new features in Android Studio. Close it for now.
9. Click **Split** on the top right of Android Studio, this allows you to view both code and design. You can also click **Code** to view code only or click **Design** to view design only.
10. In the **Design** view, you will see a blank pane with this text: ⚠️ A successful build is needed before the preview can be displayed. **Build & Refresh...**
11. Click **Build & Refresh**. It may take a while to build but when it is done the preview shows a text box that says "**Hello Android!**". Empty Compose activity contains all the code necessary to create this app.
12. Select **app** from run configuration drop-down list and click the **Run** button. It may take a while to build your app and install on connected emulator or android device. When it is done the Notes app will be launched and displayed.

## Set up CI to run local tests

Create a new Github Actions workflow to run local and Android tests:
1. In the Android Studio, click **View > Tool Windows > Project**
2. Select **Project** from drop-down list in the **Project** window on the left side of the Android Studio
3. Create `.github/workflows` directory in the root of Notes app
4. Create a new file `CI.yml` in `.github/workflows` to define CI workflow running local tests

📄 .github/workflows/CI.yml
```diff
name: CI

on:
  workflow_dispatch:
  push:
    branches: [ main ]
    paths-ignore:
      - 'README.md'
  pull_request:
    paths-ignore:
      - 'README.md'

env:
  CACHE_VERSION: 1 # Increment this to invalidate the cache.
  JAVA_VERSION: 11

# Cancel any current or previous job from the same Pull Request
concurrency:
  group: ${{ github.workflow }}-${{ github.ref }}
  cancel-in-progress: true

jobs:
  local-tests:
    name: Check local tests
    runs-on: ubuntu-latest
    timeout-minutes: 10
    continue-on-error: false

    steps:
      - name: Checkout repository
        uses: actions/checkout@v3

      - name: Validate Gradle wrapper
        uses: gradle/wrapper-validation-action@v1

      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          distribution: 'zulu'
          java-version: ${{ env.JAVA_VERSION }}
          cache: 'gradle'

      - name: Run local tests (JVM)
        run: ./gradlew testDebug --no-daemon

      - name: Upload local tests reports
        if: always()
        uses: actions/upload-artifact@v3
        with:
          name: local-tests-report
          path: ./**/build/reports/tests/

  android-tests:
    name: Check android tests
    runs-on: macOS-latest
    timeout-minutes: 10
    continue-on-error: false
    strategy:
      fail-fast: true
      matrix:
        api-level: [ 31 ]

    steps:
      - name: Checkout repository
        uses: actions/checkout@v3

      - name: Validate Gradle wrapper
        uses: gradle/wrapper-validation-action@v1

      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          distribution: 'zulu'
          java-version: ${{ env.JAVA_VERSION }}
          cache: 'gradle'

      # API 30+ emulators only have x86_64 system images.
      - name: Get AVD info
        uses: ./.github/actions/get-avd-info
        id: avd-info
        with:
          api-level: ${{ matrix.api-level }}

      # Retrieve the cached emulator snapshot.
      - uses: actions/cache@v3
        id: avd-cache
        with:
          path: |
            ~/.android/avd/*
            ~/.android/adb*
          key: ${{ runner.os }}-avd-${{ env.CACHE_VERSION }}-${{ steps.avd-info.outputs.arch }}-${{ steps.avd-info.outputs.target }}-${{ matrix.api-level }}

      # Create a new emulator snapshot if it isn't present in the cache.
      - name: Create AVD snapshot
        if: steps.avd-cache.outputs.cache-hit != 'true'
        uses: reactivecircus/android-emulator-runner@v2
        with:
          api-level: ${{ matrix.api-level }}
          arch: ${{ steps.avd-info.outputs.arch }}
          target: ${{ steps.avd-info.outputs.target }}
          disable-animations: false
          force-avd-creation: false
          ram-size: 4096M
          emulator-options: -no-window -gpu swiftshader_indirect -noaudio -no-boot-anim -camera-back none
          script: echo "Generated AVD snapshot for caching."

      - name: Run android tests (real device or emulator)
        uses: reactivecircus/android-emulator-runner@v2
        with:
          api-level: ${{ matrix.api-level }}
          target: ${{ steps.avd-info.outputs.target }}
          arch: ${{ steps.avd-info.outputs.arch }}
          disable-animations: true
          force-avd-creation: false
          ram-size: 4096M
          emulator-options: -no-window -gpu swiftshader_indirect -noaudio -no-boot-anim -camera-back none -no-snapshot-save
          script: ./gradlew connectedDebugAndroidTest

      - name: Upload android tests reports
        if: always()
        uses: actions/upload-artifact@v3
        with:
          name: android-tests-report
          path: ./**/build/reports/androidTests/

```

5. Create a new file `action.yml` in `.github/actions/get-avd-info` to resolve AVD info for environment constants

📄 .github/actions/get-avd-info/action.yml
```diff
name: 'Get AVD Info'
description: 'Get the AVD info based on its API level.'
inputs:
  api-level:
    required: true
outputs:
  arch:
    value: ${{ steps.get-avd-arch.outputs.arch }}
  target:
    value: ${{ steps.get-avd-target.outputs.target }}
runs:
  using: "composite"
  steps:
    - id: get-avd-arch
      run: echo "::set-output name=arch::$(if [ ${{ inputs.api-level }} -ge 30 ]; then echo x86_64; else echo x86; fi)"
      shell: bash
    - id: get-avd-target
      run: echo "::set-output name=target::$(echo default)"
      shell: bash

```

## TDD

What is TDD?
- It's a **software development methodology** in which tests **drive** the development of the application.

Advantages:
- Great understanding of the product requirements
- Faster development
- Better design - high cohesion, low coupling
- Less defects
- Promotes quality culture
- Regression test suite
- Documentation

The 3 rules of TDD:
1. You may not write production code until you have written a failing unit test
2. You may not write more of a unit test than is sufficient to fail, and not compiling is failing
3. You may not write more production code than is sufficient to pass the currently failing test

Types of TDD:
1. Inside-out (Detroit school, Classicist, state based testing, black-box-testing)
- Kent Beck, Uncle Bob, Ron Jeffries
- No use of Mocks
- Tests only the end results (state)
- Emerging Design

Pros
- Faster refactoring

Cons
- Can be hard to identify what the issue is
- Redundant coverage

2. Outside-in (London school, Mockist, interaction testing, white-box-testing)
- Steve Freeman, Sandi Metz, J.B. Rainsberger
- Vast use of Mocks
- Testing the interactions
- Upfront Design

Pros
- easier to track down issues
- Upfront Design
- Enforces Architectural Design

Cons
- Refactoring the classes public API will require extra work on tests
- Might produce false positives

Which TDD style is best for us?
- The true value of tests is tracking down and correcting defects
- Outside-in starting with failing Acceptance Test, resolved with multiple Red > Green > Refactor cycles

## Set up project to let you test Compose code

First, add the following dependencies to the `build.gradle` file of the module containing your UI tests:

```groovy
+    // Test rules and transitive dependencies:
+    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")
+    // Needed for createAndroidComposeRule, but not createComposeRule:
+    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_version")
```

Then extract Compose code for Main Screen content to composable function:
```diff
@@ -16,15 +16,20 @@ class MainActivity : ComponentActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContent {
-            NotesTheme {
-                // A surface container using the 'background' color from the theme
-                Surface(
-                    modifier = Modifier.fillMaxSize(),
-                    color = MaterialTheme.colorScheme.background
-                ) {
-                    Greeting("Android")
-                }
-            }
+            MainScreen()
+        }
+    }
+}
+
+@Composable
+fun MainScreen() {
+    NotesTheme {
+        // A surface container using the 'background' color from the theme
+        Surface(
+            modifier = Modifier.fillMaxSize(),
+            color = MaterialTheme.colorScheme.background
+        ) {
+            Greeting("Android")
         }
     }
 }
```

Then create initial UI test for MainActivity:

📄 app/src/androidTest/java/com/package/MainActivityTest.kt
```diff
+import androidx.compose.ui.test.assertIsDisplayed
+import androidx.compose.ui.test.junit4.createAndroidComposeRule
+import androidx.compose.ui.test.onNodeWithText
+import org.junit.Rule
+import org.junit.Test
+
+class MainActivityTest {
+
+    @get:Rule
+    val composeTestRule = createAndroidComposeRule<MainActivity>()
+
+    @Test
+    fun displayHelloAndroid() {
+        composeTestRule.setContent {
+            MainScreen()
+        }
+
+        composeTestRule.onNodeWithText("Hello Android!").assertIsDisplayed()
+    }
+
+}
```

## Acceptance Test Driven Development (ATDD)

Let's create **NotesFeatureTests.kt** for Acceptance Tests (End-To-End level).

📄 app/src/androidTest/java/com/package/features/notes/NotesFeatureTests.kt
```diff
+package com.artishevsky.notes.features.notes
+
+import androidx.test.ext.junit.runners.AndroidJUnit4
+import androidx.test.filters.LargeTest
+import org.junit.runner.RunWith
+
+/*
+Feature: Capture and organize notes
+    As a user who needs to capture what's on his mind
+    I want to jot down quick thoughts
+    So that I can find them later when needed
+
+    Acceptance Criteria
+    - [ ] I will see my available notes saved in local database
+    - [ ] I can quickly capture a new note with title, description and pre-defined color
+    - [ ] I can remove selected note and undo this operation
+    - [ ] I can edit saved note
+    - [ ] I can quickly filter notes by title, date or color
+    - [ ] I can see 'No notes found' empty screen when no available notes
+    - [ ] I will be prompted if I try to leave note editing without save
+
+    Scenario 1:
+
+ */
+
+/**
+ * Large End-to-End tests for the notes feature.
+ */
+//@RunWith(AndroidJUnit4::class)
+//@LargeTest
+//class NotesFeatureTests {
+//
+//}
```

