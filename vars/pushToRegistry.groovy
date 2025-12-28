def call(Map cfg) {
    withCredentials([
        usernamePassword(
            credentialsId: 'nexus-docker-creds',
            usernameVariable: 'NEXUS_USER',
            passwordVariable: 'NEXUS_PASSWORD'
        )
    ]) {
        sh """
          echo "$NEXUS_PASSWORD" | docker login ${cfg.registry} \
            -u $NEXUS_USER --password-stdin

          docker push ${cfg.registry}/docker-hosted/node-backend:${cfg.tag}
          docker push ${cfg.registry}/docker-hosted/node-frontend:${cfg.tag}
          docker push ${cfg.registry}/docker-hosted/user-frontend:${cfg.tag}
        """
    }
}
