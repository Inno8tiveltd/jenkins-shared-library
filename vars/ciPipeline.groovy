def call() {

    // 🔥 DEFINE IMAGE NAMES & TAG HERE
    env.BACKEND_IMAGE       = "node-backend"
    env.FRONTEND_IMAGE      = "node-frontend"
    env.USER_FRONTEND_IMAGE = "user-frontend"
    env.IMAGE_TAG           = "latest"   // no build number

    stage('Build Docker Images') {
        dockerBuild(
            this,
            env.BACKEND_IMAGE,
            env.FRONTEND_IMAGE,
            env.USER_FRONTEND_IMAGE,
            env.IMAGE_TAG
        )
    }

    stage('Push to Nexus') {
        pushToRegistry(this, [
            registry      : "13.251.194.219:8083",
            repo          : "docker-hosted",
            backendImage  : env.BACKEND_IMAGE,
            frontendImage : env.FRONTEND_IMAGE,
            userFrontend  : env.USER_FRONTEND_IMAGE,
            tag           : env.IMAGE_TAG,
            credsId       : "nexus-docker-creds"
        ])
    }
}
