def call(String status) {
    emailext(
        subject: "Build ${status}: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
        body: """
        <h3>Build ${status}</h3>
        <p><b>Job:</b> ${env.JOB_NAME}</p>
        <p><b>Build:</b> #${env.BUILD_NUMBER}</p>
        <p><b>URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
        """,
        mimeType: 'text/html',
        to: 'jsp82767@gmail.com'
    )
}
