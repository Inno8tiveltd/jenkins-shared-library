def call(steps, String backend, String frontend, String tag) {
    steps.stage('Build Backend Docker Image') {
        steps.sh "docker build -t ${backend}:${tag} backend"
    }

    steps.stage('Build Frontend Docker Image') {
        steps.sh "docker build -t ${frontend}:${tag} frontend"
    }
}
