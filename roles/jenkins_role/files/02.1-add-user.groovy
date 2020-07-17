#!groovy

import jenkins.*
import hudson.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import hudson.plugins.sshslaves.*;
import hudson.model.*
import jenkins.model.*
import hudson.security.*

global_domain = Domain.global()
credentials_store =
  Jenkins.instance.getExtensionList(
    'com.cloudbees.plugins.credentials.SystemCredentialsProvider'
  )[0].getStore()

credentials = new BasicSSHUserPrivateKey(CredentialsScope.GLOBAL,null,"root",new BasicSSHUserPrivateKey.UsersPrivateKeySource(),"","")

credentials_store.addCredentials(global_domain, credentials)

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
def adminUsername = System.getenv('JENKINS_ADMIN_USERNAME') ?: 'admin'
def adminPassword = System.getenv('JENKINS_ADMIN_PASSWORD') ?: 'password'
hudsonRealm.createAccount(admin3, admin3pass)
//hudsonRealm.createAccount("charles", "charles")

def instance = Jenkins.getInstance()
instance.setSecurityRealm(hudsonRealm)
instance.save()

def strategy = new GlobalMatrixAuthorizationStrategy()

//  Setting Anonymous Permissions
strategy.add(hudson.model.Hudson.READ,'anonymous')
strategy.add(hudson.model.Item.BUILD,'anonymous')
strategy.add(hudson.model.Item.CANCEL,'anonymous')
strategy.add(hudson.model.Item.DISCOVER,'anonymous')
strategy.add(hudson.model.Item.READ,'anonymous')

// Setting Admin Permissions
strategy.add(Jenkins.ADMINISTER, "admin")

// Setting easy settings for local builds
def local = System.getenv("BUILD").toString()
if(local == "local") {
  //  Overall Permissions
  strategy.add(hudson.model.Hudson.ADMINISTER,'anonymous')
  strategy.add(hudson.PluginManager.CONFIGURE_UPDATECENTER,'anonymous')
  strategy.add(hudson.model.Hudson.READ,'anonymous')
  strategy.add(hudson.model.Hudson.RUN_SCRIPTS,'anonymous')
  strategy.add(hudson.PluginManager.UPLOAD_PLUGINS,'anonymous')
}

instance.setAuthorizationStrategy(strategy)
instance.save()
