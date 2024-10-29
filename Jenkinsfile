pipeline {
    agent any  // Utiliser n'importe quel agent disponible

    stages {
        stage('Checkout') {  // Première étape : récupération du code
            steps {
                git branch: 'HassenMrakbenJebahi-5SIM4-blaugranaGroup',
                    url: 'https://github.com/hassenmrakbenjebahi/5SIM4-blaugranaGroup-gestion-station-ski.git'
            }
        }

        stage("Compile") {
            steps {
                sh "mvn clean compile"
            }
        }
        
        stage("Junit/Mockito") {
            steps {
                sh "mvn test"
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
