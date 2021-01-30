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
        sh './gradlew check'
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
        sh './gradlew sonarqube -Dsonar.projectKey=sped -Dsonar.host.url=http://34.107.83.75:9000 -Dsonar.login=5ab4f44500fc9833cf734c4e64b6d942bd55c064'
      }
    }

  }
  post {
    always {
      archiveArtifacts(artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true)
    }

  }
}