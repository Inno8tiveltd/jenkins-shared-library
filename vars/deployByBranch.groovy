def call(String branch) {

```
def envMap = [
    dev  : [host: 'DEV_SERVER_IP',   envFile: '.env.dev'],
    stage: [host: 'STAGE_SERVER_IP', envFile: '.env.stage'],
    main : [host: 'PROD_SERVER_IP',  envFile: '.env.prod']
]

def cfg = envMap[branch]
if (!cfg) {
    echo "No deployment configured for branch: ${branch}"
    return
}

sshagent(['jenkins-ssh-key']) {
    sh """
    ssh ec2-user@${cfg.host} << EOF
      cd /opt/node-app
      docker compose --env-file ${cfg.envFile} pull
      docker compose --env-file ${cfg.envFile} up -d
    EOF
    """
}
```

}
