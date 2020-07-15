import jenkins.model.*
import hudson.security.*

def instance = Jenkins.getInstance()

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount("admin_02","admin02pass")
instance.setSecurityRealm(hudsonRealm)
instance.save()
def strategy = new GlobalMatrixAuthorizationStrategy()
strategy.add(Jenkins.ADMINISTER, "admin_02")
instance.setAuthorizationStrategy(strategy)
