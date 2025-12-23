sshagent(credentials: ['ec2-ssh-key']) {
    sh """
    ssh -o StrictHostKeyChecking=no ec2-user@${serverIP} '
        echo "${NEXUS_PASSWORD}" | docker login 13.251.194.219:8083 -u ${NEXUS_USER} --password-stdin &&
        cd /home/ec2-user/node-app &&
        docker compose --env-file ${envFile} pull &&
        docker compose --env-file ${envFile} up -d
    '
    """
}
