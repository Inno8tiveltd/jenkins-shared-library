def call(steps, backend, frontend, userFrontend, tag) {
    // Default tag to 'latest' if null or empty
    tag = tag?.trim() ? tag : 'latest'

    steps.stage('Build Docker Images') {
        echo "Building Docker images with tag: ${tag}"
        steps.sh """
            docker build -t ${backend}:${tag} backend
            docker build -t ${frontend}:${tag} frontend
            docker build -t ${userFrontend}:${tag} user-portal-frontend
        """
    }
}
