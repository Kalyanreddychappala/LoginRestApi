pipeline {
    agent any
    
    environment {
        MAVEN_OPTS = '-Xmx1024m'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        
        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }
        

        
        stage('Deploy to Staging') {
            when {
                branch 'develop'
            }
            steps {
                sh '''
                    echo "Deploying to staging environment"
                    echo "JAR file: target/LoginForm-0.0.1-SNAPSHOT.jar"
                    echo "Profile: staging"
                    echo "Deployment completed successfully"
                '''
            }
        }
        
        stage('Deploy to Production') {
            when {
                branch 'main'
            }
            steps {
                input message: 'Deploy to production?', ok: 'Deploy'
                sh '''
                    echo "Deploying to production environment"
                    echo "JAR file: target/LoginForm-0.0.1-SNAPSHOT.jar"
                    echo "Profile: prod"
                    echo "Deployment completed successfully"
                '''
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        success {
            echo "Build completed successfully!"
        }
        failure {
            echo "Build failed. Check the console output for details."
        }
    }
}