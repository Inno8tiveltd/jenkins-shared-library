## Jenkins Shared Library Setup

### Overview

We implemented a Jenkins Shared Library to centralize and standardize CI/CD pipeline logic across multiple projects. This approach eliminates duplication, improves maintainability, and ensures consistent pipeline behavior for all services.

---

### What We Delivered

We created a reusable Jenkins Shared Library containing common pipeline stages as functions:

* **buildStage()**
  Handles application build logic (e.g., Maven/Gradle/npm build).

* **testStage()**
  Executes unit and integration tests with standardized reporting.

* **sonarScan()**
  Performs static code analysis using SonarQube and enforces quality gates.

* **dockerBuild()**
  Builds Docker images using a consistent tagging and build strategy.

* **pushToRegistry()**
  Pushes Docker images to the configured container registry (ECR / JFrog / Docker Hub).

* **notifySlack()**
  Sends pipeline status notifications (success/failure) to Slack channels.

---

### Key Design Highlights

* Pipeline logic is written once and reused across all Jenkins jobs.
* Application-specific Jenkinsfiles remain minimal and easy to read.
* Changes to CI/CD behavior can be done centrally without touching each repository.
* Encourages best practices and consistency across teams.

---

### Outcome

* **Standardized CI/CD stages** across multiple microservices and repositories.
* **Faster onboarding** for new projects—teams only need to call shared functions.
* **Reduced pipeline duplication**, leading to cleaner Jenkinsfiles.
* **Easier maintenance** since updates are managed in one central location.
* Improved reliability and governance of CI/CD workflows.

---

### Final Result

The Jenkins Shared Library acts as a single source of truth for CI/CD logic, enabling scalable, maintainable, and enterprise-ready pipeline management.
======================================================

Let’s reduce it to the minimum possible code.

🎯 Goal

Jenkinsfile → orchestration only

Shared library → all logic, defaults, environment, tools

✅ Ultra-Clean Jenkinsfile (BEST PRACTICE)
🔹 Final Jenkinsfile (VERY LESS CODE)
@Library('ci-lib') _

pipeline {
    agent any

    stages {
        stage('CI') {
            steps {
                ciPipeline()
            }
        }
    }
}


That’s it.
No env, no docker, no sonar, no nexus clutter.
This is how real enterprise pipelines look.

🧠 Where did everything go?

➡️ Into the shared library.

📦 Shared Library Structure (Recommended)
jenkins-shared-library/
├── vars/
│   ├── ciPipeline.groovy
│   ├── dockerBuild.groovy
│   ├── sonarScan.groovy
│   ├── pushToRegistry.groovy
│   ├── buildStage.groovy
│   └── notifyEmail.groovy
└── resources/

🔧 vars/ciPipeline.groovy (Core Orchestrator)
def call() {

    buildStage(this)

    sonarScan(
        this,
        env.SONAR_KEY,
        env.SONAR_ORG,
        'frontend,backend'
    )

    dockerBuild(
        this,
        env.BACKEND_IMAGE,
        env.FRONTEND_IMAGE,
        env.IMAGE_TAG
    )

    pushToRegistry(this, [
        registry      : env.NEXUS_REGISTRY,
        repo          : env.NEXUS_REPO,
        backendImage  : env.BACKEND_IMAGE,
        frontendImage : env.FRONTEND_IMAGE,
        tag           : env.IMAGE_TAG,
        credsId       : 'nexus-docker-creds'
    ])
}

🌍 Global Defaults (One-time setup)
Jenkins → Manage Jenkins → Configure System → Global Properties

Add Environment variables:

NEXUS_REGISTRY=13.251.194.219:8083
NEXUS_REPO=docker-hosted
BACKEND_IMAGE=node-backend
FRONTEND_IMAGE=node-frontend
SONAR_ORG=sonar00732
SONAR_KEY=sonar00732-NodeJS-Application


And in shared lib you can generate tag:

env.IMAGE_TAG = env.BUILD_NUMBER

🏆 Result
Before ❌

60+ lines Jenkinsfile

Repeated env everywhere

Hard to maintain

After ✅

8 lines Jenkinsfile

Centralized logic

Easy onboarding

Same pipeline for 10–100 repos

💬 Interview-ready explanation (IMPORTANT)

“Our Jenkinsfiles are intentionally thin.
All CI/CD logic is centralized in a shared library so teams only call ciPipeline() and don’t worry about tooling, Docker, Sonar, or Nexus.”