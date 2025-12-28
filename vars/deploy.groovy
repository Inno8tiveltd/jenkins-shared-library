def call(Map cfg) {
    withCredentials([
        string(credentialsId: env.SERVER_CRED, variable: 'SERVER_IP'),
        usernamePassword(
            credentialsId: 'nexus-docker-creds',
            usernameVariable: 'NEXUS_USER',
            passwordVariable: 'NEXUS_PASS'
        )
    ]) {
        sshagent(credentials: ['ec2-ssh-key']) {
            sh """
            ssh -o StrictHostKeyChecking=no ec2-user@${SERVER_IP} '
                echo "TAG=${cfg.imageTag}" > /home/ec2-user/node-app/.env.runtime
                cat ${cfg.envFile} >> /home/ec2-user/node-app/.env.runtime

                docker login ${cfg.registry} -u ${NEXUS_USER} -p ${NEXUS_PASS}

                cd /home/ec2-user/node-app
                docker compose --env-file .env.runtime pull
                docker compose --env-file .env.runtime up -d
            '
            """
        }
    }
}
