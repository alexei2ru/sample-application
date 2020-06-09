pipeline {
    options {

    buildDiscarder(
        logRotator(
            // number of build logs to keep
            numToKeepStr:'5',
            // history to keep in days
            daysToKeepStr: '1000',
            // artifacts are kept for days
            artifactDaysToKeepStr: '1000',
            // number of builds have their artifacts kept
            artifactNumToKeepStr: '5'
        )
    )
    }
    agent any
    environment {
        //be sure to replace "willbla" with your own Docker Hub username
        DOCKER_IMAGE_APP = "alexei2ru/tasksampleapp"
        DOCKER_IMAGE_DB = "alexei2ru/tasksampledb"
        PATH = "$PATH:/usr/local/bin"
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
                parallel (
                "buildAppContainer" : {
                    script {
                        dir ('task') {
                            app = docker.build(DOCKER_IMAGE_APP)
                            app.inside {
                                sh 'echo Hello, APP!'
                            }
                        }
                    }
                },
                "buildDBContainer" : {
                    script {
                        dir ('task-db') {
                            app_db = docker.build(DOCKER_IMAGE_DB)
                            app_db.inside {
                                sh 'echo Hello, DB!'
                            }
                        }
                    }
                }
                )
            }
        }
        stage('Push Docker Image test') {
            when {
                branch 'test_branch'
            }
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub_login') {
                        app.push("test")
                        app.push("latest")
                        app_db.push("test")
                        app_db.push("latest")
                    }
                }
            }
        }
        stage('Push Docker Image prod') {
            when {
                branch 'master'
            }
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub_login') {
                        app.push("prod")
                        app.push("latest")
                        app_db.push("prod")
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
                    sh 'docker-compose -f docker-compose.yaml down'
                    sh 'docker-compose -f docker-compose.yaml up -d'
                    script {
                        sleep time: 30, unit: 'SECONDS'
                        def response = sh(script: 'curl -s http://alexandruszabo1c.mylabserver.com:30080/data/hello', returnStdout: true) 
                        echo "--------------Response----------" +response
                        if (response == 'Hello World') {
                            sh 'docker-compose -f docker-compose.yaml down'
                        }
                    }
                    kubernetesDeploy(
                    kubeconfigId: 'kube_config',
                    configs: 'task-db-kube.yaml',
                    enableConfigSubstitution: true
                  )
                    
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

post {
    success {
      echo "SUCCESS"
    }
    failure {
      echo "FAILURE"
    }
    changed {
      echo "Status Changed: [From: $currentBuild.previousBuild.result, To: $currentBuild.result]"
    }
    always {
      script {
        def result = currentBuild.result
        if (result == null) {
          result = "SUCCESS"
        }
      }
    }
  }
}
