pipeline {
   agent any
   
   stages {

      stage("Build"){
  
          steps {
             sh './gradlew build'
          }
      }
      stage("Assemble"){
  
          steps {
             sh './gradlew assembleDebug'
          }
      }
   }
   post {
        always {
            archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true
        }
    }
}
