pipeline {
    agent any
    environment {
        //be sure to replace "willbla" with your own Docker Hub username
        DOCKER_IMAGE_APP = "alexei2ru/tasksampleapp"
        DOCKER_IMAGE_DB = "alexei2ru/tasksampledb"
        DOCKER_IMAGE_FILES = "task"
    }
    stages {
        stage('Build Sample App') {
            steps {
                dir ('task') {
                    echo 'Running build automation'
                    sh 'mvn clean package'
                }
            }
        }
        stage('Build Docker Image for Sample App') {
            steps {
                script {
                    dir ('task') {
                        app = docker.build(DOCKER_IMAGE_APP)
                        app.inside {
                            sh 'echo Hello, APP!'
                        }
                    }
                }
            }
        }
        stage('Build DB Docker Image for Sample App') {
            steps {
                script {
                    dir ('task-db') {
                        app_db = docker.build(DOCKER_IMAGE_DB)
                        app_db.inside {
                            sh 'echo Hello, DB!'
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
                        app_db.push("${env.BUILD_NUMBER}")
                        app_db.push("latest")
                    }
                }
            }
        }
        stage('DeployToTest') {
            when {
                branch 'test_branch'
            }
            steps {
                 sh 'docker-compose -f docker-compose.yaml up'
            }
        }
        stage('DeployToProduction') {
            when {
                branch 'production'
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
