def call(Map cfg) {
    sh """
      docker build -t ${cfg.registry}/docker-hosted/node-backend:${cfg.tag} backend
      docker build -t ${cfg.registry}/docker-hosted/node-frontend:${cfg.tag} frontend
      docker build -t ${cfg.registry}/docker-hosted/user-frontend:${cfg.tag} user-portal-frontend
    """
}
