def call() {
    def tag = deployByBranch()

    echo "Using image tag: ${tag}"

    dockerBuild(
        registry: "13.251.194.219:8083",
        tag: tag
    )

    pushToRegistry(
        registry: "13.251.194.219:8083",
        tag: tag
    )
}
