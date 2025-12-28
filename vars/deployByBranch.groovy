def call() {
    def branch = env.BRANCH_NAME
    def build = env.BUILD_NUMBER
    def shortSha = env.GIT_COMMIT?.take(7)

    if (branch == 'dev') {
        return "dev-${build}"
    }
    if (branch == 'stage') {
        return "stage-${build}"
    }
    if (branch == 'main' || branch == 'prod') {
        return "prod-${shortSha}"
    }

    error "Unsupported branch: ${branch}"
}
