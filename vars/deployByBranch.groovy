def deployByBranch() {
    if (env.BRANCH_NAME == 'dev') {
        return [ env: 'dev', serverCred: 'DEV_SERVER_IP', tag: "dev-${env.BUILD_NUMBER}" ]
    }
    if (env.BRANCH_NAME == 'stage') {
        return [ env: 'stage', serverCred: 'STAGE_SERVER_IP', tag: "stage-${env.BUILD_NUMBER}" ]
    }
    if (env.BRANCH_NAME == 'main') {
        return [ env: 'prod', serverCred: 'PROD_SERVER_IP', tag: "prod-${env.BUILD_NUMBER}" ]
    }
    error "Unsupported branch: ${env.BRANCH_NAME}"
}
