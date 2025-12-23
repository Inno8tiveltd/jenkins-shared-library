def call(steps, String projectKey, String org, String sources) {
    steps.stage('SonarQube Analysis') {
        def scannerHome = steps.tool 'SonarScanner'
        steps.withSonarQubeEnv('SonarQube') {
            steps.sh """
            ${scannerHome}/bin/sonar-scanner \
              -Dsonar.projectKey=${projectKey} \
              -Dsonar.organization=${org} \
              -Dsonar.sources=${sources}
            """
        }
    }
}
