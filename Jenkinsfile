pipeline {
    agent any
    options {
        // Agar Jenkins restart ho, toh pipeline khud ko retry kare
        retry(2)
    }
    tools {
        maven 'Maven3'
        nodejs 'Node18'
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