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
