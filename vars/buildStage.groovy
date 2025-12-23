def call(steps) {
    steps.stage('Build') {
        steps.echo "Build handled inside Dockerfile"
    }
}
