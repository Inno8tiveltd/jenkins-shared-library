def call() {
    def branch = env.BRANCH_NAME?.trim()

    if (branch == 'dev') {
        return 'dev-latest'
    }
    if (branch == 'stage') {
        return 'stage-latest'
    }
    if (branch == 'prod') {
        return 'prod-latest'
    }

    error "Unsupported branch for tagging: ${branch}"
}
