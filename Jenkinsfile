pipeline {
    agent any
    tools {
        maven 'Maven 3.9.11'
    }

    environment {
     PATH = "/usr/local/bin:${env.PATH}"
//            JAVA_HOME = 'C:\\Program Files\\Java\\jdk-21'  // Adjust to your actual JDK pat
        SONARQUBE_SERVER = 'SonarQubeServer'  // The name of the SonarQube server configured in Jenkins
        SONAR_TOKEN = 'SONAR_TOKEN' // Store the token securely
        DOCKERHUB_CREDENTIALS_ID = 'Docker_Miro_Hub'
        DOCKERHUB_REPO = 'mirovaltonen2/sep2_week5_inclass_s2'
        DOCKER_IMAGE_TAG = 'latest'


    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Miro193/sep2_week5_inclass_s2.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    sh """
                        ${tool 'SonarScanner'}\\bin\\sonar-scanner ^
                        -Dsonar.projectKey=devops-demo ^
                        -Dsonar.sources=src ^
                        -Dsonar.projectName=DevOps-Demo ^
                        -Dsonar.host.url=http://localhost:9000 ^
                        -Dsonar.login=${env.SONAR_TOKEN} ^
                        -Dsonar.java.binaries=target/classes
                    """
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}")
                    // Or specify Dockerfile path explicitly if needed
                    // docker.build("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}", "-f ./Dockerfile .")
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENTIALS_ID}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                        docker login -u $DOCKER_USER -p $DOCKER_PASS
                        docker push $DOCKERHUB_REPO:$DOCKER_IMAGE_TAG
                    '''
                }
            }
        }
    }
}
