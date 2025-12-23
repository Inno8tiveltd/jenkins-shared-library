def call(Map config) {
    sshagent(credentials: ['ec2-ssh-key']) {
        sh """
        ssh -o StrictHostKeyChecking=no ${config.server} '
            # Login to Nexus Docker registry
            echo "${config.nexusPassword}" | docker login ${config.registry} -u ${config.nexusUser} --password-stdin

            cd /home/ec2-user/node-app

            # Pull images and deploy with docker compose
            docker compose --env-file ${config.envFile} pull
            docker compose --env-file ${config.envFile} up -d
        '
        """
    }
}
