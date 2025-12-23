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
    'node-backend',
    'node-frontend',
    'user-frontend',
    env.IMAGE_TAG
)


    pushToRegistry(this, [
    registry          : env.NEXUS_REGISTRY,
    repo              : env.NEXUS_REPO,
    backendImage      : 'node-backend',
    frontendImage     : 'node-frontend',
    userFrontendImage : 'user-frontend',   // 🔥 THIS WAS NULL
    tag               : env.IMAGE_TAG,      // 🔥 MAKE SURE THIS IS NOT NULL
    credsId           : 'nexus-docker-creds'
])
}
