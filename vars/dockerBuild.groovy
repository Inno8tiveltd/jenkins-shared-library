def call(steps, backend, frontend, userFrontend, tag) {
    steps.stage('Build Docker Images') {
        steps.echo "Building Docker images with tag: ${tag}"

        steps.sh """
            docker build -t ${backend}:${tag} backend
            docker build -t ${frontend}:${tag} frontend
            docker build -t ${userFrontend}:${tag} user-portal-frontend
        """
    }
}
