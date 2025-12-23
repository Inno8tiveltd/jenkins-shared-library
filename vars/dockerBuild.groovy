def call(Map config) {

    def tag = config.tag ?: 'latest'

    stage('Build Backend') {
        sh "docker build -t ${config.backend}:${tag} backend"
    }

    stage('Build Frontend') {
        sh "docker build -t ${config.frontend}:${tag} frontend"
    }

    if (config.userFrontend) {
        stage('Build User Frontend') {
            sh "docker build -t ${config.userFrontend}:${tag} user-portal-frontend"
        }
    }
}
