def call() {
    def branch = env.BRANCH_NAME
    echo "Deploying for branch: ${branch}"

    withCredentials([
        string(credentialsId: 'DEV_SERVER_IP', variable: 'DEV_IP'),
        string(credentialsId: 'STAGE_SERVER_IP', variable: 'STAGE_IP'),
        string(credentialsId: 'PROD_SERVER_IP', variable: 'PROD_IP'),
        usernamePassword(credentialsId: 'nexus-docker-creds',
                         usernameVariable: 'NEXUS_USER',
                         passwordVariable: 'NEXUS_PASSWORD')
    ]) {

        def serverIP
        def envFile

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

        deploy(
            server: "ec2-user@${serverIP}",
            envFile: envFile,
            registry: "13.251.194.219:8083",
            nexusUser: NEXUS_USER,
            nexusPassword: NEXUS_PASSWORD
        )
    }
}
