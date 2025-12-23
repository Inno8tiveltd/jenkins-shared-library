def call() {
    def branch = env.BRANCH_NAME
    echo "Deploying for branch: ${branch}"
    
    withCredentials([
        string(credentialsId: 'DEV_SERVER_IP', variable: 'DEV_IP'),
        string(credentialsId: 'STAGE_SERVER_IP', variable: 'STAGE_IP'),
        string(credentialsId: 'PROD_SERVER_IP', variable: 'PROD_IP')
    ]) {

        def serverIP = ''
        def envFile = ''

        if (branch == 'dev') {
            serverIP = DEV_IP
            envFile = '.env.dev'
        }
        else if (branch == 'stage') {
            serverIP = STAGE_IP
            envFile = '.env.stage'
        }
        else if (branch == 'main' || branch == 'prod') {
            serverIP = PROD_IP
            envFile = '.env.prod'
        }
        else {
            echo "No deployment for branch: ${branch}"
            return
        }

        echo "Deploying branch ${branch} to ${serverIP}"

        // 🔥 THIS WAS MISSING
        deploy(
            server: "ec2-user@${serverIP}",
            envFile: envFile
        )
    }
}
