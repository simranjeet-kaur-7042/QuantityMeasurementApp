pipeline {
    agent any
    
    stages {
        stage('Build Backend') {
            steps {
                dir('measurement') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }
        stage('Build Frontend') {
            steps {
                dir('Frontendquantity-frontend') {
                    sh 'npm install && npm run build'
                }
            }
        }
    }
}