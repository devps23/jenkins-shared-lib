def call() {
    node('node1') {

        stage('CodeCheckout') {

            sh "find ."
            sh "find . | sed -e '1d' |xargs rm -rf"
            if(env.TAG_NAME ==~ ".*") {
                env.branch_name = "refs/tags/${env.TAG_NAME}"
            } else {
                env.branch_name = "${env.BRANCH_NAME}"
            }
            checkout([$class: 'GitSCM',
                      branches: [[name: "${branch_name}"]],
                      userRemoteConfigs: [[url: "https://github.com/devps23/expense-${component}"]]]
            )
        }

        if (env.TAG_NAME ==~ '.*') {
            stage('Build Code') {
                sh 'docker build -t 041445559784.dkr.ecr.us-east-1.amazonaws.com/expense-backend:${TAG_NAME} .'
            }
            stage('Release Software') {
                sh 'docker login aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 041445559784.dkr.ecr.us-east-1.amazonaws.com'
                sh 'docker push 041445559784.dkr.ecr.us-east-1.amazonaws.com/expense-backend:${TAG_NAME}'
               print 'OK'
            }
                   } else {
            stage('Lint Code') {
                print 'OK'
            }
            if(env.BRANCH_NAME != 'main') {
                stage('Run Unit tests') {
                    print 'OK'
                }
                stage('Run Integration tests') {
                    print 'OK'
                }
            }
            stage('Sonar Scan Code Review') {
                print 'OK'
            }

        }

    }
}