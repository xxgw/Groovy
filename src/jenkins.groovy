#!/usr/bin/env groovy
node {
    stage ( 'Checkout') {
        def scmVars = checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '2220b674-f45c-49b0-93d5-bdd959587643', depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: 'https://YANG/svn/reop/jenkins/trunk']], quietOperation: true, workspaceUpdater: [$class: 'UpdateUpdater']])
        println(scmVars)
    }
    stage ('Sonar Scan'){
        echo 'Sonar Scan'
    }

    stage ('Quality Gate'){
        echo 'Quality Gate'
    }

    stage('Build'){
        echo message: 'Start build...'
        try {
            bat script: 'mvn -B -DskipTests clean package'
        } catch (Exception exception){
            echo (exception.toString())
            error('Build Failed!')
        } finally{
            echo 'Finish build...'
        }
    }

    stage('Unit Test'){
        echo('Start unit test...')
        try{
            bat("mvn test")
        }catch (Exception e){
            echo(e.toString())
        }finally{
            junit 'target/surefire-reports/*.xml'
            echo("Finish unit test...")
        }
    }

    stage("Deploy"){
        echo ("Start Deploy...")
        echo ("Finish Deploy...")
    }


}