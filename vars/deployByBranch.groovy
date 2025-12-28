def call() {
    if (env.BRANCH_NAME == 'dev') {
        return "dev-${env.BUILD_NUMBER}"
    }
    if (env.BRANCH_NAME == 'stage') {
        return "stage-${env.BUILD_NUMBER}"
    }
    if (env.BRANCH_NAME == 'prod') {
        return "prod-${env.GIT_COMMIT.take(7)}"
    }
    error "Unsupported branch: ${env.BRANCH_NAME}"
}
