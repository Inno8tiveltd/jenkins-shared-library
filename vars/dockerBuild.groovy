def call(steps, backend, frontend, userFrontend, tag) {

    steps.stage('Build Backend') {
        steps.sh "docker build -t ${backend}:${tag} backend"
    }

    steps.stage('Build Frontend') {
        steps.sh "docker build -t ${frontend}:${tag} frontend"
    }

    steps.stage('Build User Frontend') {
        steps.sh "docker build -t ${userFrontend}:${tag} user-portal-frontend"
    }
}
