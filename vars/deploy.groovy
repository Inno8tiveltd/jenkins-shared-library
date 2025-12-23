def call(Map config) {
    sshagent(credentials: ['ec2-ssh-key']) {
        withCredentials([usernamePassword(
            credentialsId: 'nexus-docker-creds',
            usernameVariable: 'NEXUS_USER',
            passwordVariable: 'NEXUS_PASSWORD'
        )]) {
            sh """
            ssh -o StrictHostKeyChecking=no ${config.server} '
                # Login to Nexus Docker registry
                echo "$NEXUS_PASSWORD" | docker login ${config.registry} -u $NEXUS_USER --password-stdin
                cd /home/ec2-user/node-app
                docker compose --env-file ${config.envFile} pull
                docker compose --env-file ${config.envFile} up -d
            '
            """
        }
    }
}
