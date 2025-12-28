def call(Map cfg) {
    sshagent(credentials: ['ec2-ssh-key']) {
        sh """
        ssh -o StrictHostKeyChecking=no ${cfg.server} '
            echo "TAG=${cfg.imageTag}" > /home/ec2-user/node-app/.env.runtime
            cat ${cfg.envFile} >> /home/ec2-user/node-app/.env.runtime

            docker login ${cfg.registry} -u ${cfg.nexusUser} --password-stdin

            cd /home/ec2-user/node-app
            docker compose --env-file .env.runtime pull
            docker compose --env-file .env.runtime up -d
        '
        """
    }
}
