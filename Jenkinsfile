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

    stage('') {
      steps {
        sh '/Users/lukaszkolodziej/Library/Android/sdk/emulator -avd Pixel_2_API_30'
      }
    }

  }
  post {
    always {
      archiveArtifacts(artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true)
    }

  }
}