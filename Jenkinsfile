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
}
