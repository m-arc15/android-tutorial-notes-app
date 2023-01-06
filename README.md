# Notes app built with Android

This tutorial will walk you through the process of building a simple notes app with Android

## Getting started

Make sure to complete [Android Studio installation](https://developer.android.com/studio/install). If you need more detailed instructions on the setup process, refer to the [Download and install Android Studio](https://developer.android.com/codelabs/basic-android-kotlin-training-install-android-studio) codelab.

## First steps

Create a new project using the template in Android Studio:
1. Double click the Android Studio icon to launch Android Studio.
2. In the **Welcome to Android Studio** dialog, click **New Project**. The **New Project** window opens with a list of templates provided by Android Studio.
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

Create a new Github Actions workflow to run local tests on all modules of the project:
1. In the Android Studio, click **View > Tool Windows > Project**
2. Select **Project** from drop-down list in the **Project** window on the left side of the Android Studio
3. Create `.github/workflows` directory in the root of Notes app
4. Create a new file `CI.yml` in `.github/workflows` to define CI workflow running local tests

📄 .github/workflows/CI.yml
```diff
+name: Run local tests
+
+on:
+  # Trigger the workflow manually using the Actions tab on GitHub, GitHub CLI, or the REST API
+  workflow_dispatch:
+  # Trigger the workflow on Push event to main branch
+  push:
+    branches: [ main ]
+  # Trigger the workflow on Pull Request event. By default, a workflow only runs
+  # when a Pull Request event's activity type is opened, synchronize or reopened.
+  pull_request:
+    types: [ opened, synchronize, reopened ]
+
+# Cancel any current or previous job from the same Pull Request
+concurrency:
+  group: ${{ github.workflow }}-${{ github.ref }}
+  cancel-in-progress: true
+
+jobs:
+  local-tests:
+    name: Run Local Tests
+    runs-on: ubuntu-latest
+    timeout-minutes: 10
+    continue-on-error: false
+
+    steps:
+      - name: Checkout repository
+        uses: actions/checkout@v3
+
+      - name: Validate Gradle wrapper
+        uses: gradle/wrapper-validation-action@v1
+
+      - name: Set up JDK 11
+        uses: actions/setup-java@v3
+        with:
+          distribution: 'zulu'
+          java-version: '11'
+          cache: 'gradle'
+
+      - name: Run local tests
+        run: ./gradlew testDebug --no-daemon
+
+      - name: Upload build reports
+        if: always()
+        uses: actions/upload-artifact@v3
+        with:
+          name: build-reports
+          path: ./**/build/reports/tests/
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


