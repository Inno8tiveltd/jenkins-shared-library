def call(Map config) {
    sshagent(credentials: ['ec2-ssh-key']) {
        sh """
        ssh -o StrictHostKeyChecking=no ${config.server} '
            echo "${config.nexusPassword}" | docker login ${config.registry} \
              -u ${config.nexusUser} --password-stdin

            cd /home/ec2-user/node-app
            docker compose --env-file ${config.envFile} pull
            docker compose --env-file ${config.envFile} up -d
        '
        """
    }
}
