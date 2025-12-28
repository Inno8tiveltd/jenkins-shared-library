def call() {
    if (env.BRANCH_NAME == 'dev') {
        return 'DEV_SERVER_IP'
    }
    if (env.BRANCH_NAME == 'stage') {
        return 'STAGE_SERVER_IP'
    }
    if (env.BRANCH_NAME == 'prod') {
        return 'PROD_SERVER_IP'
    }
    error "Unsupported branch: ${env.BRANCH_NAME}"
}
