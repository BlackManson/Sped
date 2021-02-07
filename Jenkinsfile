pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh './gradlew build'
      }
    }

    stage('Test') {
      steps {
        sh './gradlew test'
        archiveArtifacts 'app/build/reports/tests/testDebugUnitTest/'
      }
    }

    stage('Assemble') {
      steps {
        sh './gradlew assembleDebug'
        echo 'Wygenerowano plik apk'
      }
    }

    stage('Run Emulator') {
      steps {
        sh './run.sh'
      }
    }

    stage('Sonarqube') {
      steps {
        sh './gradlew sonarqube'
      }
    }

  }
  post {
    always {
      archiveArtifacts(artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true)
    }

  }
}