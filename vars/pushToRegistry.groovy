def call(steps, Map config) {
    steps.stage('Push to Nexus') {
        steps.withCredentials([steps.usernamePassword(
            credentialsId: config.credsId,
            usernameVariable: 'NEXUS_USER',
            passwordVariable: 'NEXUS_PASSWORD'
        )]) {
            steps.sh """
                echo $NEXUS_PASSWORD | docker login ${config.registry} -u $NEXUS_USER --password-stdin
                docker tag ${config.backendImage}:${config.tag} ${config.registry}/${config.repo}/${config.backendImage}:${config.tag}
                docker tag ${config.frontendImage}:${config.tag} ${config.registry}/${config.repo}/${config.frontendImage}:${config.tag}
                docker push ${config.registry}/${config.repo}/${config.backendImage}:${config.tag}
                docker push ${config.registry}/${config.repo}/${config.frontendImage}:${config.tag}
            """
        }
    }
}
