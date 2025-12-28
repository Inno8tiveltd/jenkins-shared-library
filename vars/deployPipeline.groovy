def call() {
    def tag = deployByBranch()

    deploy(
        server: "ec2-user@${env.TARGET_SERVER}",
        registry: "13.251.194.219:8083",
        envFile: ".env.${env.ENV}",
        imageTag: tag
    )
}
