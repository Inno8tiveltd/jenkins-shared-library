def call() {
    def branch = env.BRANCH_NAME?.trim()

    echo "🔍 Raw BRANCH_NAME = '${branch}'"

    if (branch == 'dev') {
        return 'DEV_SERVER_IP'
    }
    if (branch == 'stage') {
        return 'STAGE_SERVER_IP'
    }
    if (branch == 'prod') {
        return 'PROD_SERVER_IP'
    }

    error """
❌ Unsupported branch detected
Expected : dev | stage | prod
Actual   : ${branch}
"""
}
