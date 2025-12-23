def call(steps, backend, frontend, userFrontend) {
    steps.stage('Build Docker Images') {
        steps.sh """
            docker build -t ${backend}:latest backend
            docker build -t ${frontend}:latest frontend
            docker build -t ${userFrontend}:latest user-portal-frontend
        """
    }
}
