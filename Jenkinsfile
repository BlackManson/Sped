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

    stage('error') {
      steps {
        sh './run.sh'
      }
    }

  }
  post {
    always {
      archiveArtifacts(artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true)
    }

  }
}