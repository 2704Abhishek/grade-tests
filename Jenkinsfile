pipeline {

    agent any

    environment {
        RUNNER_IMAGE = 'grade-runner:v1'
        REPORT_DIR = "${WORKSPACE}/reports"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Pull Image') {
            steps {
                sh 'docker images'
            }
        }

        stage('Run Tests') {
            steps {

                sh 'mkdir -p ${REPORT_DIR}'

                sh '''
                docker run --rm \
                -v ${WORKSPACE}:/app \
                -v ${REPORT_DIR}:/app/target/surefire-reports \
                -w /app \
                ${RUNNER_IMAGE} mvn test
                '''
            }
        }

        stage('Publish Results') {
            steps {
                junit 'reports/*.xml'
            }
        }
    }

    post {

        always {
            archiveArtifacts artifacts: 'reports/*.xml', fingerprint: true
        }

        success {
            echo 'Build passed successfully!'
        }

        failure {
            echo 'Build failed!'
        }
    }
}