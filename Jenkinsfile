pipeline {
    agent any  // Utiliser n'importe quel agent disponible
    environment {
        SONARQUBE_SERVER = 'sq'  // Nom du serveur configuré dans Jenkins
        SONARQUBE_LOGIN = credentials('sonar-token')  // Nom des credentials configurés
    }
    stages {
        stage('Checkout') {  // Première étape : récupération du code
            steps {
             git branch: 'HassenMrakbenJebahi-5SIM4-blaugranaGroup',
             url: 'https://github.com/hassenmrakbenjebahi/5SIM4-blaugranaGroup-gestion-station-ski.git'
             }
        }

        stage("Compile"){
            steps{
                sh "mvn clean compile"
            }
        }
        stage("Junit/Mockito"){
            steps {
                sh "mvn test"
            }
        }


        stage('SonarQube Analysis') {  // Analyse de qualité de code avec SonarQube
             steps {
               withSonarQubeEnv(SONARQUBE_SERVER) {  // Utilisation de l'environnement SonarQube
                 sh "mvn sonar:sonar -Dsonar.login=${SONARQUBE_LOGIN}"  // Lancer l'analyse avec Maven
               }
             }
        }
    }

    post {
        success {
            echo 'Le pipeline a réussi.'
        }
        failure {
            echo 'Le pipeline a échoué.'
        }
    }
}
