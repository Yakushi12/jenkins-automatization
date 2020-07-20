#!groovy

import jenkins.model.*
import hudson.security.*

def env = System.getenv()

def jenkins = Jenkins.getInstance()
if(!(jenkins.getSecurityRealm() instanceof HudsonPrivateSecurityRealm))
    jenkins.setSecurityRealm(new HudsonPrivateSecurityRealm(false))

if(!(jenkins.getAuthorizationStrategy() instanceof hudson.security.GlobalMatrixAuthorizationStrategy))
    jenkins.setAuthorizationStrategy(new hudson.security.GlobalMatrixAuthorizationStrategy())

def user = jenkins.getSecurityRealm().createAccount("admin4", "admin4pass")
user.save()
jenkins.getAuthorizationStrategy().add(Jenkins.ADMINISTER, "admin4")

jenkins.save()
