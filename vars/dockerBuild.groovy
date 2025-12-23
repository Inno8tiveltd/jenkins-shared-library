def call(steps, backend, frontend, userFrontend, tag) {
    steps.stage('Build Docker Images') {
        steps.sh """
            docker build -t ${backend}:${tag} backend
            docker build -t ${frontend}:${tag} frontend
            docker build -t ${userFrontend}:${tag} user-portal-frontend
        """
    }
}
