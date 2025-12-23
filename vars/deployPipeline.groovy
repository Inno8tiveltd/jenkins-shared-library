def call() {
    def branch = env.BRANCH_NAME
    echo "Deploying for branch: ${branch}"

    // Wrap all string credentials here
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
        } else if (branch == 'stage') {
            serverIP = STAGE_IP
            envFile = '.env.stage'
        } else if (branch == 'main' || branch == 'prod') {
            serverIP = PROD_IP
            envFile = '.env.prod'
        } else {
            echo "No deployment for branch: ${branch}"
            return
        }

        echo "Deploying branch ${branch} to ${serverIP}"

        // SSH agent with your PEM key
        sshagent(credentials: ['ec2-ssh-key']) {
            sh """
            ssh -o StrictHostKeyChecking=no ec2-user@${serverIP} '
                cd /home/ec2-user/node-app &&
                docker compose --env-file ${envFile} pull &&
                docker compose --env-file ${envFile} up -d
            '
            """
        }
    }
}
