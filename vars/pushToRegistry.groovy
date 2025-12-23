def call(steps, Map config) {
    steps.stage('Push to Nexus') {
        steps.withCredentials([steps.usernamePassword(
            credentialsId: config.credsId,
            usernameVariable: 'NEXUS_USER',
            passwordVariable: 'NEXUS_PASSWORD'
        )]) {
            steps.sh '''
              echo "$NEXUS_PASSWORD" | docker login ${REGISTRY} -u "$NEXUS_USER" --password-stdin

              docker tag node-backend:latest ${REGISTRY}/${REPO}/node-backend:latest
              docker tag node-frontend:latest ${REGISTRY}/${REPO}/node-frontend:latest
              docker tag user-frontend:latest ${REGISTRY}/${REPO}/user-frontend:latest

              docker push ${REGISTRY}/${REPO}/node-backend:latest
              docker push ${REGISTRY}/${REPO}/node-frontend:latest
              docker push ${REGISTRY}/${REPO}/user-frontend:latest
            '''
        }
    }
}
