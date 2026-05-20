pipeline {
    agent any
    tools {
        maven 'Maven3' // Wahi naam jo aapne Tool settings mein diya tha
        nodejs 'Node18' // Wahi naam jo aapne Tool settings mein diya tha
    }
    stages {
        stage('Build Backend') {
            steps {
                dir('measurement') { // Yahan apne folder ka sahi naam likhein
                    sh 'mvn clean package -DskipTests'
                }
            }
        }
        stage('Build Frontend') {
            steps {
                dir('Frontend/quantity-frontend') { // Yahan apne folder ka sahi naam likhein
                    sh 'npm install && npm run build'
                }
            }
        }
    }
}