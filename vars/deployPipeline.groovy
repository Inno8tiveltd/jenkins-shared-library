def call() {
    def tag = deployByBranch()
    def serverCredId = resolveTargetServer()

    withCredentials([
        string(credentialsId: serverCredId, variable: 'TARGET_SERVER')
    ]) {
        deploy(
            server: "ec2-user@${TARGET_SERVER}",
            registry: "13.251.194.219:8083",
            envFile: ".env.${env.BRANCH_NAME}",
            imageTag: tag
        )
    }
}
