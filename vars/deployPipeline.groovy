def call() {
    echo "🚀 Deploy triggered for branch: ${env.BRANCH_NAME}"

    // Get image tag & server credential ID
    def tag = deployByBranch()
    def serverCredId = serverCredByBranch()

    echo "🔐 Using credential ID: ${serverCredId}"

    // Bind all credentials here
    withCredentials([
        string(credentialsId: serverCredId, variable: 'SERVER_IP'),
        usernamePassword(
            credentialsId: 'nexus-docker-creds',
            usernameVariable: 'NEXUS_USER',
            passwordVariable: 'NEXUS_PASS'
        )
    ]) {
        sshagent(credentials: ['ec2-ssh-key']) {
            // Call deploy with all required info
            deploy(
                server: "ec2-user@${SERVER_IP}",
                registry: "13.251.194.219:8083",
                envFile: ".env.${env.BRANCH_NAME}",
                imageTag: tag,
                serverIp: SERVER_IP,
                nexusUser: NEXUS_USER,
                nexusPass: NEXUS_PASS
            )
        }
    }
}
