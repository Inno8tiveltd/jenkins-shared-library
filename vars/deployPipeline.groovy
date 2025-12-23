def call() {

    def branch = env.BRANCH_NAME
    echo "Deploying for branch: ${branch}"

    if (branch == 'dev') {
        deploy(
            envName: 'dev',
            server: 'ec2-user@DEV_SERVER_IP',
            envFile: '.env.dev'
        )
    }
    else if (branch == 'stage') {
        deploy(
            envName: 'stage',
            server: 'ec2-user@STAGE_SERVER_IP',
            envFile: '.env.stage'
        )
    }
    else if (branch == 'main' || branch == 'prod') {
        deploy(
            envName: 'prod',
            server: 'ec2-user@PROD_SERVER_IP',
            envFile: '.env.prod'
        )
    }
    else {
        echo "No deployment for branch: ${branch}"
    }
}
