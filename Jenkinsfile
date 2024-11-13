pipeline {
    agent any
    environment {
        registry = "cawael09/blaugrana"
        registryCredential = 'dockerHub'
        dockerImage = ''
        imageTag = ''
        IMAGE_REPO = "cawael09/blaugrana"
        sonarServer = 'SonarQubeServer' 
        newVersion = ""
    }

    stages {
        stage("Cloning Project") {
            steps {
                git branch: 'WaelAouadhi-5SIM4blaugranaGroup', credentialsId: 'GithubID', url: 'https://github.com/hassenmrakbenjebahi/5SIM4-blaugranaGroup-gestion-station-ski.git url: '', credentialsId: '', branch '''
                echo 'Checkout stage complete'
            }
        }
      
        stage('Maven Clean') {
            steps {
                sh 'mvn clean'
                echo 'Clean stage done'
            }
        }

        stage('Compile Project') {
            steps {
                sh 'mvn compile'
                echo 'Compile stage done'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
                echo 'Unit tests stage done'
            }
        }
           stage('Update Version') {
            steps {
                script {
                    def currentVersion = sh(script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()
                    echo "Current version: ${currentVersion}"
                    
                    newVersion = currentVersion.replaceAll(/SNAPSHOT$/, "") + ".${env.BUILD_NUMBER}"
                    echo "New version: ${newVersion}"

                    sh "mvn versions:set -DnewVersion=${newVersion} -DgenerateBackupPoms=false"
                }
            }
        }
        
        stage('Maven Package') {
            steps {
                sh 'mvn package -DskipTests'
                echo 'Package stage completed'
            }
        }
        
        stage('Verify JAR File') {
            steps {
                script {
                    def jarFile = "target/gestion-station-ski-${newVersion}.jar"
                    if (!fileExists(jarFile)) {
                        error("JAR file ${jarFile} not found.")
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                sh "mvn deploy:deploy-file -DgroupId=tn.esprit.spring -DartifactId=gestion-station-ski -Dversion=${newVersion} -DgeneratePom=true -Dpackaging=jar -DrepositoryId=deploymentRepo -Durl=http://192.168.29.128:8081/repository/maven-releases/ -Dfile=target/gestion-station-ski-${newVersion}.jar -DskipTests"
                echo 'Deploy stage completed'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    imageTag = "v${env.BUILD_NUMBER}"
                    
                    dockerImage = docker.build("${registry}:${imageTag}", "--build-arg JAR_VERSION=${newVersion} .")
                    echo "Docker image built with tag ${imageTag}"
                }
            }
        }

        stage("Docker Push") {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', registryCredential) {
                        dockerImage.push()
                        echo "Docker image pushed: ${dockerImage}"
                    }
                }
            }
        }
        
        stage('Clean Old Docker Images from Registry') {
            steps {
                script {
                    def currentBuildNumber = env.BUILD_NUMBER.toInteger()

                    if (currentBuildNumber > 1) {
                        docker.withRegistry('', registryCredential) {
                            for (int i = 1; i < currentBuildNumber; i++) {
                                def oldTag = "v${i}"
                                def oldImage = "${IMAGE_REPO}:${oldTag}"
                                sh "docker rmi ${oldImage} || echo 'Image ${oldImage} not found or already removed.'"
                                echo "Attempted to remove Docker image ${oldImage}."
                            }
                        }
                    } else {
                        echo "No previous Docker images to clean up."
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
