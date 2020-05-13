pipeline {
    agent any
    environment {
        //be sure to replace "willbla" with your own Docker Hub username
        DOCKER_IMAGE_NAME = "alexei2ru/tasksampleapp"
        DOCKER_IMAGE_FILES = "task"
    }
    stages {
        stage('Build Sample App') {
            steps {
                dir ('task') {
                    echo 'Running build automation'
                    sh 'mvn clean package -f ./task/pom.xml'
                }
            }
        }
        stage('Build Docker Image for Sample App') {
            when {
                branch 'master'
            }
            steps {
                script {
                    dir ('task') {
                        app = docker.build(DOCKER_IMAGE_NAME)
                        app.inside {
                            sh 'echo Hello, World!'
                        }
                    }
                }
            }
        }
        stage('Push Docker Image') {
            when {
                branch 'master'
            }
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub_login') {
                        app.push("${env.BUILD_NUMBER}")
                        app.push("latest")
                    }
                }
            }
        }
        stage('DeployToProduction') {
            when {
                branch 'example'
            }
            steps {
                input 'Deploy to Production?'
                milestone(1)
                 kubernetesDeploy(
                    kubeconfigId: 'kube_config',
                    configs: 'recipe-shop.yml',
                    enableConfigSubstitution: true
                  )
            }
        }
       
    }
}
