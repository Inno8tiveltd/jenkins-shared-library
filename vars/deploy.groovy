def call(Map config) {

    sshagent(credentials: ['ec2-ssh-key']) {
        sh """
        ssh -o StrictHostKeyChecking=no ${config.server} '
            cd /home/ec2-user/node-app &&
            docker compose --env-file ${config.envFile} pull &&
            docker compose --env-file ${config.envFile} up -d
        '
        """
    }
}
