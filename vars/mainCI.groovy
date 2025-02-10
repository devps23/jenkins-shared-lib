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
                sh 'docker build -t 041445559784.dkr.ecr.us-east-1.amazonaws.com/expense-${component}:${TAG_NAME} .'
                print 'OK'
            }
            stage('Release Software') {
                sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 041445559784.dkr.ecr.us-east-1.amazonaws.com'
                sh 'docker push 041445559784.dkr.ecr.us-east-1.amazonaws.com/expense-${component}:${TAG_NAME}'
            }
//            stage('Deploy to Dev'){
//                sh 'aws eks update-kubeconfig --name dev-eks'
//                sh 'argocd login $(kubectl get svc argocd argocd-server | awk \'{print$4}\' | tail -1) --username admin --password $(argocd admin initial-password -n argocd | head -1) --insecure --grpc-web'
//                sh 'argocd app create ${component} --repo https://github.com/devps23/expense-helm-chart.git --path chart --dest-server https://kubernetes.default.svc --dest-namespace default.svc --grpc-web --values values/${app}.yml'
//                sh 'argocd app set ${component} --parameter appVersion=${TAG_NAME}'
//                sh 'argocd app sync ${component}'
//               print 'OK'
//            }
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